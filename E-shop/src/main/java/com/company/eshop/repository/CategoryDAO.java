package com.company.eshop.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.company.eshop.domain.Category;
import com.company.eshop.domain.CategoryPK;



@Repository
public class CategoryDAO {

	@Autowired
	@Qualifier(value="categoryCRUD")
	private GenericDAOImpl<Category,CategoryPK> crud;

	public GenericDAOImpl<Category,CategoryPK> getCrud() {
		return crud;
	}

	public void setCrud(GenericDAOImpl<Category,CategoryPK> crud) {
		this.crud = crud;
	}
	
	

	public List<String> getSubCategoryNames(Category category){		
		
		List<Category> categories=category.getSubCategories();
		List<String> list=new ArrayList<String>();		

		if(categories!=null)
			for (Category cat : categories) {
				list.add(cat.getId().getCategoryId());
				list.addAll(getSubCategoryNames(cat));	
			}
				
		return list;		
	}
	
	
	
	@SuppressWarnings("unchecked")
	private List<Category> getSubCategories(Category category){		

		Query query=getCrud().getEntityManager().createQuery("SELECT sub FROM Category c JOIN c.subCategories sub WHERE c.id.categoryId=:categoryId ORDER BY sub.id.categoryId");
		query.setParameter("categoryId",category.getId().getCategoryId());
			
		List<Category> categories=null;
				
		categories=query.getResultList();
		

		if(categories!=null)
			for (Category cat : categories) {			
				cat.setSubCategories(getSubCategories(cat));			
			}		
		
		return categories;	
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Category> findCategoriesWithSubCategories(){
		
		Query query=getCrud().getEntityManager().createQuery("SELECT c FROM Category c WHERE c.supCategoryId IS NULL ORDER BY c.id.categoryId");
		
		List<Category> categories=null;
		
		categories=query.getResultList();	
		
			
		for (Category category : categories) {
			category.setSubCategories(getSubCategories(category));				
		}
		
		return categories;
	}
	
	

	public Long getDependentObjectsNumber(CategoryPK categoryPK){
		
		Query query=getCrud().getEntityManager().createQuery("SELECT count(*) FROM Category c JOIN c.products p WHERE c.id.categoryId=:categoryId");
		query.setParameter("categoryId",categoryPK.getCategoryId());
		
		Long products=(Long) query.getSingleResult();
		
		query=getCrud().getEntityManager().createQuery("SELECT count(*) FROM Category c JOIN c.subCategories sub WHERE c.id.categoryId=:categoryId");
		query.setParameter("categoryId",categoryPK.getCategoryId());
		
		Long subCategories=(Long) query.getSingleResult();
		
		return products+subCategories;
	}
	
}
