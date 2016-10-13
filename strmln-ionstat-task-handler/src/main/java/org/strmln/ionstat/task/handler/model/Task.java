package org.strmln.ionstat.task.handler.model;

public enum Task {

	ADD_NEW_FACILITY("addNewFacility"), ADD_NEW_DEPARTMENT("addNewDepartment"), ADD_NEW_ROOM(
			"addNewRoom"), ADD_NEW_DEVICE("addNewDevice"), CREATE_SESSION(
			"createSession"), APPROVE_SESSION("approveSession"), DELETE_SESSION(
			"deleteSession"), DOWNLOAD_REPORT("downloadReport");

	private String _name;

	private Task(String name) {
		_name = name;
	}

	public String getTaskName() {
		return _name;
	}

}
