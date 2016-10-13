package org.strmln.ionstat.service;

import java.io.InputStream;

public interface ReportingService {

	InputStream generateReport(Long sessionId);
}
