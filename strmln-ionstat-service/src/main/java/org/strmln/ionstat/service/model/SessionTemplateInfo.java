package org.strmln.ionstat.service.model;

public class SessionTemplateInfo {

	private MeasurementInfo _nominalMeasurementInfo;
	private MeasurementInfo _inputMeasurementInfo;
	private String _name;
	private Long _sessionTemplateId;
	private String _description;
	private Boolean _uploadRequired;

	public String getDescription() {
		return _description;
	}

	public MeasurementInfo getInputMeasurementInfo() {
		return _inputMeasurementInfo;
	}

	public String getName() {
		return _name;
	}

	public MeasurementInfo getNominalMeasurementInfo() {
		return _nominalMeasurementInfo;
	}

	public Long getSessionTemplateId() {
		return _sessionTemplateId;
	}

	public Boolean getUploadRequired() {
		return _uploadRequired;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setInputMeasurementInfo(MeasurementInfo inputMeasurementInfo) {
		_inputMeasurementInfo = inputMeasurementInfo;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setNominalMeasurementInfo(MeasurementInfo nominalMeasurementInfo) {
		_nominalMeasurementInfo = nominalMeasurementInfo;
	}

	public void setSessionTemplateId(Long sessionTemplateId) {
		_sessionTemplateId = sessionTemplateId;
	}

	public void setUploadRequired(Boolean uploadRequired) {
		_uploadRequired = uploadRequired;
	}

}
