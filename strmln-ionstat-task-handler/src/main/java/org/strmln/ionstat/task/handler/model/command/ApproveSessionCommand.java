package org.strmln.ionstat.task.handler.model.command;

import org.strmln.ionstat.model.SessionStatus;

public class ApproveSessionCommand {

	private Long _sessionId;
	private SessionStatus _status;
	private String _comment;

	public String getComment() {
		return _comment;
	}

	public Long getSessionId() {
		return _sessionId;
	}

	public SessionStatus getStatus() {
		return _status;
	}

	public void setComment(String comment) {
		_comment = comment;
	}

	public void setSessionId(Long sessionId) {
		_sessionId = sessionId;
	}

	public void setStatus(SessionStatus status) {
		_status = status;
	}

}
