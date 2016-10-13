package org.strmln.ionstat.mvc.commands;

public class TaskCommand extends JsonCommand {

	private String _taskName;
	private String _jsonTaskData;

	public String getJsonTaskData() {
		return _jsonTaskData;
	}

	public String getTaskName() {
		return _taskName;
	}

	public void setJsonTaskData(String jsonTaskData) {
		_jsonTaskData = jsonTaskData;
	}

	public void setTaskName(String taskName) {
		_taskName = taskName;
	}

}
