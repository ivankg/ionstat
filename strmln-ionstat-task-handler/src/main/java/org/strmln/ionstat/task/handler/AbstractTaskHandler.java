package org.strmln.ionstat.task.handler;

import org.strmln.ionstat.task.handler.model.Task;
import org.strmln.ionstat.task.handler.model.TaskResponse;

public abstract class AbstractTaskHandler implements TaskHandler {

	private String _policy;

	public String getPolicy() {
		return _policy;
	}

	@Override
	public TaskResponse getTaskInfos(Object command) {
		TaskResponse taskInfoResponse = executeGetTaskInfos(command);
		taskInfoResponse.setSupportedTasks(getSupportedTasks(command));
		return taskInfoResponse;
	}

	public void setPolicy(String policy) {
		_policy = policy;
	}

	protected abstract TaskResponse executeGetTaskInfos(Object command);

	protected Task[] getSupportedTasks(Object command) {
		return null;
	}

}
