package org.strmln.ionstat.task.handler;

import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.strmln.ionstat.model.Department;
import org.strmln.ionstat.model.converters.DepartmentConverter;
import org.strmln.ionstat.service.DepartmentService;
import org.strmln.ionstat.task.handler.model.Task;
import org.strmln.ionstat.task.handler.model.TaskResponse;
import org.strmln.ionstat.task.handler.model.command.GetEntityCommand;
import org.strmln.ionstat.task.handler.model.command.dto.DtoGetEntityCommand;
import org.strmln.ionstat.task.handler.model.command.dto.DtoTaskHandlerCommand;

public class GetDepartmentTaskHandler extends AbstractTaskHandler {

	private static final String ENTITY_KEY = "entity";
	@Autowired
	private DepartmentService _departmentService;

	@Override
	public Class<? extends DtoTaskHandlerCommand<?>> getCommandClass() {
		return DtoGetEntityCommand.class;
	}

	@Override
	public TaskResponse processTask(Object command, List<Path> files) {
		return null;
	}

	private DepartmentService getDepartmentService() {
		return _departmentService;
	}

	@Override
	protected TaskResponse executeGetTaskInfos(Object command) {
		GetEntityCommand entityCommand = (GetEntityCommand) command;

		Department department = getDepartmentService().findById(
				entityCommand.getId());
		TaskResponse response = new TaskResponse();
		response.add(ENTITY_KEY,
				DepartmentConverter.fromInternalToDto(department));

		return response;
	}

	@Override
	protected Task[] getSupportedTasks(Object command) {
		return new Task[] { Task.ADD_NEW_ROOM };
	}

}
