package com.company.eshop.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.company.eshop.domain.Usr;
import com.company.eshop.service.UserService;


@Component
public class UsrValidator implements Validator{

	@Autowired
	private UserService userService;
	
	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

	@Override
	public void validate(Object object, Errors errors) {
		
		Usr usr=(Usr) object;
	
		if(userService.isUser(usr.getId())){
			errors.rejectValue("id.login","unique.login");
		}
		
		if(userService.isUser(usr.getEmail())){
			errors.rejectValue("email","unique.email");
		}
		
	}

}
