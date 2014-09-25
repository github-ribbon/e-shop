package com.company.eshop.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.company.eshop.domain.Feature;
import com.company.eshop.domain.FeatureTypePK;
import com.company.eshop.service.ShopManagement;



@Component
public class FeatureValidator implements Validator{

	@Autowired
	private ShopManagement shopManagement;
	
	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

	@Override
	public void validate(Object object, Errors errors) {
		
		Feature feature=(Feature) object;
		
		if(shopManagement.isFeature(feature.getId())){
			errors.rejectValue("id.featureTypeId","unique.feature");
		}
		
		FeatureTypePK featureTypePK=new FeatureTypePK();
		featureTypePK.setFeatureTypeId(feature.getId().getFeatureTypeId());
		
		if(!shopManagement.isFeatureType(featureTypePK)){
			errors.rejectValue("id.featureTypeId","error.incorrectFeatureType");
		}
	}

}
