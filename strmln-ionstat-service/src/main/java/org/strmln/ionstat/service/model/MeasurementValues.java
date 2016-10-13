package org.strmln.ionstat.service.model;

import java.util.HashMap;

public class MeasurementValues extends HashMap<String, Object> {

	private static final long serialVersionUID = 8288513758779411513L;

	public void addValue(String measureName, Object value) {
		put(measureName, value);
	}

}
