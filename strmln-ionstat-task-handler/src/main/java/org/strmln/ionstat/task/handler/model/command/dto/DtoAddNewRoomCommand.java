package org.strmln.ionstat.task.handler.model.command.dto;

import org.strmln.ionstat.task.handler.model.command.AddNewRoomCommand;

public class DtoAddNewRoomCommand implements
		DtoTaskHandlerCommand<AddNewRoomCommand> {

	private Long _parentId;
	private String _name;
	private Long _inspectionInterval;

	@Override
	public AddNewRoomCommand convertToInternalTaskHandlerCommand() {
		AddNewRoomCommand result = new AddNewRoomCommand();
		result.setParentId(getParentId());
		result.setName(getName());
		result.setInspectionInterval(getInspectionInterval());

		return result;
	}

	public Long getInspectionInterval() {
		return _inspectionInterval;
	}

	public String getName() {
		return _name;
	}

	public Long getParentId() {
		return _parentId;
	}

	public void setInspectionInterval(Long inspectionInterval) {
		_inspectionInterval = inspectionInterval;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setParentId(Long parentId) {
		_parentId = parentId;
	}

}
