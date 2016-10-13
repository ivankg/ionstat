package org.strmln.ionstat.task.handler;

import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.strmln.ionstat.dto.DisplayEntity;
import org.strmln.ionstat.model.Room;
import org.strmln.ionstat.model.converters.DisplayEntityConverter;
import org.strmln.ionstat.service.RoomService;
import org.strmln.ionstat.task.handler.model.TaskResponse;
import org.strmln.ionstat.task.handler.model.command.GetEntitiesCommand;
import org.strmln.ionstat.task.handler.model.command.dto.DtoGetEntitiesCommand;
import org.strmln.ionstat.task.handler.model.command.dto.DtoTaskHandlerCommand;

public class GetRoomsTaskHandler extends AbstractTaskHandler {

	private static final String ENTITIES_KEY = "entities";
	
	@Autowired
	private RoomService _roomService;

	@Override
	public Class<? extends DtoTaskHandlerCommand<?>> getCommandClass() {
		return DtoGetEntitiesCommand.class;
	}

	@Override
	public TaskResponse processTask(Object command, List<Path> files) {
		return null;
	}

	private RoomService getRoomService() {
		return _roomService;
	}

	@Override
	protected TaskResponse executeGetTaskInfos(Object command) {
		GetEntitiesCommand entitiesCommand = (GetEntitiesCommand) command;
		List<Room> rooms = getRoomService().findRoomsByDepartmentId(
				entitiesCommand.getParentId());

		List<DisplayEntity> displayEntities = DisplayEntityConverter
				.convert(rooms);

		TaskResponse response = new TaskResponse();
		response.add(ENTITIES_KEY, displayEntities);

		return response;
	}

}
