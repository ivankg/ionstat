package org.strmln.ionstat.task.handler.model.command.dto;

public interface DtoTaskHandlerCommand<T> {

	T convertToInternalTaskHandlerCommand();
}
