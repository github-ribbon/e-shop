package com.company.eshop.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public interface CustomValidator extends Validator {

	public abstract void validateBeforeCreating(Object object, Errors errors);
	public abstract void validateBeforeUpdate(Object object, Errors errors);
	
}
