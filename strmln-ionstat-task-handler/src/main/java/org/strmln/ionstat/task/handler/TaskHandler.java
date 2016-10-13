package org.strmln.ionstat.task.handler;

import java.nio.file.Path;
import java.util.List;

import org.strmln.ionstat.task.handler.model.TaskResponse;
import org.strmln.ionstat.task.handler.model.command.dto.DtoTaskHandlerCommand;

public interface TaskHandler {

	Class<? extends DtoTaskHandlerCommand<?>> getCommandClass();

	TaskResponse getTaskInfos(Object command);

	TaskResponse processTask(Object command, List<Path> files);

	String getPolicy();

}
