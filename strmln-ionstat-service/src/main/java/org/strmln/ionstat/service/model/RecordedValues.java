package org.strmln.ionstat.service.model;

import java.util.List;

public class RecordedValues {
	
	private Long _measureId;
	private List<Object> _recordedValues;

	public RecordedValues(Long measureId, List<Object> recordedValues) {
		_measureId = measureId;
		_recordedValues = recordedValues;
	}

	public List<Object> getRecordedValues() {
		return _recordedValues;
	}

	public Long getMeasureId() {
		return _measureId;
	}

	public void setRecordedValues(List<Object> recordedValues) {
		_recordedValues = recordedValues;
	}

	public void setMeasureId(Long measureId) {
		_measureId = measureId;
	}
}
