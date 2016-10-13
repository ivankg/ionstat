package org.strmln.ionstat.task.handler.model.command.dto;

import java.util.ArrayList;
import java.util.List;

import org.strmln.ionstat.service.model.RecordedValues;
import org.strmln.ionstat.task.handler.model.command.CreateSessionCommand;

public class DtoCreateSessionCommand implements DtoTaskHandlerCommand<CreateSessionCommand> {

	private static final String NUMERIC_MATCH_REGEX = "\\d+(\\.\\d+)?";

	public static class DtoMeasurementData {
		private Long _measureId;
		private String[] _values;

		public Long getMeasureId() {
			return _measureId;
		}

		public String[] getValues() {
			return _values;
		}

		public void setMeasureId(Long measureId) {
			_measureId = measureId;
		}

		public void setValues(String[] values) {
			_values = values;
		}

	}

	private DtoMeasurementData[] _measurementData;
	private Long _sessionTemplateId;
	private Long _deviceId;

	@Override
	public CreateSessionCommand convertToInternalTaskHandlerCommand() {
		CreateSessionCommand internalCommand = new CreateSessionCommand();
		List<RecordedValues> internalMeasurementValues = new ArrayList<>();
		DtoMeasurementData[] measurementData = getMeasurementData();
		if (measurementData != null) {
			for (DtoMeasurementData data : measurementData) {
				List<Object> measuredValues = new ArrayList<>();
				for (String value : data.getValues()) {
					Object internalValue = extractValue(value);
					measuredValues.add(internalValue);
				}
				RecordedValues internalMeasurementValuesByMeasurement = new RecordedValues(data.getMeasureId(),
						measuredValues);
				internalMeasurementValues.add(internalMeasurementValuesByMeasurement);
			}

			internalCommand.setMeasurementValues(internalMeasurementValues);
			internalCommand.setSessionTemplateId(getSessionTemplateId());
		}
		internalCommand.setDeviceId(getDeviceId());

		return internalCommand;
	}

	public Long getDeviceId() {
		return _deviceId;
	}

	public DtoMeasurementData[] getMeasurementData() {
		return _measurementData;
	}

	public Long getSessionTemplateId() {
		return _sessionTemplateId;
	}

	public void setDeviceId(Long deviceId) {
		_deviceId = deviceId;
	}

	public void setMeasurementData(DtoMeasurementData[] measurementData) {
		_measurementData = measurementData;
	}

	public void setSessionTemplateId(Long sessionTemplateId) {
		_sessionTemplateId = sessionTemplateId;
	}

	private Object extractValue(String value) {
		Object result = value;
		if (isNumeric(value)) {
			result = Double.parseDouble(value);
		}
		return result;
	}

	private boolean isNumeric(String str) {
		return str.matches(NUMERIC_MATCH_REGEX);
	}

}
