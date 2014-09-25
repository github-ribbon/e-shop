package com.company.eshop.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.company.eshop.domain.ProductInstance;
import com.company.eshop.domain.ProductPK;
import com.company.eshop.service.ShopAccess;



@Component
public class ProductInstanceValidator implements Validator{

	@Autowired
	@Qualifier("shopAccessImpl")
	private ShopAccess shopAccess;
	
	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

	@Override
	public void validate(Object object, Errors errors) {
		
		ProductInstance productInstance=(ProductInstance) object;
		
		ProductPK productPK=new ProductPK();
		productPK.setProductId(productInstance.getProductId());
		
		if(!shopAccess.isProduct(productPK)){
			errors.rejectValue("productId","error.incorrectProduct");
			errors.rejectValue("productId","productRemoved");
		}
	}

	
}
