package org.strmln.ionstat.task.handler.model.command.dto;

import org.strmln.ionstat.task.handler.model.command.AddNewDepartmentCommand;

public class DtoAddNewDepartmentCommand implements
		DtoTaskHandlerCommand<AddNewDepartmentCommand> {

	private Long _parentId;
	private String _name;

	@Override
	public AddNewDepartmentCommand convertToInternalTaskHandlerCommand() {
		AddNewDepartmentCommand result = new AddNewDepartmentCommand();
		result.setParentId(getParentId());
		result.setName(getName());

		return result;
	}

	public String getName() {
		return _name;
	}

	public Long getParentId() {
		return _parentId;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setParentId(Long parentId) {
		_parentId = parentId;
	}

}
