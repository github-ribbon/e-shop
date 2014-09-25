package com.company.eshop.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.company.eshop.domain.Category;
import com.company.eshop.domain.CategoryPK;
import com.company.eshop.domain.DbStatusPK;
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



public interface ShopManagement {

	
	public abstract Manufacturer getManufacturer(ManufacturerPK manufacturerPK); 
	public abstract void addManufacturer(Manufacturer manufacturer);
	public abstract void saveManufacturer(Manufacturer manufacturer);
	public abstract void deleteManufacturer(ManufacturerPK manufacturerPK);
	public abstract List<Manufacturer> getAllManufacturers();
	public abstract boolean isManufacturer(ManufacturerPK manufacturerPK);	
	public abstract boolean hasManufacturerDependentObjects(ManufacturerPK manufacturerPK);
	
	
	
	public abstract FeatureType getFeatureType(FeatureTypePK featureTypePK); 
	public abstract void addFeatureType(FeatureType featureType);
	public abstract void saveFeatureType(FeatureType featureType);
	public abstract void deleteFeatureType(FeatureTypePK featureTypePK);
	public abstract List<FeatureType> getAllFeatureTypes();
	public abstract boolean isFeatureType(FeatureTypePK featureTypePK);	
	public abstract boolean hasFeatureTypeDependentObjects(FeatureTypePK featureTypePK);
	
	

	public abstract void addFeature(Feature feature);
	public abstract void saveFeature(Feature feature);
	public abstract void deleteFeature(FeaturePK featurePK);
	public abstract boolean isFeature(FeaturePK featurePK);	
	public abstract Feature getFeature(FeaturePK featurePK);
	
	 
	
	public abstract void addCategory(Category category);	
	public abstract void saveCategory(Category category);
	
	public abstract void deleteCategory(CategoryPK categoryPK);
	
	
	public abstract boolean isCategory(CategoryPK categoryPK);
	public abstract boolean hasCategoryDependentObjects(CategoryPK categoryPK);
	
	
	
		
	
	public abstract boolean isProduct(String name);
	public abstract void addProduct(Product product);
	public abstract void saveProduct(Product product);
	public abstract void deleteProduct(ProductPK productPK);
	public abstract boolean hasProductDependentObjects(ProductPK productPK);
	 
	
	
	public abstract boolean isProductInstance(ProductInstancePK productInstancePK);
	public abstract ProductInstance getProductInstance(ProductInstancePK productInstancePK);	
	public abstract void addProductInstance(ProductInstance productInstance);
	public abstract void deleteProductInstance(ProductInstancePK productInstancePK);
	public abstract List<ProductInstance> getProductInstances(ProductPK productPK);
	public abstract boolean hasProductInstanceDependentObjects(ProductInstancePK productInstancePK);
	 
	   
	
	
	
	public abstract boolean isImage(ImagePK imagePK);
	public abstract Image getImage(ImagePK imagePK);
	public abstract void addImage(Image image) throws IOException;
	public abstract void deleteImage(ImagePK imagePK) throws IOException;
	public abstract void saveImage(String filename,MultipartFile binaryContent) throws IOException;
	public abstract void removeImage(String filename) throws IOException;
	
	
	
	public abstract void completeOrder(OrderPK orderPK);
	public abstract List<Order> getAllOrders();
	public abstract List<Order> getOrdersByProductInstance(ProductInstancePK productInstancePK);
	public abstract List<Order> getOrdersByStatus(DbStatusPK dbStatusPK);
	


	public abstract void sendOrder(OrderPK orderPK);
}
