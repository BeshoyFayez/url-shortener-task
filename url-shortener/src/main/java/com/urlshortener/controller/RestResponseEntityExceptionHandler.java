package com.urlshortener.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import io.jsonwebtoken.ExpiredJwtException;

/**
 * @author beshoy
 *
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler {

	public RestResponseEntityExceptionHandler() {
		super();
	}

	@ExceptionHandler(value = { AccessDeniedException.class })
	public ResponseEntity<Object> handleBadRequest(final AccessDeniedException ex, final WebRequest request) {
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler({ UsernameNotFoundException.class })
	public ResponseEntity<Object> handleUserNotFound(final UsernameNotFoundException ex, final WebRequest request) {
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler({ ExpiredJwtException.class })
	public ResponseEntity<Object> handleBadRequest(final ExpiredJwtException ex, final WebRequest request) {
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Object> handleException(Exception ex) {
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}