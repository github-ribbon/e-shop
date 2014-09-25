package com.company.eshop.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.company.eshop.domain.Image;
import com.company.eshop.domain.ProductPK;
import com.company.eshop.service.ShopAccess;




@Component
public class ImageValidator implements Validator {

	
	@Autowired
	@Qualifier("shopAccessImpl")
	private ShopAccess shopAccess;
	
	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

	@Override
	public void validate(Object object, Errors errors) {
		
		Image image=(Image) object;
		
		MultipartFile binaryContent=image.getBinaryContent();		
		
			
		ProductPK productPK=new ProductPK();
		productPK.setProductId(image.getProductId());
			
		if(!shopAccess.isProduct(productPK)){
			errors.rejectValue("productId","error.incorrectProduct");
		}
				
		
		if(binaryContent.isEmpty()){
			errors.rejectValue("binaryContent","required.image");
		}else if(!binaryContent.getContentType().equals("image/jpeg")){
			errors.rejectValue("binaryContent","error.incorrectImageFormat");
		}
				
	}

}
