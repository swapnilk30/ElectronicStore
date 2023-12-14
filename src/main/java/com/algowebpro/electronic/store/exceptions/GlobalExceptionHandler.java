package com.algowebpro.electronic.store.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.algowebpro.electronic.store.dtos.ApiResponseMessage;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	// handle resource not found Exception
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponseMessage> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
		logger.info("Exception Handler Invoked !!");
		ApiResponseMessage response = ApiResponseMessage.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND)
				.success(true).build();
		return new ResponseEntity(response, HttpStatus.NOT_FOUND);
	}

	// MethodArgumentNot Valid Exception
	

	// handle BadApiRequest Exception
	@ExceptionHandler(BadApiRequestException.class)
	public ResponseEntity<ApiResponseMessage> handleBadApiRequest(BadApiRequestException ex) {
		logger.info("Bad Api Request");
		ApiResponseMessage response = ApiResponseMessage.builder().message(ex.getMessage())
				.status(HttpStatus.BAD_REQUEST).success(false).build();
		return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
	}

}
