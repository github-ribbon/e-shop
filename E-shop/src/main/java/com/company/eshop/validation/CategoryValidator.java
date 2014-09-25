package com.company.eshop.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.company.eshop.domain.Category;
import com.company.eshop.domain.CategoryPK;
import com.company.eshop.service.ShopAccess;
import com.company.eshop.service.ShopManagement;




@Component
public class CategoryValidator implements CustomValidator{

	@Autowired
	@Qualifier("shopAccessImpl")
	private ShopAccess shopAccess;
	
	@Autowired
	private ShopManagement shopManagement;
	
	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

	@Override
	public void validateBeforeCreating(Object object, Errors errors) {
		
		Category category=(Category) object;
		
		
		if(shopManagement.isCategory(category.getId())){
			errors.rejectValue("id.categoryId","unique.category");
		}
		
		validate(category,errors);
	}

	@Override
	public void validate(Object object, Errors errors) {
		
		Category category=(Category) object;
		
		CategoryPK categoryPK=new CategoryPK();
		categoryPK.setCategoryId(category.getSupCategoryId());
		
		if(categoryPK.getCategoryId().length()>0)			
			if(!shopManagement.isCategory(categoryPK)){				
				errors.rejectValue("supCategoryId","error.incorrectCategory");			
			}		
	}

	@Override
	public void validateBeforeUpdate(Object object, Errors errors) {
		
		Category category=(Category) object;
		
		CategoryPK categoryPK=new CategoryPK();
		categoryPK.setCategoryId(category.getSupCategoryId());
		
		if(categoryPK.getCategoryId().length()>0)			
			if(category.getId().getCategoryId().equals(categoryPK.getCategoryId())){				
				errors.rejectValue("supCategoryId","error.incorrectCategory");			
			}else{ 
				
				Category full=shopAccess.getCategory(category.getId());
				
				if(shopAccess.isSubCategory(full, categoryPK)){
					errors.rejectValue("supCategoryId","error.categoryLoop");
				}
			}	
		
		validate(category,errors);
	}

	
}
