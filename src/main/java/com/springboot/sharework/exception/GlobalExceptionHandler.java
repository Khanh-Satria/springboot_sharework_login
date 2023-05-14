package com.springboot.sharework.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.google.firebase.auth.FirebaseAuthException;
import com.springboot.sharework.payload.ErrorDetails;



@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	
	@ExceptionHandler(FirebaseAuthException.class)
	public ResponseEntity<ErrorDetails> handleGlobalFirebaseAuthException(FirebaseAuthException exception, WebRequest webRequest){
		
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage() , webRequest.getDescription(false));
		
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception, WebRequest webRequest){
		
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage() , webRequest.getDescription(false));
		
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	

}
