package org.strmln.ionstat.task.handler;

import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.strmln.ionstat.model.Room;
import org.strmln.ionstat.model.converters.RoomConverter;
import org.strmln.ionstat.service.RoomService;
import org.strmln.ionstat.task.handler.model.Task;
import org.strmln.ionstat.task.handler.model.TaskResponse;
import org.strmln.ionstat.task.handler.model.command.GetEntityCommand;
import org.strmln.ionstat.task.handler.model.command.dto.DtoGetEntityCommand;
import org.strmln.ionstat.task.handler.model.command.dto.DtoTaskHandlerCommand;

public class GetRoomTaskHandler extends AbstractTaskHandler {

	private static final String ENTITY_KEY = "entity";
	
	@Autowired
	private RoomService _roomService;

	@Override
	public Class<? extends DtoTaskHandlerCommand<?>> getCommandClass() {
		return DtoGetEntityCommand.class;
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
		GetEntityCommand entityCommand = (GetEntityCommand) command;

		Room room = getRoomService().findById(entityCommand.getId());
		TaskResponse response = new TaskResponse();
		response.add(ENTITY_KEY, RoomConverter.fromInternalToDto(room));

		return response;
	}

	@Override
	protected Task[] getSupportedTasks(Object command) {
		return new Task[] { Task.ADD_NEW_DEVICE };
	}

}
