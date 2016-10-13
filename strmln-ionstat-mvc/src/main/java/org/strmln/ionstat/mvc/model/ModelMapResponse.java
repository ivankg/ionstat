package org.strmln.ionstat.mvc.model;

import java.util.HashMap;

public class ModelMapResponse extends HashMap<Object, Object> {
	private static final String ERROR_MESSAGE = "errorMessage";
	private static final String SUCCESS_KEY = "success";
	private static final long serialVersionUID = -5501570563762961017L;

	public ModelMapResponse() {
		this.put(SUCCESS_KEY, Boolean.TRUE);
	}
	
	public ModelMapResponse(String errorMessage){
		this.put(SUCCESS_KEY, Boolean.FALSE);
		this.put(ERROR_MESSAGE, errorMessage);
	}

}
