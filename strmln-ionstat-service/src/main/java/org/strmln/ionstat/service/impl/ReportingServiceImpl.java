package org.strmln.ionstat.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.apache.commons.lang3.mutable.MutableObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.strmln.ionstat.service.ReportingService;

@Component("reportingService")
public class ReportingServiceImpl implements ReportingService {

	private static final String SESSION_ID = "sessionId";
	private static final String REPORT_TEMPLATE_PATH = "org/strmln/ionstat/service/reports/report.jrxml";

	@Autowired
	private SessionFactory _sessionFactory;

	public InputStream generateReport(Long sessionId) {
		try {
			InputStream inputStream = ReportingServiceImpl.class
					.getClassLoader().getResourceAsStream(REPORT_TEMPLATE_PATH);
			final JasperReport jasperReport = JasperCompileManager
					.compileReport(inputStream);

			JasperPrint jasperPrint = createPrint(sessionId, jasperReport);

			byte[] reportByteArray = JasperExportManager
					.exportReportToPdf(jasperPrint);

			return new ByteArrayInputStream(reportByteArray);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private JasperPrint createPrint(Long sessionId,
			final JasperReport jasperReport) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put(SESSION_ID, sessionId);

		final MutableObject<JasperPrint> report = new MutableObject<>();
		Session session = getSessionFactory().openSession();
		session.doWork(new Work() {

			@Override
			public void execute(Connection connection) throws SQLException {
				try {
					report.setValue(JasperFillManager.fillReport(jasperReport,
							parameters, connection));
				} catch (Exception e) {
					throw new RuntimeException(e);
				}

			}
		});
		session.close();

		return report.getValue();
	}

	private SessionFactory getSessionFactory() {
		return _sessionFactory;
	}
}
