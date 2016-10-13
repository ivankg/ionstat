package org.strmln.ionstat.task.handler;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.strmln.ionstat.model.Device;
import org.strmln.ionstat.model.Session;
import org.strmln.ionstat.model.converters.DeviceConverter;
import org.strmln.ionstat.model.views.SessionView;
import org.strmln.ionstat.service.DeviceService;
import org.strmln.ionstat.task.handler.model.Task;
import org.strmln.ionstat.task.handler.model.TaskResponse;
import org.strmln.ionstat.task.handler.model.command.GetEntityCommand;
import org.strmln.ionstat.task.handler.model.command.dto.DtoGetEntityCommand;
import org.strmln.ionstat.task.handler.model.command.dto.DtoTaskHandlerCommand;

public class GetDeviceTaskHandler extends AbstractTaskHandler {

	@Autowired
	private DeviceService _deviceService;

	@Override
	public Class<? extends DtoTaskHandlerCommand<?>> getCommandClass() {
		return DtoGetEntityCommand.class;
	}

	@Override
	public TaskResponse processTask(Object command, List<Path> files) {
		return null;
	}

	private SessionView[] convertSessions(Set<Session> sessions) {
		SessionView[] result = new SessionView[sessions.size()];
		int i = 0;
		for (Session session : sessions) {
			SessionView sessionView = new SessionView();
			sessionView.setSessionId(session.getSessionId());
			sessionView.setSessionPerformedDate(session.getSessionPerformDate());
			sessionView.setSessionTemplate(session.getSessionTemplate().getName());
			sessionView.setStatus(session.getStatus().name());
			// TODO set technician
			sessionView.setTechnician("");
			result[i] = sessionView;
			i++;
		}
		return result;
	}

	private DeviceService getDeviceService() {
		return _deviceService;
	}

	@Override
	protected TaskResponse executeGetTaskInfos(Object command) {
		GetEntityCommand entityCommand = (GetEntityCommand) command;

		Device device = getDeviceService().findByIdWithSessionAndSessionTemplate(entityCommand.getId());

		List<String> headerInfos = new ArrayList<>();
		headerInfos.add("sessionId");
		headerInfos.add("sessionPerformDate");
		headerInfos.add("sessionTemplate");
		headerInfos.add("status");
		headerInfos.add("technician");

		TaskResponse response = new TaskResponse();
		response.add("entity", DeviceConverter.fromInternalToDto(device));
		response.add("headerInfos", headerInfos);
		response.add("sessions", convertSessions(device.getSessions()));

		return response;
	}

	@Override
	protected Task[] getSupportedTasks(Object command) {
		return new Task[] { Task.CREATE_SESSION };
	}

}
