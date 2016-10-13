package org.strmln.ionstat.task.handler.model.command;

public class AddNewDepartmentCommand {

	private Long _parentId;
	private String _name;

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
