package org.strmln.ionstat.task.handler;

import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.strmln.ionstat.dto.DisplayEntity;
import org.strmln.ionstat.model.Facility;
import org.strmln.ionstat.model.context.UserProfileContext;
import org.strmln.ionstat.model.converters.DisplayEntityConverter;
import org.strmln.ionstat.service.FacilityService;
import org.strmln.ionstat.task.handler.model.TaskResponse;
import org.strmln.ionstat.task.handler.model.command.dto.DtoGetEntitiesCommand;
import org.strmln.ionstat.task.handler.model.command.dto.DtoTaskHandlerCommand;

public class GetFacilitiesTaskHandler extends AbstractTaskHandler {

	@Autowired
	private FacilityService _facilityService;

	@Override
	public Class<? extends DtoTaskHandlerCommand<?>> getCommandClass() {
		return DtoGetEntitiesCommand.class;
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
		List<Facility> facilities = getFacilityService().findUserFacilities(
				UserProfileContext.getCurrentUserProfile().getIdentifier());

		List<DisplayEntity> displayEntities = DisplayEntityConverter
				.convert(facilities);

		TaskResponse response = new TaskResponse();
		response.add("entities", displayEntities);

		return response;
	}

}
