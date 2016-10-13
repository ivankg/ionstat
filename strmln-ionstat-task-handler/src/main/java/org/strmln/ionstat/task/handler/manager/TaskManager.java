package org.strmln.ionstat.task.handler.manager;

import java.util.Map;

import org.strmln.ionstat.task.handler.AbstractTaskHandler;
import org.strmln.ionstat.task.handler.TaskHandler;

public interface TaskManager {

	TaskHandler getTaskHandler(String taskName);

	Map<String, AbstractTaskHandler> getTaskHandlerMap();

}
