package org.strmln.ionstat.task.handler;

import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.strmln.ionstat.service.DeviceService;
import org.strmln.ionstat.task.handler.model.TaskResponse;
import org.strmln.ionstat.task.handler.model.command.AddNewDeviceCommand;
import org.strmln.ionstat.task.handler.model.command.dto.DtoAddNewDeviceCommand;
import org.strmln.ionstat.task.handler.model.command.dto.DtoTaskHandlerCommand;

public class AddDeviceTaskHandler extends AbstractTaskHandler {

	@Autowired
	private DeviceService _deviceService;

	@Override
	public Class<? extends DtoTaskHandlerCommand<?>> getCommandClass() {
		return DtoAddNewDeviceCommand.class;
	}

	@Override
	public TaskResponse processTask(Object command, List<Path> files) {
		AddNewDeviceCommand internalCommand = (AddNewDeviceCommand) command;

		getDeviceService().addNewDevice(internalCommand.getParentId(),
				internalCommand.getName(), internalCommand.getManufacturer(),
				internalCommand.getModel(), internalCommand.getSerialNumber(),
				internalCommand.getDeviceUsage());

		return new TaskResponse();
	}

	private DeviceService getDeviceService() {
		return _deviceService;
	}

	@Override
	protected TaskResponse executeGetTaskInfos(Object command) {
		return null;
	}

}
