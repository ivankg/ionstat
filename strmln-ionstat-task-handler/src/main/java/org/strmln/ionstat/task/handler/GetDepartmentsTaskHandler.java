package org.strmln.ionstat.task.handler;

import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.strmln.ionstat.dto.DisplayEntity;
import org.strmln.ionstat.model.Department;
import org.strmln.ionstat.model.converters.DisplayEntityConverter;
import org.strmln.ionstat.service.DepartmentService;
import org.strmln.ionstat.task.handler.model.TaskResponse;
import org.strmln.ionstat.task.handler.model.command.GetEntitiesCommand;
import org.strmln.ionstat.task.handler.model.command.dto.DtoGetEntitiesCommand;
import org.strmln.ionstat.task.handler.model.command.dto.DtoTaskHandlerCommand;

public class GetDepartmentsTaskHandler extends AbstractTaskHandler {

	@Autowired
	private DepartmentService _departmentService;

	@Override
	public Class<? extends DtoTaskHandlerCommand<?>> getCommandClass() {
		return DtoGetEntitiesCommand.class;
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
		GetEntitiesCommand entitiesCommand = (GetEntitiesCommand) command;
		List<Department> departments = getDepartmentService()
				.findDepartmentsByFacilityId(entitiesCommand.getParentId());

		List<DisplayEntity> displayEntities = DisplayEntityConverter
				.convert(departments);

		TaskResponse response = new TaskResponse();
		response.add("entities", displayEntities);

		return response;
	}

}
