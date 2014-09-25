package com.company.eshop.repository;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.company.eshop.domain.Category;
import com.company.eshop.domain.Product;
import com.company.eshop.domain.ProductPK;



@Repository
public class ProductDAO {

	@Autowired
	@Qualifier(value="productCRUD")
	private GenericDAOImpl<Product,ProductPK> crud;

	@Autowired
	private CategoryDAO categoryDAO;
	
	public GenericDAOImpl<Product,ProductPK> getCrud() {
		return crud;
	}

	public void setCrud(GenericDAOImpl<Product,ProductPK> crud) {
		this.crud = crud;
	}
	
	
	public Product findProductByName(String name){
		
		Query query=crud.getEntityManager().createQuery("SELECT p FROM Product p WHERE p.name=:name");
		query.setParameter("name",name);
		
		Product product;
		
		try{
			product=(Product) query.getSingleResult();
		}catch(NoResultException e){
			return null;
		}		
		
		return product;
	}
	
	
	public List<Product> findProducts(Category category,Integer manufacturerId,boolean priceAscending,String name){
		
		String priceOrderString;	
		String nameString="";
		
		if(name!=null){
			nameString=" p.name=:name ";
		}
		
	    if(priceAscending){
	    	priceOrderString=" ORDER BY p.price";
	    }else{
	    	priceOrderString=" ORDER BY p.price DESC";
	    }
	    
	    Query query;
	    
		
	    if((category!=null)&&(manufacturerId!=null)){
	    	
	    	if(name!=null){
	    		nameString=" AND p.name LIKE :name ";
	    	}else{
	    		nameString=" ";
	    	}
	    	
	    	List<String> categoryList=categoryDAO.getSubCategoryNames(category);
			
			String categoryString="";
			
			if(!categoryList.isEmpty())
				categoryString=" OR p.categoryId IN (:categoryList) ";
			
			
			
	    	query=crud.getEntityManager().createQuery("SELECT p FROM Product p WHERE" +
	    			" (p.categoryId=:categoryId "+categoryString+") AND p.manufacturerId=:manufacturerId "+nameString+" "+priceOrderString);
	    	
	    	query.setParameter("categoryId", category.getId().getCategoryId());
	    
	    	if(!categoryList.isEmpty())
	    		query.setParameter("categoryList",categoryList);

	    	
	    	query.setParameter("manufacturerId",manufacturerId);
	    	
	    	if(name!=null){
	    		query.setParameter("name", "%"+name+"%");
	    	}
	    	
		}else if(category!=null){
			
			if(name!=null){
	    		nameString=" AND p.name LIKE :name ";
	    	}else{
	    		nameString=" ";
	    	}
	    	
			List<String> categoryList=categoryDAO.getSubCategoryNames(category);
			
			String categoryString="";
			
			if(!categoryList.isEmpty())
				categoryString=" OR p.categoryId IN (:categoryList) ";
			
			
			query=crud.getEntityManager().createQuery("SELECT p FROM Product p WHERE" +
	    			" (p.categoryId=:categoryId "+categoryString+") "+nameString+" "+priceOrderString);
	    	
	    	query.setParameter("categoryId", category.getId().getCategoryId());
	    	
	    	if(!categoryList.isEmpty())
	    		query.setParameter("categoryList",categoryList);


	    	if(name!=null){
	    		query.setParameter("name", "%"+name+"%");
	    	}
	    	
		}else if(manufacturerId!=null){
			
			if(name!=null){
	    		nameString=" AND p.name LIKE :name ";
	    	}else{
	    		nameString=" ";
	    	}
	    	
			query=crud.getEntityManager().createQuery("SELECT p FROM Product p WHERE" +
	    			" p.manufacturerId=:manufacturerId "+nameString+" "+priceOrderString);
	    	query.setParameter("manufacturerId",manufacturerId);
	    	

	    	if(name!=null){
	    		query.setParameter("name", "%"+name+"%");
	    	}
	    	
		}else{
			
			if(name!=null){
	    		nameString=" WHERE p.name LIKE :name ";
	    	}else{
	    		nameString=" ";
	    	}
	    	
			
			query=crud.getEntityManager().createQuery("SELECT p FROM Product p "+nameString+" "+priceOrderString);
			

	    	if(name!=null){
	    		query.setParameter("name", "%"+name+"%");
	    	}
	    	
		}
	    	
	    @SuppressWarnings("unchecked")
		List<Product> products=query.getResultList();	    
	    
	    Iterator<Product> iterator=products.iterator();
	    
	    while(iterator.hasNext()){
	    	iterator.next().getImages().iterator();
	    }
	    
		return products;
	}
	
	
	
	public List<Product> findBestsellers(){
		
		Query query=crud.getEntityManager().createQuery("SELECT p,count(oi) FROM Product p JOIN p.productInstances pi" +
				" JOIN pi.orderedItems oi JOIN oi.order o WHERE o.dbStatusId='F' GROUP BY p ORDER BY count(oi) DESC");
		query.setMaxResults(5);
		
		
		List<Product> products=new ArrayList<Product>();
		
		@SuppressWarnings("unchecked")
		List<Object[]> list=query.getResultList();
			
		Iterator<Object[]> iterator=list.iterator();
	
		while(iterator.hasNext()){	
			products.add((Product) iterator.next()[0]);
		}
		
		
		return products;			
	}
	
	

	public Long getDependentObjectsNumber(ProductPK productPK){
		
		int productId=productPK.getProductId();
		
		Query query=getCrud().getEntityManager().createQuery("SELECT count(*) FROM Product p JOIN " +
				" p.images im WHERE p.productId=:productId");
		query.setParameter("productId",productId);
		
		Long images=(Long) query.getSingleResult();
		
		

		query=getCrud().getEntityManager().createQuery("SELECT count(*) FROM Product p JOIN " +
				" p.opinions o WHERE p.productId=:productId");
		query.setParameter("productId",productId);
		
		Long opinions=(Long) query.getSingleResult();
		
		
		
		query=getCrud().getEntityManager().createQuery("SELECT count(*) FROM Product p JOIN " +
				" p.productInstances pi WHERE p.productId=:productId");
		query.setParameter("productId",productId);
		
		Long productInstances=(Long) query.getSingleResult();
		
		
		
		query=getCrud().getEntityManager().createQuery("SELECT count(*) FROM Product p JOIN " +
				" p.features f WHERE p.productId=:productId");
		query.setParameter("productId",productId);
		
		Long features=(Long) query.getSingleResult();
		
		
		return images+opinions+productInstances+features;
	}

	
	
	public List<Object[]> findOrderedProducts(int orderId){
		
		Query query=crud.getEntityManager().createQuery("SELECT p,count(p) FROM Order o JOIN o.orderedItems oi " +
				" JOIN oi.productInstance pi JOIN pi.product p WHERE o.orderId=:orderId GROUP BY p ORDER BY count(p) DESC");
		query.setParameter("orderId", orderId);
		
			
		
		@SuppressWarnings("unchecked")
		List<Object[]> list=query.getResultList();
			
		return list;				
	}
}
