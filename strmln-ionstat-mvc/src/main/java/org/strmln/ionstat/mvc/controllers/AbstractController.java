package org.strmln.ionstat.mvc.controllers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.strmln.ionstat.mvc.model.ModelMapResponse;

public abstract class AbstractController {

	@ExceptionHandler
	public ModelMapResponse handleException(Exception e) {
		return new ModelMapResponse(e.getMessage());
	}

}
