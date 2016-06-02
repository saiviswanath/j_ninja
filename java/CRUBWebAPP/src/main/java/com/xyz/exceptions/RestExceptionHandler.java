package com.xyz.exceptions;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class RestExceptionHandler {
	@org.springframework.web.bind.annotation.ExceptionHandler({
			URISyntaxException.class, UnsupportedEncodingException.class })
	public ResponseEntity<String> handleException(Exception e) {
		return new ResponseEntity<>(e.getMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
