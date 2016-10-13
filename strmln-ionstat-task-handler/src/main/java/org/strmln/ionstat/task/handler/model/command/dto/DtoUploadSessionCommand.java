package org.strmln.ionstat.task.handler.model.command.dto;

import org.strmln.ionstat.task.handler.model.command.UploadSessionCommand;

public class DtoUploadSessionCommand implements
		DtoTaskHandlerCommand<UploadSessionCommand> {

	private Long _sessionTemplateId;

	@Override
	public UploadSessionCommand convertToInternalTaskHandlerCommand() {
		UploadSessionCommand command = new UploadSessionCommand();
		command.setSessionTemplateId(getSessionTemplateId());
		return command;
	}

	public Long getSessionTemplateId() {
		return _sessionTemplateId;
	}

	public void setSessionTemplateId(Long sessionTemplateId) {
		_sessionTemplateId = sessionTemplateId;
	}

}
