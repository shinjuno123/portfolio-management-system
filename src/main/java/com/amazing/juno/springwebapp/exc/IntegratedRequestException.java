package com.amazing.juno.springwebapp.exc;

public class IntegratedRequestException extends RuntimeException {

	private static final long serialVersionUID = 7604980816782573072L;

	public IntegratedRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public IntegratedRequestException(String message) {
		super(message);
	}

	public IntegratedRequestException(Throwable cause) {
		super(cause);
	}
	
	
}
