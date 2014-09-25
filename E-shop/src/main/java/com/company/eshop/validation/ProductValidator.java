package com.company.eshop.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.company.eshop.domain.CategoryPK;
import com.company.eshop.domain.ManufacturerPK;
import com.company.eshop.domain.Product;
import com.company.eshop.domain.ProductPK;
import com.company.eshop.service.ShopAccess;
import com.company.eshop.service.ShopManagement;


@Component
public class ProductValidator implements CustomValidator{

	@Autowired
	private ShopManagement shopManagement;
	
	@Autowired
	@Qualifier("shopAccessImpl")
	private ShopAccess shopAccess;
	
	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

	@Override
	public void validate(Object object, Errors errors) {
		
		
		Product product=(Product) object;		
		
		if(!product.getCategoryId().isEmpty()){
			
			CategoryPK categoryPK=new CategoryPK();
			categoryPK.setCategoryId(product.getCategoryId());
			
			if(!shopManagement.isCategory(categoryPK)){
				errors.rejectValue("categoryId","error.incorrectCategory");
			}
		}
		
		
		ManufacturerPK manufacturerPK=new ManufacturerPK();
		manufacturerPK.setManufacturerId(product.getManufacturerId());
		
		if(!shopManagement.isManufacturer(manufacturerPK)){
			errors.rejectValue("manufacturerId","error.incorrectManufacturer");
		}
		
		if(product.getPrice()<0.01){
			errors.rejectValue("price","error.incorrectPrice");
		}
		
	}

	@Override
	public void validateBeforeCreating(Object object, Errors errors) {
		
		Product product=(Product) object;	
		
		validate(product,errors);
		
		if(shopManagement.isProduct(product.getName())){
			errors.rejectValue("name","unique.product");
		}
		
	}

	@Override
	public void validateBeforeUpdate(Object object, Errors errors) {
		
		Product product=(Product) object;	
		
		validate(product,errors);
		
		ProductPK productPK=new ProductPK();
		productPK.setProductId(product.getProductId());
		
		if(!shopAccess.getProduct(productPK).getName().
				equals(product.getName()))
			if(shopManagement.isProduct(product.getName())){
				errors.rejectValue("name","unique.product");
			}
	}

}
