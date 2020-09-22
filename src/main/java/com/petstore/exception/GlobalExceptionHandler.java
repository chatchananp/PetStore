package com.petstore.exception;

import javax.validation.ValidationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final String BAD_REQUEST = "Bad Requset"; 
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(), "Not Found", ex.getMessage());
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), BAD_REQUEST, ex.getMessage());
		return new ResponseEntity<Object>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ValidationException.class)
	protected ResponseEntity<Object> handleValidationException(ValidationException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(HttpStatus.METHOD_NOT_ALLOWED.value(), "Method not allowed", ex.getMessage());
		return new ResponseEntity<Object>(errorDetails, HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@ExceptionHandler(NumberFormatException.class)
	public ResponseEntity<Object> handleNumberFormat(NumberFormatException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), BAD_REQUEST, ex.getMessage());
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidEx.class)
	public ResponseEntity<?> anotherHandleMethodArgumentNotValid(MethodArgumentNotValidEx ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), BAD_REQUEST, ex.getMessage());
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), BAD_REQUEST, ex.getCause().toString());
		return new ResponseEntity<Object>(errorDetails, HttpStatus.BAD_REQUEST);
	}
}
