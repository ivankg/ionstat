package org.strmln.ionstat.dto;

import java.io.Serializable;

public class Room implements Serializable {

	private static final long serialVersionUID = -2633003798069035042L;

	private Long _id;
	private String _name;
	private Long _inspectionInterval;

	public Long getId() {
		return _id;
	}

	public Long getInspectionInterval() {
		return _inspectionInterval;
	}

	public String getName() {
		return _name;
	}

	public void setId(Long id) {
		_id = id;
	}

	public void setInspectionInterval(Long inspectionInterval) {
		_inspectionInterval = inspectionInterval;
	}

	public void setName(String name) {
		_name = name;
	}

}
