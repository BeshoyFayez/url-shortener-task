package com.urlshortener.security;

import java.io.Serializable;

/**
 * This is class is required for creating a response containing the JWT to be
 * returned to the user.
 * 
 * @author beshoy
 *
 */
public class Response implements Serializable {

	private static final long serialVersionUID = 54523680461952943L;
	private String message;

	public Response(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
