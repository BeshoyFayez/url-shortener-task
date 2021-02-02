package com.urlshortener.security;

import java.io.Serializable;

/**
 * @author beshoy
 *
 */
public class UserDto implements Serializable {
	public UserDto(String jwtToken) {
		this.token = jwtToken;
	}

	private static final long serialVersionUID = 6597309479365280380L;
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "UserDto [token=" + token + "]";
	}

}
