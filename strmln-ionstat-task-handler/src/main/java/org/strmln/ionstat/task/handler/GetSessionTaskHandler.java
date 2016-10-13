package org.strmln.ionstat.task.handler;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.strmln.ionstat.model.Measure;
import org.strmln.ionstat.model.MeasuredValue;
import org.strmln.ionstat.model.Measurement;
import org.strmln.ionstat.model.Session;
import org.strmln.ionstat.model.SessionStatus;
import org.strmln.ionstat.service.SessionService;
import org.strmln.ionstat.service.model.HeaderInfo;
import org.strmln.ionstat.service.model.MeasurementInfo;
import org.strmln.ionstat.service.model.MeasurementValues;
import org.strmln.ionstat.task.handler.model.Task;
import org.strmln.ionstat.task.handler.model.TaskResponse;
import org.strmln.ionstat.task.handler.model.command.GetEntityCommand;
import org.strmln.ionstat.task.handler.model.command.dto.DtoGetEntityCommand;
import org.strmln.ionstat.task.handler.model.command.dto.DtoTaskHandlerCommand;

public class GetSessionTaskHandler extends AbstractTaskHandler {

	private static final String SESSION_TEMPLATE_INFO_KEY = "sessionTemplateInfo";
	private static final String MEASUREMENT_INFO_KEY = "measurementInfo";
	
	@Autowired
	private SessionService _sessionService;

	@Override
	public Class<? extends DtoTaskHandlerCommand<?>> getCommandClass() {
		return DtoGetEntityCommand.class;
	}

	@Override
	public TaskResponse processTask(Object command, List<Path> files) {
		return null;
	}

	private MeasurementInfo createMeasurementInfo(List<Measurement> measurements) {
		MeasurementInfo measurementInfo = new MeasurementInfo();

		List<HeaderInfo> headerInfos = new ArrayList<>();
		Measurement measurementForHeader = measurements.get(0);
		for (MeasuredValue measuredValue : measurementForHeader
				.getMeasuredValues()) {
			Measure measure = measuredValue.getMeasure();
			HeaderInfo headerInfo = new HeaderInfo(measure.getName(),
					measure.getUnit(), null);
			headerInfos.add(headerInfo);
		}

		List<MeasurementValues> measurementsValues = new ArrayList<>();
		for (Measurement measurement : measurements) {
			MeasurementValues measurementValues = new MeasurementValues();
			for (MeasuredValue measuredValue : measurement.getMeasuredValues()) {
				measurementValues.addValue(
						measuredValue.getMeasure().getName(),
						measuredValue.getValue());
			}
			measurementsValues.add(measurementValues);
		}

		measurementInfo.setHeaderInfos(headerInfos);
		measurementInfo.setMeasurements(measurementsValues);

		return measurementInfo;
	}

	private SessionService getSessionService() {
		return _sessionService;
	}

	@Override
	protected TaskResponse executeGetTaskInfos(Object command) {
		GetEntityCommand internalCommand = (GetEntityCommand) command;
		Long sessionId = internalCommand.getId();

		Session session = getSessionService().findByIdWithDependents(sessionId);

		List<Measurement> recordedMeasurements = session.getMeasurements();
		MeasurementInfo measurementInfo = createMeasurementInfo(recordedMeasurements);

		List<Measurement> nominalMeasurements = session.getSessionTemplate()
				.getNominalMeasurements();
		MeasurementInfo sessionTemplateInfo = createMeasurementInfo(nominalMeasurements);

		TaskResponse response = new TaskResponse();
		response.add(MEASUREMENT_INFO_KEY, measurementInfo);
		response.add(SESSION_TEMPLATE_INFO_KEY, sessionTemplateInfo);

		return response;
	}

	@Override
	protected Task[] getSupportedTasks(Object command) {
		GetEntityCommand internalCommand = (GetEntityCommand) command;
		Session session = getSessionService().findById(internalCommand.getId());

		List<Task> result = new ArrayList<>();
		result.add(Task.DOWNLOAD_REPORT);
		result.add(Task.DELETE_SESSION);
		if (session.getStatus().equals(SessionStatus.PENDING)) {
			result.add(Task.APPROVE_SESSION);
		}
		return result.toArray(new Task[result.size()]);
	}

}
