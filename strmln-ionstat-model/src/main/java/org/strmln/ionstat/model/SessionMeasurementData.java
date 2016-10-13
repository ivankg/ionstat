package org.strmln.ionstat.model;

import java.util.List;
import java.util.Map;

public class SessionMeasurementData {

	private List<String> _header;
	private Map<Long, List<Double>> _data;

	public SessionMeasurementData(List<String> header,
			Map<Long, List<Double>> data) {
		_header = header;
		_data = data;
	}

	public Map<Long, List<Double>> getData() {
		return _data;
	}

	public List<String> getHeader() {
		return _header;
	}

	public void setData(Map<Long, List<Double>> data) {
		_data = data;
	}

	public void setHeader(List<String> header) {
		_header = header;
	}

}
