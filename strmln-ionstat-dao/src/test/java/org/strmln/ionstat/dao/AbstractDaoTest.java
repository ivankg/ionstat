package org.strmln.ionstat.dao;

import java.lang.reflect.Method;

import javax.sql.DataSource;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.strmln.ionstat.dao.helper.DbUnitHelper;
import org.strmln.ionstat.dao.model.TestCase;
import org.strmln.ionstat.dao.model.TestSuite;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:org/strmln/ionstat/dao/spring/applicationContext-hibernate.xml" })
@TransactionConfiguration(defaultRollback = false)
@TestExecutionListeners({ TransactionalTestExecutionListener.class, DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class })
@Transactional
public abstract class AbstractDaoTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		SimpleNamingContextBuilder builder = SimpleNamingContextBuilder.emptyActivatedContextBuilder();

		SingleConnectionDataSource ds = new SingleConnectionDataSource();

		ds.setDriverClassName("net.sf.log4jdbc.DriverSpy");
		ds.setUrl("jdbc:log4jdbc:h2:mem:test;create=true");
		ds.setSuppressClose(true);

		builder.bind("java:comp/env/jdbc/ionstat", ds);
	}

	@Autowired
	private SessionFactory _sessionFactory;

	@Autowired
	private DataSource _dataSource;

	@Rule
	public TestName _testMethodName = new TestName();

	@Before
	public void setUp() throws Exception {

		DbUnitHelper dbUnitHelper = new DbUnitHelper(getDataSource());

		String cleanDataSetName = "data-sets/cleanDataSet.xml";

		FlatXmlDataSetBuilder builderClean = new FlatXmlDataSetBuilder();
		builderClean.setColumnSensing(true);
		IDataSet cleanDataSet = builderClean.build(getClass().getClassLoader().getResourceAsStream(cleanDataSetName));

		String baseDataSetName = "data-sets/baseDataSet.xml";

		Class<?> clazz = this.getClass();
		TestSuite classAnnotation = clazz.getAnnotation(TestSuite.class);
		Method testMethod = clazz.getMethod(_testMethodName.getMethodName(), new Class<?>[] {});
		TestCase methodAnnotation = testMethod.getAnnotation(TestCase.class);

		if (methodAnnotation != null && classAnnotation != null) {
			String pathFromAnotation = buildTestCasePath(classAnnotation, methodAnnotation);
			baseDataSetName = "data-sets/annotations/" + pathFromAnotation;
		}

		FlatXmlDataSetBuilder builderData = new FlatXmlDataSetBuilder();
		builderData.setColumnSensing(true);
		IDataSet dataSet = builderData.build(getClass().getClassLoader().getResourceAsStream(baseDataSetName));

		dbUnitHelper.cleanDatabase(cleanDataSet);
		dbUnitHelper.setupDatabase(dataSet);
	}

	@After
	public void tearDown() throws Exception {
		Session session = getSessionFactory().getCurrentSession();

		Assert.assertNotNull(session);

		session.clear();
	}

	private String buildTestCasePath(TestSuite classAnnotation, TestCase methodAnnotation) {
		StringBuilder path = new StringBuilder();

		path.append(classAnnotation.value());
		path.append("/");
		path.append(methodAnnotation.value());
		path.append(".xml");

		return path.toString();
	}

	private DataSource getDataSource() {
		return _dataSource;
	}

	private SessionFactory getSessionFactory() {
		return _sessionFactory;
	}

}
