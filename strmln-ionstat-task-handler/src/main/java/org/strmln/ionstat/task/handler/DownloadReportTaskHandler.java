package org.strmln.ionstat.task.handler;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.strmln.ionstat.service.ReportingService;
import org.strmln.ionstat.task.handler.model.TaskResponse;
import org.strmln.ionstat.task.handler.model.command.GetEntityCommand;
import org.strmln.ionstat.task.handler.model.command.dto.DtoGetEntityCommand;
import org.strmln.ionstat.task.handler.model.command.dto.DtoTaskHandlerCommand;

public class DownloadReportTaskHandler extends AbstractTaskHandler {

	private static final String PDF_EXTENSION = ".pdf";
	private static final String SESSION_REPORT_PREFIX = "SessionReport_";

	@Autowired
	private ReportingService _reportingService;

	@Override
	public Class<? extends DtoTaskHandlerCommand<?>> getCommandClass() {
		return DtoGetEntityCommand.class;
	}

	@Override
	public TaskResponse processTask(Object command, List<Path> files) {
		return null;
	}

	private ReportingService getReportingService() {
		return _reportingService;
	}

	@Override
	protected TaskResponse executeGetTaskInfos(Object command) {
		GetEntityCommand entityCommand = (GetEntityCommand) command;
		Long sessionId = entityCommand.getId();
		InputStream reportInputStream = getReportingService().generateReport(
				sessionId);

		String reportFileName = SESSION_REPORT_PREFIX.concat(
				String.valueOf(sessionId)).concat(PDF_EXTENSION);

		TaskResponse taskResponse = new TaskResponse();
		taskResponse.setInputStream(reportInputStream);
		taskResponse.setResourceName(reportFileName);
		return taskResponse;
	}
}
