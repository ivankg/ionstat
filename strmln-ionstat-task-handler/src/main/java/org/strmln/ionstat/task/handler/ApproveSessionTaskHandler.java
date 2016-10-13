package org.strmln.ionstat.task.handler;

import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.strmln.ionstat.service.SessionService;
import org.strmln.ionstat.task.handler.model.TaskResponse;
import org.strmln.ionstat.task.handler.model.command.ApproveSessionCommand;
import org.strmln.ionstat.task.handler.model.command.dto.DtoApproveSessionCommand;
import org.strmln.ionstat.task.handler.model.command.dto.DtoTaskHandlerCommand;

public class ApproveSessionTaskHandler extends AbstractTaskHandler {

	@Autowired
	private SessionService _sessionService;

	@Override
	public Class<? extends DtoTaskHandlerCommand<?>> getCommandClass() {
		return DtoApproveSessionCommand.class;
	}

	@Override
	public TaskResponse processTask(Object command, List<Path> files) {
		ApproveSessionCommand internalCommand = (ApproveSessionCommand) command;

		getSessionService().changeSessionStatus(internalCommand.getSessionId(),
				internalCommand.getStatus(), internalCommand.getComment());

		return new TaskResponse();
	}

	private SessionService getSessionService() {
		return _sessionService;
	}

	@Override
	protected TaskResponse executeGetTaskInfos(Object command) {
		return null;
	}

}
