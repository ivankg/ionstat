package org.strmln.ionstat.task.handler;

import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.strmln.ionstat.model.Facility;
import org.strmln.ionstat.model.converters.FacilityConverter;
import org.strmln.ionstat.service.FacilityService;
import org.strmln.ionstat.task.handler.model.Task;
import org.strmln.ionstat.task.handler.model.TaskResponse;
import org.strmln.ionstat.task.handler.model.command.GetEntityCommand;
import org.strmln.ionstat.task.handler.model.command.dto.DtoGetEntityCommand;
import org.strmln.ionstat.task.handler.model.command.dto.DtoTaskHandlerCommand;

public class GetFacilityTaskHandler extends AbstractTaskHandler {

	private static final String ENTITY_KEY = "entity";
	
	@Autowired
	private FacilityService _facilityService;

	@Override
	public Class<? extends DtoTaskHandlerCommand<?>> getCommandClass() {
		return DtoGetEntityCommand.class;
	}

	@Override
	public TaskResponse processTask(Object command, List<Path> files) {
		return null;
	}

	private FacilityService getFacilityService() {
		return _facilityService;
	}

	@Override
	protected TaskResponse executeGetTaskInfos(Object command) {
		GetEntityCommand entityCommand = (GetEntityCommand) command;

		Facility facility = getFacilityService()
				.findById(entityCommand.getId());
		TaskResponse response = new TaskResponse();
		response.add(ENTITY_KEY, FacilityConverter.fromInternalToDto(facility));

		return response;
	}

	@Override
	protected Task[] getSupportedTasks(Object command) {
		return new Task[] { Task.ADD_NEW_DEPARTMENT };
	}

}
