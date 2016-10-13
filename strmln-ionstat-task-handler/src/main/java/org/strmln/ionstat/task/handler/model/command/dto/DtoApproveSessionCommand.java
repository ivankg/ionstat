package org.strmln.ionstat.task.handler.model.command.dto;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.strmln.ionstat.model.SessionStatus;
import org.strmln.ionstat.task.handler.model.command.ApproveSessionCommand;

public class DtoApproveSessionCommand implements DtoTaskHandlerCommand<ApproveSessionCommand> {

	private static final String UTF_8_ENCODING = "UTF-8";

	private Long _id;
	private String _status;
	private String _comment;

	@Override
	public ApproveSessionCommand convertToInternalTaskHandlerCommand() {
		ApproveSessionCommand result = new ApproveSessionCommand();
		result.setSessionId(getId());
		result.setStatus(SessionStatus.valueOf(getStatus()));
		try {
			result.setComment(URLDecoder.decode(getComment(), UTF_8_ENCODING));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	public String getComment() {
		return _comment;
	}

	public Long getId() {
		return _id;
	}

	public String getStatus() {
		return _status;
	}

	public void setComment(String comment) {
		_comment = comment;
	}

	public void setId(Long id) {
		_id = id;
	}

	public void setStatus(String status) {
		_status = status;
	}

}
