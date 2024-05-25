package com.estore.products.core.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpHeaders;

@ControllerAdvice
public class ProductServiceControllerAdvice {
	
	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<Object> handleIllegalStateException(IllegalStateException ex, WebRequest req){
		return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleOtherException(Exception ex, WebRequest req){
		return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), INTERNAL_SERVER_ERROR);
	}
	
}
