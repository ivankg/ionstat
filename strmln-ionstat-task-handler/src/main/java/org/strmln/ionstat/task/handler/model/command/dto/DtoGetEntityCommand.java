package org.strmln.ionstat.task.handler.model.command.dto;

import org.strmln.ionstat.task.handler.model.command.GetEntityCommand;

public class DtoGetEntityCommand implements
		DtoTaskHandlerCommand<GetEntityCommand> {

	private Long _id;

	@Override
	public GetEntityCommand convertToInternalTaskHandlerCommand() {
		GetEntityCommand internalCommand = new GetEntityCommand();
		internalCommand.setId(getId());
		return internalCommand;
	}

	public Long getId() {
		return _id;
	}

	public void setId(Long id) {
		_id = id;
	}

}
