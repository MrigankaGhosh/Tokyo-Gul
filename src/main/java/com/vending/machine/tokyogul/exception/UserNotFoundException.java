package com.vending.machine.tokyogul.exception;

public class UserNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;

	public UserNotFoundException(String message) {
		super();
		this.message = message;
	}

	@Override
	public String toString() {
		return String.format("UserNotFoundException [message=%s]", message);
	}

}
