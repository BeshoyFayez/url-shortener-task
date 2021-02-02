package com.urlshortener.security;

/**
 * @author beshoy
 *
 */
public class ErrorResponse {
	
	private String message;
	private String description;

	public ErrorResponse(String description, String message) {
		this.message = message;
		this.description = description;
	}

	public ErrorResponse(RuntimeException e) {
		this.message = e.getMessage();
	}

	public String getMessage() {
		return message;
	}

	public String getDescription() {
		return description;
	}
}