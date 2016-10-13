package org.strmln.ionstat.service.model;

public class HeaderInfo {

	private String _measureName;
	private String _measureUnit;
	private Long _measureId;

	public HeaderInfo() {
	}

	public HeaderInfo(String measureName, String measureUnit, Long measureId) {
		_measureName = measureName;
		_measureUnit = measureUnit;
		_measureId = measureId;
	}

	public Long getMeasureId() {
		return _measureId;
	}

	public String getMeasureName() {
		return _measureName;
	}

	public String getMeasureUnit() {
		return _measureUnit;
	}

	public void setMeasureId(Long measureId) {
		_measureId = measureId;
	}

	public void setMeasureName(String measureName) {
		_measureName = measureName;
	}

	public void setMeasureUnit(String measureUnit) {
		_measureUnit = measureUnit;
	}
}
