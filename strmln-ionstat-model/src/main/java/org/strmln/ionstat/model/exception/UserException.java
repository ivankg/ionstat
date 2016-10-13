package org.strmln.ionstat.model.exception;

public class UserException extends RuntimeException {

	private static final long serialVersionUID = -7507982412314071345L;

	private String _description;

	public UserException(String message) {
		super(message);
	}

	public UserException(String message, String description) {
		super(message);
		setDescription(description);
	}

	public UserException(String message, String description, Throwable throwable) {
		super(message, throwable);
		setDescription(description);
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

}
