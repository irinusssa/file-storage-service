package com.home.assignment.exception;

public class FileStorageException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2548980895917326917L;
	private String message;
	
	public FileStorageException(String message) {
		this.setMessage(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
