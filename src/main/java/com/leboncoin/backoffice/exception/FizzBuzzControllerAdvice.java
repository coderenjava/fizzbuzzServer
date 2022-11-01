package com.leboncoin.backoffice.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class FizzBuzzControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(InvalidFizzBuzzParamsException.class)
	public ResponseEntity<Object> handleInvalidFizzBuzzParamsException(
			InvalidFizzBuzzParamsException e, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put( "timestamp", LocalDateTime.now());
		body.put("message", e.getMessage());

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ServerErrorException.class)
	public ResponseEntity<Object> handleServerErrorException(
			ServerErrorException e, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put( "timestamp", LocalDateTime.now());
		body.put("message", e.getMessage());

		return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
