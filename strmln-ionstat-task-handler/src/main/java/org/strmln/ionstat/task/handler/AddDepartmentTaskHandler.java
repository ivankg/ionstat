package org.strmln.ionstat.task.handler;

import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.strmln.ionstat.service.DepartmentService;
import org.strmln.ionstat.task.handler.model.TaskResponse;
import org.strmln.ionstat.task.handler.model.command.AddNewDepartmentCommand;
import org.strmln.ionstat.task.handler.model.command.dto.DtoAddNewDepartmentCommand;
import org.strmln.ionstat.task.handler.model.command.dto.DtoTaskHandlerCommand;

public class AddDepartmentTaskHandler extends AbstractTaskHandler {

	@Autowired
	private DepartmentService _departmentService;

	@Override
	public Class<? extends DtoTaskHandlerCommand<?>> getCommandClass() {
		return DtoAddNewDepartmentCommand.class;
	}

	@Override
	public TaskResponse processTask(Object command, List<Path> files) {
		AddNewDepartmentCommand internalCommand = (AddNewDepartmentCommand) command;

		getDepartmentService().addNewDepartment(internalCommand.getParentId(),
				internalCommand.getName());

		return new TaskResponse();
	}

	private DepartmentService getDepartmentService() {
		return _departmentService;
	}

	@Override
	protected TaskResponse executeGetTaskInfos(Object command) {
		return null;
	}

}
