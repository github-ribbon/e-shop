package com.company.eshop.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.company.eshop.domain.Category;
import com.company.eshop.domain.CategoryPK;
import com.company.eshop.domain.DbStatusPK;
import com.company.eshop.domain.DeliveryAddress;
import com.company.eshop.domain.DeliveryAddressPK;
import com.company.eshop.domain.Feature;
import com.company.eshop.domain.FeaturePK;
import com.company.eshop.domain.FeatureType;
import com.company.eshop.domain.FeatureTypePK;
import com.company.eshop.domain.Image;
import com.company.eshop.domain.ImagePK;
import com.company.eshop.domain.Manufacturer;
import com.company.eshop.domain.ManufacturerPK;
import com.company.eshop.domain.Order;
import com.company.eshop.domain.OrderPK;
import com.company.eshop.domain.Product;
import com.company.eshop.domain.ProductInstance;
import com.company.eshop.domain.ProductInstancePK;
import com.company.eshop.domain.ProductPK;
import com.company.eshop.repository.FeatureTypeDAO;
import com.company.eshop.repository.ManufacturerDAO;




@Service
public class ShopManagementImpl extends ShopAccessImpl implements ShopManagement{

	
	@Autowired
	private ManufacturerDAO manufacturerDAO;
	
	@Autowired
	private FeatureTypeDAO featureTypeDAO;
	
		
		
	
	@Override
	@Transactional
	public void addManufacturer(Manufacturer manufacturer) {		
		manufacturerDAO.getCrud().insert(manufacturer);		
	}

	@Override
	@Transactional(readOnly=true)
	public Manufacturer getManufacturer(ManufacturerPK manufacturerPK) {
		return manufacturerDAO.getCrud().read(manufacturerPK);	
	}
	
	@Override
	@Transactional
	public void saveManufacturer(Manufacturer manufacturer) {
		manufacturerDAO.getCrud().update(manufacturer);
	}

	@Override
	@Transactional
	public void deleteManufacturer(ManufacturerPK manufacturerPK) {
		manufacturerDAO.getCrud().delete(manufacturerDAO.getCrud().read(manufacturerPK));		
	}

	@Override
	@Transactional(readOnly=true)
	public List<Manufacturer> getAllManufacturers() {
		return manufacturerDAO.getCrud().getAllRows();
	}

	@Override
	@Transactional(readOnly=true)
	public boolean isManufacturer(ManufacturerPK manufacturerPK) {
		
		if(manufacturerDAO.getCrud().read(manufacturerPK)!=null){
			return true;
		}else
			return false;
		
	}

	@Override
	@Transactional(readOnly=true)
	public boolean hasManufacturerDependentObjects(ManufacturerPK manufacturerPK) {
		
		if(manufacturerDAO.getDependentObjectsNumber(manufacturerPK)>0){
			return true;
		}else
			return false;
	}

	
	
	
	@Override
	@Transactional(readOnly=true)
	public FeatureType getFeatureType(FeatureTypePK featureTypePK) {
		return featureTypeDAO.getCrud().read(featureTypePK);
	}

	@Override
	@Transactional
	public void addFeatureType(FeatureType featureType) {		
		featureTypeDAO.getCrud().insert(featureType);
	}

	@Override
	@Transactional
	public void saveFeatureType(FeatureType featureType) {
		featureTypeDAO.getCrud().update(featureType);		
	}

	@Override
	@Transactional
	public void deleteFeatureType(FeatureTypePK featureTypePK) {		
		featureTypeDAO.getCrud().delete(featureTypeDAO.getCrud().read(featureTypePK));		
	}

	@Override
	@Transactional(readOnly=true)
	public List<FeatureType> getAllFeatureTypes() {
		return featureTypeDAO.getCrud().getAllRows();
	}

	@Override
	@Transactional(readOnly=true)
	public boolean isFeatureType(FeatureTypePK featureTypePK) {
		
		if(featureTypeDAO.getCrud().read(featureTypePK)!=null){
			return true;
		}else 
			return false;
		
	}

