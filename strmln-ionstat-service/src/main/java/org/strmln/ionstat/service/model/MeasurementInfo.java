package org.strmln.ionstat.service.model;

import java.util.List;

public class MeasurementInfo {

	private List<HeaderInfo> _headerInfos;
	private List<MeasurementValues> _measurements;

	public List<HeaderInfo> getHeaderInfos() {
		return _headerInfos;
	}

	public List<MeasurementValues> getMeasurements() {
		return _measurements;
	}

	public void setHeaderInfos(List<HeaderInfo> headerInfos) {
		_headerInfos = headerInfos;
	}

	public void setMeasurements(List<MeasurementValues> measurements) {
		_measurements = measurements;
	}

}
