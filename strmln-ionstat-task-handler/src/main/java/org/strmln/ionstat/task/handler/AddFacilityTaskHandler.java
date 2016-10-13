package org.strmln.ionstat.task.handler;

import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.strmln.ionstat.service.FacilityService;
import org.strmln.ionstat.task.handler.model.TaskResponse;
import org.strmln.ionstat.task.handler.model.command.AddNewFacilityCommand;
import org.strmln.ionstat.task.handler.model.command.dto.DtoAddNewFacilityCommand;
import org.strmln.ionstat.task.handler.model.command.dto.DtoTaskHandlerCommand;

public class AddFacilityTaskHandler extends AbstractTaskHandler {

	@Autowired
	private FacilityService _facilityService;

	@Override
	public Class<? extends DtoTaskHandlerCommand<?>> getCommandClass() {
		return DtoAddNewFacilityCommand.class;
	}

	@Override
	public TaskResponse processTask(Object command, List<Path> files) {
		AddNewFacilityCommand facilityCommand = (AddNewFacilityCommand) command;

		getFacilityService().addNewFacility(facilityCommand.getAddress(),
				facilityCommand.getCity(), facilityCommand.getContactPerson(),
				facilityCommand.getEmail(), facilityCommand.getName(),
				facilityCommand.getPhone(), facilityCommand.getPib());

		return new TaskResponse();
	}

	private FacilityService getFacilityService() {
		return _facilityService;
	}

	@Override
	protected TaskResponse executeGetTaskInfos(Object command) {
		return null;
	}

}
