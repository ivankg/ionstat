package org.strmln.ionstat.model.views;

import java.util.Date;

public class SessionView {

	private Long _sessionId;
	private Date _sessionPerformedDate;
	private String _status;
	private String _sessionTemplate;
	private String _technician;

	public Long getSessionId() {
		return _sessionId;
	}

	public Date getSessionPerformedDate() {
		return _sessionPerformedDate;
	}

	public String getSessionTemplate() {
		return _sessionTemplate;
	}

	public String getStatus() {
		return _status;
	}

	public String getTechnician() {
		return _technician;
	}

	public void setSessionId(Long sessionId) {
		_sessionId = sessionId;
	}

	public void setSessionPerformedDate(Date sessionPerformedDate) {
		_sessionPerformedDate = sessionPerformedDate;
	}

	public void setSessionTemplate(String sessionTemplate) {
		_sessionTemplate = sessionTemplate;
	}

	public void setStatus(String status) {
		_status = status;
	}

	public void setTechnician(String technician) {
		_technician = technician;
	}

}
