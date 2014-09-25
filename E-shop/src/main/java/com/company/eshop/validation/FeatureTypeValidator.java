package com.company.eshop.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.company.eshop.domain.FeatureType;
import com.company.eshop.service.ShopManagement;



@Component
public class FeatureTypeValidator implements Validator{

	@Autowired
	private ShopManagement shopManagement;
	
	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

	@Override
	public void validate(Object object, Errors errors) {
		
		FeatureType featureType=(FeatureType) object;		
		
		if(shopManagement.isFeatureType(featureType.getId())){
			errors.rejectValue("id.featureTypeId","unique.featureType");
		}		
		
	}

}