	@Override
	@Transactional(readOnly=true)
	public boolean hasFeatureTypeDependentObjects(FeatureTypePK featureTypePK) {
		
		if(featureTypeDAO.getDependentObjectsNumber(featureTypePK)>0){
			return true;
		}else
			return false;
	}
	
	@Override
	@Transactional
	public void addCategory(Category category) {
		
		if(category.getSupCategoryId().isEmpty()){
			category.setSupCategoryId(null);
		}
		
		categoryDAO.getCrud().insert(category);
		reloadCategories();		
	}

	@Override
	@Transactional
	public void saveCategory(Category category) {
		
		if(category.getSupCategoryId().isEmpty()){
			category.setSupCategoryId(null);
		}
		
		categoryDAO.getCrud().update(category);
		reloadCategories();
	}
	
	
	
	
	

	@Override
	@Transactional
	public void deleteCategory(CategoryPK categoryPK) {
		categoryDAO.getCrud().delete(categoryDAO.getCrud().read(categoryPK));
		reloadCategories();
	}

	@Override
	@Transactional(readOnly=true)
	public boolean isCategory(CategoryPK categoryPK) {
		
		if(categoryDAO.getCrud().read(categoryPK)!=null){
			return true;
		}else
			return false;
		
	}

	@Override
	@Transactional(readOnly=true)
	public boolean hasCategoryDependentObjects(CategoryPK categoryPK) {
		
		if(categoryDAO.getDependentObjectsNumber(categoryPK)>0){
			return true;
		}else{
			return false;
		}
		
	}	
	
	@Override
	@Transactional(readOnly=true)
	public Feature getFeature(FeaturePK featurePK) {		
		return featureDAO.getCrud().read(featurePK);
	}

	@Override
	@Transactional
	public void addFeature(Feature feature) {
		featureDAO.getCrud().insert(feature);
	}

	@Override
	@Transactional
	public void saveFeature(Feature feature) {
		featureDAO.getCrud().update(feature);
	}

	@Override
	@Transactional
	public void deleteFeature(FeaturePK featurePK) {
		featureDAO.getCrud().delete(featureDAO.getCrud().read(featurePK));
	}

	@Override
	@Transactional(readOnly=true)
	public boolean isFeature(FeaturePK featurePK) {
		
		if(featureDAO.getCrud().read(featurePK)!=null){
			return true;
		}else
			return false;
		
	}

	

	
	
	@Override
	@Transactional
	public void addProduct(Product product) {
		
		if(product.getCategoryId().isEmpty()){
			product.setCategoryId(null);
		}
		
		productDAO.getCrud().insert(product);
	}

	@Override
	@Transactional
	public void saveProduct(Product product) {
		
		if(product.getCategoryId().isEmpty()){
			product.setCategoryId(null);
		}
		
		productDAO.getCrud().update(product);
	}

	@Override
	@Transactional
	public void deleteProduct(ProductPK productPK) {
		productDAO.getCrud().delete(productDAO.getCrud().read(productPK));
	}

	
	@Override
	@Transactional(readOnly=true)
	public boolean isProduct(String name) {
		
		if(productDAO.findProductByName(name)!=null){
			return true;
		}else
			return false;
		
	}	
	
	
	

	

	@Override
	@Transactional(readOnly=true)
	public boolean isProductInstance(ProductInstancePK productInstancePK) {
		
		if(productInstanceDAO.getCrud().read(productInstancePK)!=null){
			return true;
		}else
			return false;
		
	}

	@Override
	@Transactional(readOnly=true)
	public ProductInstance getProductInstance(
			ProductInstancePK productInstancePK) {
		
		return productInstanceDAO.getCrud().read(productInstancePK);
	}

	@Override
	@Transactional
	public void addProductInstance(ProductInstance productInstance) {
		
		productInstance.setAdded(new Date());
		
		productInstanceDAO.getCrud().insert(productInstance);
	}

	@Override
	@Transactional
	public void deleteProductInstance(ProductInstancePK productInstancePK) {
		
		productInstanceDAO.getCrud().delete(productInstanceDAO.getCrud().read(productInstancePK));
	}

