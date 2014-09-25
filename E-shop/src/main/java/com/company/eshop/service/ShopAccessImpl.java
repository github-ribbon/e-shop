package com.company.eshop.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.eshop.domain.Category;
import com.company.eshop.domain.CategoryPK;
import com.company.eshop.domain.DbStatusPK;
import com.company.eshop.domain.DeliveryAddress;
import com.company.eshop.domain.DeliveryAddressPK;
import com.company.eshop.domain.Feature;
import com.company.eshop.domain.Image;
import com.company.eshop.domain.ManufacturerPK;
import com.company.eshop.domain.Order;
import com.company.eshop.domain.OrderPK;
import com.company.eshop.domain.Product;
import com.company.eshop.domain.ProductInstance;
import com.company.eshop.domain.ProductPK;
import com.company.eshop.domain.UsrPK;
import com.company.eshop.repository.CategoryDAO;
import com.company.eshop.repository.DeliveryAddressDAO;
import com.company.eshop.repository.FeatureDAO;
import com.company.eshop.repository.ImageDAO;
import com.company.eshop.repository.OrderDAO;
import com.company.eshop.repository.ProductInstanceDAO;

@Service
public class ShopAccessImpl extends ShopAccess{

	@Autowired
	protected DeliveryAddressDAO deliveryAddressDAO;
	
	@Autowired
	protected  ServletContext servletContext;
	
	
	
	@Autowired
	protected FeatureDAO featureDAO;
	
	@Autowired
	protected ImageDAO imageDAO;
	
	
	@Autowired
	protected ProductInstanceDAO productInstanceDAO;
	
	@Autowired
	protected CategoryDAO categoryDAO;
	
	@Autowired
	protected OrderDAO orderDAO;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getCategories() {		
		return (List<Category>) servletContext.getAttribute("appContextCategories");
	}

	private Category seekSubCategories(Category category,CategoryPK categoryPK){			
		
		List<Category> categories=category.getSubCategories();	
		

		if(categories!=null)
		for (Category cat : categories) {			
			
			if(cat.getId().equals(categoryPK)){
				return cat;
			}else{
				
				Category categ=seekSubCategories(cat, categoryPK);
				
				if(categ!=null){
					return categ; 
				}
			}		
		}
				
		return null;
	}


	@Override
	public Category getCategory(CategoryPK categoryPK) {
		
		List<Category> categories=new ArrayList<Category>();
		categories=getCategories();
		
		for(Category category: categories){
			
			if(category.getId().equals(categoryPK)){
				return category;
			}else{
				
				Category cat=seekSubCategories(category, categoryPK);
				if(cat!=null){
					return cat; 
				}
			}
		}
		
		
		return null;
	}
	
