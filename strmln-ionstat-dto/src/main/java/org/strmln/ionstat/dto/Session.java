package org.strmln.ionstat.dto;

import java.io.Serializable;

public class Session implements Serializable {

	private static final long serialVersionUID = 2752362350310576913L;

	private Long _id;
	private String _sessionPerformDate;

	public Long getId() {
		return _id;
	}

	public String getSessionPerformDate() {
		return _sessionPerformDate;
	}

	public void setId(Long id) {
		_id = id;
	}

	public void setSessionPerformDate(String sessionPerformDate) {
		_sessionPerformDate = sessionPerformDate;
	}

}
