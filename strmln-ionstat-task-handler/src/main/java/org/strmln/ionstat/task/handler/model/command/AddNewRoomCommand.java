package org.strmln.ionstat.task.handler.model.command;

public class AddNewRoomCommand {

	private Long _parentId;
	private String _name;
	private Long _inspectionInterval;

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
