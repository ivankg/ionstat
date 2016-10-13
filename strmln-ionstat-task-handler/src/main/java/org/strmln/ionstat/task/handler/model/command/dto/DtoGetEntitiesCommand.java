package org.strmln.ionstat.task.handler.model.command.dto;

import org.strmln.ionstat.task.handler.model.command.GetEntitiesCommand;

public class DtoGetEntitiesCommand implements
		DtoTaskHandlerCommand<GetEntitiesCommand> {

	private Long _parentId;

	@Override
	public GetEntitiesCommand convertToInternalTaskHandlerCommand() {
		GetEntitiesCommand result = new GetEntitiesCommand();
		result.setParentId(getParentId());

		return result;
	}

	public Long getParentId() {
		return _parentId;
	}

	public void setParentId(Long parentId) {
		_parentId = parentId;
	}

}
