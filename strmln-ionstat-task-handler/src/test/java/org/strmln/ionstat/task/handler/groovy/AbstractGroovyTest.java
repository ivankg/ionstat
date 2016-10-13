package org.strmln.ionstat.task.handler.groovy;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import org.strmln.ionstat.task.handler.groovy.annotations.TestCase;
import org.strmln.ionstat.task.handler.groovy.annotations.TestSuite;

public abstract class AbstractGroovyTest {

    private static final String SLASH = "/";

	private TestModel _testModel;

    private static final String TEST_CASE_EXSTENSION = ".groovy";

    private String buildTestCasePath(TestSuite testSuite, TestCase testCase) {
	StringBuilder path = new StringBuilder(getTestSuiteRoot());
	path.append(testSuite.value());
	path.append(SLASH);
	path.append(testCase.value());
	path.append(TEST_CASE_EXSTENSION);
	return path.toString();
    }

    protected TestModel createModelFromResource(String resourcePath) {
	return TestModelCreator.create(resourcePath);
    }

    protected <T> T getModelObject(String name, Class<T> clazz) {
	T object;
	if (_testModel == null) {
	    throw new RuntimeException("Add prepareTestModel call to test setup");
	} else {
	    object = _testModel.getObject(name, clazz);
	}
	return object;
    }

    protected abstract String getTestSuiteRoot();

    protected void prepareAnnotatedMethodModel(String methodName) {
	try {
	    Class<?> clazz = this.getClass();
	    AnnotatedElement annotatedClass = clazz;
	    TestSuite classAnnotation = annotatedClass.getAnnotation(TestSuite.class);
	    Method testMethod = clazz.getMethod(methodName, new Class<?>[] {});
	    TestCase methodAnnotation = testMethod.getAnnotation(TestCase.class);
	    _testModel = null;

	    if (classAnnotation != null && methodAnnotation != null) {
		String testCasePath = buildTestCasePath(classAnnotation, methodAnnotation);
		_testModel = TestModelCreator.create(testCasePath);
	    }
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

}