	@Override
	@Transactional(readOnly=true)
	public List<ProductInstance> getProductInstances(ProductPK productPK) {
		return productInstanceDAO.findProductInstances(productPK.getProductId());
	}

	@Override
	@Transactional(readOnly=true)
	public boolean hasProductInstanceDependentObjects(
			ProductInstancePK productInstancePK) {
		
		if(productInstanceDAO.getDependentObjectsNumber(productInstancePK)>0){
			return true;
		}else
			return false;
	}
	
	


	@Override
	public void saveImage(String filename,MultipartFile binaryContent) throws IOException {
		
		File file=new File(servletContext.getRealPath("/resources/img/products/"+filename)); 
		FileUtils.writeByteArrayToFile(file, binaryContent.getBytes());
		
	}

	

	@Override
	@Transactional(readOnly=true)
	public boolean isImage(ImagePK imagePK) {
		
		if(imageDAO.getCrud().read(imagePK)!=null){
			return true;
		}else
			return false;
		
	}

	@Override
	@Transactional(readOnly=true)
	public Image getImage(ImagePK imagePK) {
		
		return imageDAO.getCrud().read(imagePK);
	}

	@Override
	@Transactional(rollbackFor=IOException.class)
	public void addImage(Image image) throws IOException {
		
		imageDAO.getCrud().insert(image);		
		saveImage(String.valueOf(image.getImageId())+".jpg", image.getBinaryContent());		
	}

	@Override
	@Transactional(rollbackFor=IOException.class)
	public void deleteImage(ImagePK imagePK) throws IOException {
				
		
		imageDAO.getCrud().delete(imageDAO.getCrud().read(imagePK));
		
		removeImage(String.valueOf(imagePK.getImageId()));
	}
	
	
	@Override
	public void removeImage(String filename) throws IOException {
		
		File file=new File(servletContext.getRealPath("/resources/img/products/"+filename+".jpg")); 
		file.delete();	
		
	}
	
	
	@Override
	@Transactional
	public void completeOrder(OrderPK orderPK) {
		
		Order order=orderDAO.getCrud().read(orderPK);
		
		if(order.getDbStatusId().equals("U")){
			order.setModified(new Date());
			order.setDbStatusId("F");
			
			orderDAO.getCrud().update(order);
		}
		
	}

	@Override
	@Transactional(readOnly=true)
	public List<Order> getAllOrders() {	
		return orderDAO.findAllOrders();
	}
	

	@Override
	@Transactional(readOnly=true)
	public List<Order> getOrdersByProductInstance(
			ProductInstancePK productInstancePK) {
		
		return orderDAO.findOrdersByProductInstance(productInstancePK.getProductInstanceId());
	}	

	@Override
	@Transactional(readOnly=true)
	public List<Order> getOrdersByStatus(DbStatusPK dbStatusPK) {
		
		return orderDAO.findOrdersByStatus(dbStatusPK.getDbStatusId());
	}
	

	
	@Override
	@Transactional
	public void sendOrder(OrderPK orderPK) {
		
		Order order=orderDAO.getCrud().read(orderPK);
		
		if(order.getDbStatusId().equals("C")){
			order.setModified(new Date());
			order.setDbStatusId("U");
			
			orderDAO.getCrud().update(order);
		}
		
		DeliveryAddressPK deliveryAddressPK=new DeliveryAddressPK();
		deliveryAddressPK.setDeliveryAddressId(order.getDeliveryAddressId());
		
		DeliveryAddress deliveryAddress=deliveryAddressDAO.getCrud().read(deliveryAddressPK);
		
		String message="Paczka została przekazana kurierowi. W ciągu 2 dni zostanie dostarczona pod wskazany adres." +
				"\n\n"+getOrderReport(order,deliveryAddress);
		
		sendMail(order, deliveryAddress.getClient().getUsr().getEmail(),"Zamówienie "+order.getOrderId()+" zostało wysłane", message);		
		
	}

	@Override
	@Transactional(readOnly=true)
	public boolean hasProductDependentObjects(ProductPK productPK) {
		
		if(productDAO.getDependentObjectsNumber(productPK)>0)
			return true;
		else
			return false;
	}
	
	
	
	
}
