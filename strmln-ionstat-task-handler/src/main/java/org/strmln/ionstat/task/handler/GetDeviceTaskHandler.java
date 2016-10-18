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

	private static final String SESSIONS_KEY = "sessions";
	private static final String HEADER_INFOS_KEY = "headerInfos";
	private static final String ENTITY_KEY = "entity";
	private static final String TECHNICIAN_HEADER_INFO = "technician";
	private static final String STATUS_HEADER_INFO = "status";
	private static final String SESSION_TEMPLATE_HEADER_INFO = "sessionTemplate";
	private static final String SESSION_PERFORM_DATE_HEADER_INFO = "sessionPerformDate";
	private static final String SESSION_ID_HEADER_INFO = "sessionId";
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
		headerInfos.add(SESSION_ID_HEADER_INFO);
		headerInfos.add(SESSION_PERFORM_DATE_HEADER_INFO);
		headerInfos.add(SESSION_TEMPLATE_HEADER_INFO);
		headerInfos.add(STATUS_HEADER_INFO);
		headerInfos.add(TECHNICIAN_HEADER_INFO);

		TaskResponse response = new TaskResponse();
		response.add(ENTITY_KEY, DeviceConverter.fromInternalToDto(device));
		response.add(HEADER_INFOS_KEY, headerInfos);
		response.add(SESSIONS_KEY, convertSessions(device.getSessions()));

		return response;
	}

	@Override
	protected Task[] getSupportedTasks(Object command) {
		return new Task[] { Task.CREATE_SESSION };
	}

}
