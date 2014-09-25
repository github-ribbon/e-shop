package com.company.eshop.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.MailSendException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionHandlerController{	  
	 
	  @ExceptionHandler(DataIntegrityViolationException.class)
	  public String handleDataIntegrityViolationException(HttpServletRequest request,DataIntegrityViolationException e) {
		 
		e.printStackTrace();
		
	    return "errors/dataIntegrityViolationException";
	  }
	  
	  @ExceptionHandler(DataAccessException.class)
	  public String handleDataAccessException(HttpServletRequest request,DataAccessException e) {
		  
		e.printStackTrace();
		
	    return "errors/dataAccessException";
	  }
	  
	  @ExceptionHandler(AccessDeniedException.class)
	  public String handleAccessDeniedException(HttpServletRequest request,AccessDeniedException e) {	
		 
		e.printStackTrace();
		
	    return "errors/accessDeniedException";
	  }
	  
	 
	  
	  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	  public String handleHttpRequestMethodNotSupportedException(HttpServletRequest request,HttpRequestMethodNotSupportedException e) {	
		 
		e.printStackTrace();
		
	    return "errors/error405";
	  }
	  
	 
	
	  
	  @ExceptionHandler(MailSendException.class)
	  public String handleMailException(HttpServletRequest request,MailSendException e) {	
	
		e.printStackTrace();
		
	    return "errors/mailSendException";
	  }
	  
	  @ExceptionHandler(UnsupportedEncodingException.class)
	  public String handleUnsupportedEncodingException(HttpServletRequest request,
			  UnsupportedEncodingException e) {	
		 
		e.printStackTrace();
		
	    return "errors/unsupportedEncodingException";
	  }
	  
	  @ExceptionHandler(Exception.class)
	  public String handleException(HttpServletRequest request,Exception e) {	
	
		e.printStackTrace();
		
	    return "errors/error500";
	  } 
	  
}
