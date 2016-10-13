package org.strmln.ionstat.task.handler;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.strmln.ionstat.model.Measure;
import org.strmln.ionstat.model.MeasuredValue;
import org.strmln.ionstat.model.Measurement;
import org.strmln.ionstat.model.SessionTemplate;
import org.strmln.ionstat.model.SessionTemplateMeasure;
import org.strmln.ionstat.model.SessionTemplateMeasureType;
import org.strmln.ionstat.service.SessionService;
import org.strmln.ionstat.service.SessionTemplateService;
import org.strmln.ionstat.service.model.HeaderInfo;
import org.strmln.ionstat.service.model.MeasurementInfo;
import org.strmln.ionstat.service.model.MeasurementValues;
import org.strmln.ionstat.service.model.SessionTemplateInfo;
import org.strmln.ionstat.task.handler.model.TaskResponse;
import org.strmln.ionstat.task.handler.model.command.CreateSessionCommand;
import org.strmln.ionstat.task.handler.model.command.dto.DtoCreateSessionCommand;
import org.strmln.ionstat.task.handler.model.command.dto.DtoTaskHandlerCommand;

public class CreateSessionTaskHandler extends AbstractTaskHandler {

	private static final String SESSION_TEMPLATES_KEY = "sessionTemplates";

	@Autowired
	private SessionTemplateService _sessionTemplateService;

	@Autowired
	private SessionService _sessionService;

	@Override
	public Class<? extends DtoTaskHandlerCommand<?>> getCommandClass() {
		return DtoCreateSessionCommand.class;
	}

	@Override
	public TaskResponse processTask(Object command, List<Path> files) {
		CreateSessionCommand internalComannd = (CreateSessionCommand) command;

		getSessionService().createNewSession(
				internalComannd.getSessionTemplateId(),
				internalComannd.getDeviceId(),
				internalComannd.getMeasurementValues());

		return new TaskResponse();
	}

	private void addHeaderInfos(SessionTemplate sessionTemplate,
			MeasurementInfo measurementInfo) {
		List<MeasuredValue> measuredValues = sessionTemplate
				.getNominalMeasurements().get(0).getMeasuredValues();
		List<HeaderInfo> headerInfos = new ArrayList<>();
		for (MeasuredValue measuredValue : measuredValues) {
			Measure measure = measuredValue.getMeasure();
			HeaderInfo headerInfo = new HeaderInfo(measure.getName(),
					measure.getUnit(), null);
			headerInfos.add(headerInfo);
		}
		measurementInfo.setHeaderInfos(headerInfos);
	}

	private void addMeasurements(SessionTemplate sessionTemplate,
			MeasurementInfo measurementInfo) {
		List<MeasurementValues> measurements = new ArrayList<>();
		for (Measurement measurement : sessionTemplate.getNominalMeasurements()) {
			MeasurementValues measurementValues = new MeasurementValues();
			for (MeasuredValue measuredValue : measurement.getMeasuredValues()) {
				measurementValues.addValue(
						measuredValue.getMeasure().getName(),
						measuredValue.getValue());
			}
			measurements.add(measurementValues);
		}
		measurementInfo.setMeasurements(measurements);
	}

	private List<SessionTemplateInfo> createSessionTemplateInfos(
			List<SessionTemplate> sessionTemplates) {
		List<SessionTemplateInfo> templateInfos = new ArrayList<>();

		for (SessionTemplate sessionTemplate : sessionTemplates) {
			SessionTemplateInfo templateInfo = new SessionTemplateInfo();
			templateInfo.setSessionTemplateId(sessionTemplate
					.getSessionTemplateId());
			templateInfo.setName(sessionTemplate.getName());
			templateInfo.setDescription(sessionTemplate.getDescription());

			MeasurementInfo nominalMeasurementInfo = new MeasurementInfo();
			addHeaderInfos(sessionTemplate, nominalMeasurementInfo);
			addMeasurements(sessionTemplate, nominalMeasurementInfo);

			templateInfo.setNominalMeasurementInfo(nominalMeasurementInfo);

			MeasurementInfo inputMeasurementInfo = new MeasurementInfo();
			List<HeaderInfo> inputHeaderInfos = new ArrayList<>();
			List<String> measureNames = new ArrayList<>();
			for (SessionTemplateMeasure sessionTemplateMeasure : sessionTemplate
					.getSessionTemplateMeasures()) {
				if (sessionTemplateMeasure.getType() == SessionTemplateMeasureType.INPUT) {
					Measure measure = sessionTemplateMeasure.getMeasure();
					String measureName = measure.getName();
					HeaderInfo headerInfo = new HeaderInfo(measureName,
							measure.getUnit(), measure.getMeasureId());
					inputHeaderInfos.add(headerInfo);
					measureNames.add(measureName);
				}
			}

			// delete this after UI fix
			List<MeasurementValues> inputMeasurementValuesRows = new ArrayList<>();
			for (int i = 0; i < nominalMeasurementInfo.getMeasurements().size(); i++) {
				MeasurementValues inputMeasurementValues = new MeasurementValues();
				for (String measureName : measureNames) {
					inputMeasurementValues.addValue(measureName, null);
				}
				inputMeasurementValuesRows.add(inputMeasurementValues);
			}

			inputMeasurementInfo.setHeaderInfos(inputHeaderInfos);
			inputMeasurementInfo.setMeasurements(inputMeasurementValuesRows);
			templateInfo.setInputMeasurementInfo(inputMeasurementInfo);

			templateInfo.setUploadRequired(sessionTemplate.getUploadRequired());

			templateInfos.add(templateInfo);
		}

		return templateInfos;
	}

	private SessionService getSessionService() {
		return _sessionService;
	}

	private SessionTemplateService getSessionTemplateService() {
		return _sessionTemplateService;
	}

	@Override
	protected TaskResponse executeGetTaskInfos(Object command) {
		CreateSessionCommand createSessionCommand = (CreateSessionCommand) command;
		List<SessionTemplate> sessionTemplates = getSessionTemplateService()
				.findSessionTemplatesByDeviceId(
						createSessionCommand.getDeviceId());
		List<SessionTemplateInfo> sessionTemplateInfos = createSessionTemplateInfos(sessionTemplates);

		TaskResponse response = new TaskResponse();
		response.add(SESSION_TEMPLATES_KEY, sessionTemplateInfos);
		return response;
	}

}
