package org.strmln.ionstat.task.handler.manager;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.strmln.ionstat.model.Policy;
import org.strmln.ionstat.model.context.UserProfileContext;
import org.strmln.ionstat.model.exception.UserException;
import org.strmln.ionstat.task.handler.AbstractTaskHandler;
import org.strmln.ionstat.task.handler.TaskHandler;

public class DefaultTaskManager implements TaskManager {

	private Map<String, AbstractTaskHandler> _taskHandlerMap;

	@Override
	public TaskHandler getTaskHandler(String taskName) {
		TaskHandler taskHandler = getTaskHandlerMap().get(taskName);
		if (taskHandler == null) {
			throw new RuntimeException(String.format(Messages.getString("DefaultTaskManager.0"), taskName)); //$NON-NLS-1$
		}

		Set<Policy> currentUserPolicies = UserProfileContext.getCurrentUserProfile().getRole().getPolicies();
		Iterator<Policy> iterator = currentUserPolicies.iterator();

		boolean policyFound = false;
		while (iterator.hasNext() || !policyFound) {
			Policy policy = iterator.next();
			if (policy.getPolicyId().equals(taskHandler.getPolicy())) {
				policyFound = true;
			}

		}

		if (!policyFound) {
			throw new UserException(Messages.getString("DefaultTaskManager.1")); //$NON-NLS-1$
		}

		return taskHandler;
	}

	public Map<String, AbstractTaskHandler> getTaskHandlerMap() {
		return _taskHandlerMap;
	}

	public void setTaskHandlerMap(Map<String, AbstractTaskHandler> taskHandlerMap) {
		_taskHandlerMap = taskHandlerMap;
	}

}
