package org.strmln.ionstat.task.handler.model.command;

import java.util.List;

import org.strmln.ionstat.service.model.RecordedValues;

public class CreateSessionCommand {

	private Long _sessionTemplateId;
	private Long _deviceId;
	private List<RecordedValues> _measurementValues;

	public Long getDeviceId() {
		return _deviceId;
	}

	public List<RecordedValues> getMeasurementValues() {
		return _measurementValues;
	}

	public Long getSessionTemplateId() {
		return _sessionTemplateId;
	}

	public void setDeviceId(Long deviceId) {
		_deviceId = deviceId;
	}

	public void setMeasurementValues(List<RecordedValues> measurementValues) {
		_measurementValues = measurementValues;
	}

	public void setSessionTemplateId(Long sessionTemplateId) {
		_sessionTemplateId = sessionTemplateId;
	}

}