	@Override
	public boolean isSubCategory(Category category,CategoryPK categoryPK){
	
		if(seekSubCategories(category, categoryPK)!=null){
			return true;
		}else
			return false;
		
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Feature> getFeaturesByProduct(ProductPK productPK) {		
		return featureDAO.findFeaturesByProduct(productPK.getProductId());
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Image> getImages(ProductPK productPK) {
		
		return imageDAO.findImagesByProduct(productPK.getProductId());
	}
	
	@Override
	@Transactional(readOnly=true)
	public Product getProduct(ProductPK productPK) {
		return productDAO.getCrud().read(productPK);
	}
	

	@Override
	@Transactional(readOnly=true)
	public List<ProductInstance> getProductInstancesByOrder(OrderPK orderPK) {
		
		List<ProductInstance> productInstances=productInstanceDAO
				.findProductInstancesByOrder(orderPK.getOrderId());
		
		Iterator<ProductInstance> iterator=productInstances.iterator();
		
		while(iterator.hasNext()){
			iterator.next().getProduct().getImages().iterator();
		}
		
		return productInstances;
	}
	 	
	@Override
	@Transactional(readOnly=true)
	public boolean isProduct(ProductPK productPK) {
		
		if(productDAO.getCrud().read(productPK)!=null){
			return true;
		}else
			return false;
	}
	
	

	
	@Override
	@Transactional(readOnly=true)
	public synchronized void reloadCategories(){
		servletContext.setAttribute("appContextCategories",categoryDAO.findCategoriesWithSubCategories());
	}

	@Override
	@Transactional(readOnly=true)
	public List<Product> getProducts(Category category,
			ManufacturerPK manufacturerPK, boolean ascending,String name) {		
		
		Integer manufacturerId;
		
		
		if(manufacturerPK!=null){
			manufacturerId=manufacturerPK.getManufacturerId();
		}else{
			manufacturerId=null;
		}
		
		return productDAO.findProducts(category, manufacturerId, ascending, name);
	}
	
	@Override
	@Transactional
	public void cancelOrder(OrderPK orderPK) {
		
		Order order=orderDAO.getCrud().read(orderPK);
		
		if(!(order.getDbStatusId().equals("F")||(order.getDbStatusId().equals("D")))){
			order.setModified(new Date());
			order.setDbStatusId("D");
			
			orderDAO.getCrud().update(order);
		}
		
		DeliveryAddressPK deliveryAddressPK=new DeliveryAddressPK();
		deliveryAddressPK.setDeliveryAddressId(order.getDeliveryAddressId());
		
		DeliveryAddress deliveryAddress=deliveryAddressDAO.getCrud().read(deliveryAddressPK);
		
		
		String message="Twoje zamówienie zostało anulowane." +
				"\n\n"+getOrderReport(order,deliveryAddress);
		
		sendMail(order, deliveryAddress.getClient().getUsr().getEmail(),"Anulowano zamówienie "+order.getOrderId(), message);	
		
		
	}
	
	@Override
	@Transactional(readOnly=true)
	public Order getOrder(OrderPK orderPK) {		
		return orderDAO.getCrud().read(orderPK);
	}
	
	

	@Override
	@Transactional(readOnly=true)
	public List<Order> getOrdersByClient(UsrPK usrPK) {
		
		return orderDAO.findOrdersByClient(usrPK.getLogin());
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Order> getOrdersByClientAndStatus(UsrPK usrPK,
			DbStatusPK dbStatusPK) {
		
		return orderDAO.findOrdersByClientAndStatus(usrPK.getLogin(), dbStatusPK.getDbStatusId());
	}
	
	@Override
	public List<Product> getProducts(List<ProductInstance> productInstances) {
	
		Iterator<ProductInstance> iterator=productInstances.iterator();
		
		List<Product> products=new ArrayList<Product>();
		
		while(iterator.hasNext()){
			products.add(iterator.next().getProduct());
		}
		
		return products;
	}
	

	@Override
	public List<String> getSupCategoryNames(String categoryId) {
	
		CategoryPK categoryPK=new CategoryPK();
		categoryPK.setCategoryId(categoryId);		
		
		List<String> supCategories=new ArrayList<String>();
		
		Category temp=getCategory(categoryPK);
		
		while(temp.getSupCategoryId()!=null){
			supCategories.add(temp.getSupCategoryId());
			temp=temp.getSupCategory();
		}
		
		Collections.reverse(supCategories);
		
		return supCategories;
	}
	
	
	@Override
	public double getTotalPrice(List<Product> products) {
		
		Iterator<Product> iterator=products.iterator();
		
		double total=0;
		
		while(iterator.hasNext()){
			total+=iterator.next().getPrice();
		}
		
		return total;
	}
	

	@Override
	@Transactional(readOnly=true)
	public boolean isOrder(OrderPK orderPK) {
	
		if(orderDAO.getCrud().read(orderPK)!=null){
			return true;
		}else{
			return false;
		}
	}

	@Override
	@Transactional(readOnly=true)
	public List<Product> getBestsellers() {
		
		List<Product> products=productDAO.findBestsellers();
		
		Iterator<Product> productIterator=products.iterator();
		
		while(productIterator.hasNext()){
			productIterator.next().getImages().iterator();
		}
	
		return products;
	}
}
