package com.amazing.juno.springwebapp.exc;

import java.util.List;

import org.springframework.validation.ObjectError;

public class IntegratedRequestException extends RuntimeException {

	private static final long serialVersionUID = 7604980816782573072L;
	
	private List<ObjectError> errors;
	

	public IntegratedRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public IntegratedRequestException(List<ObjectError> errors) {
		super("");
		this.errors = errors;
	}

	public IntegratedRequestException(Throwable cause) {
		super(cause);
	}
	
	
	public List<ObjectError> getErrors() {
		return errors;
	}

	public void setErrors(List<ObjectError> errors) {
		this.errors = errors;
	}

	
	
	
	
	
}
