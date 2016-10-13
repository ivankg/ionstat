package org.strmln.ionstat.task.handler;

import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.strmln.ionstat.service.RoomService;
import org.strmln.ionstat.task.handler.model.TaskResponse;
import org.strmln.ionstat.task.handler.model.command.AddNewRoomCommand;
import org.strmln.ionstat.task.handler.model.command.dto.DtoAddNewRoomCommand;
import org.strmln.ionstat.task.handler.model.command.dto.DtoTaskHandlerCommand;

public class AddRoomTaskHandler extends AbstractTaskHandler {

	@Autowired
	private RoomService _roomService;

	@Override
	public Class<? extends DtoTaskHandlerCommand<?>> getCommandClass() {
		return DtoAddNewRoomCommand.class;
	}

	@Override
	public TaskResponse processTask(Object command, List<Path> files) {
		AddNewRoomCommand internalCommand = (AddNewRoomCommand) command;

		getRoomService().addNewRoom(internalCommand.getParentId(),
				internalCommand.getName(),
				internalCommand.getInspectionInterval());

		return new TaskResponse();
	}

	private RoomService getRoomService() {
		return _roomService;
	}

	@Override
	protected TaskResponse executeGetTaskInfos(Object command) {
		return null;
	}

}
