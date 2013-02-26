package org.openbakery.timetracker.service.exception;

public class LoginFailedException extends Exception {

	private static final long serialVersionUID = 1L;

	public LoginFailedException() {
		super();
	}

	public LoginFailedException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public LoginFailedException(String message) {
		super(message);
	}

	public LoginFailedException(Throwable throwable) {
		super(throwable);
	}

}
