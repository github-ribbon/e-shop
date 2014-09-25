package com.company.eshop.domain;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Entity implementation class for Entity: Product
 *
 */
@Entity
@IdClass(value = ProductPK.class)
@Table(name="product")
@Cacheable(false)
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	   
	@Id
	@Column(name = "product_id")
	@GeneratedValue(strategy = SEQUENCE, generator = "product_seq")
	@SequenceGenerator(name = "product_seq", allocationSize = 1)	
	private int productId;
	
	@Column(name = "manufacturer_id", nullable = false)
	@NotNull
	private int manufacturerId;
	
	@Column(nullable = false, unique = true, length = 50)
	@NotBlank
	@Size(min=3,max=50)
	private String name;
	
	@Column(nullable = false)
	@NotNull
	private double price;
	
	
	@Column(length = 300)
	@Size(max=300)
	private String description;
	
	@Column(length = 50, name = "category_id")
	private String categoryId;
	
	@ManyToOne(fetch = LAZY)	
	@JoinColumn(name="manufacturer_id", referencedColumnName="manufacturer_id",insertable=false,updatable=false)		
	private Manufacturer manufacturer;
	
	@OneToMany(mappedBy = "product", fetch = LAZY)
	private List<Image> images;
	
	@OneToMany(mappedBy = "product", fetch = LAZY)
	private List<Opinion> opinions;
	
	@ManyToOne(fetch = LAZY)	
	@JoinColumn(name="category_id", referencedColumnName="category_id",insertable=false,updatable=false)		
	private Category category;
	
	@OneToMany(mappedBy = "product", fetch = LAZY)
	private List<Feature> features;
	
	@OneToMany(mappedBy = "product", fetch = LAZY)
	private List<ProductInstance> productInstances;
	
	public Product() {
	}  
	
	public int getProductId() {
		return this.productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}  
	
	public int getManufacturerId() {
		return this.manufacturerId;
	}

	public void setManufacturerId(int manufacturerId) {
		this.manufacturerId = manufacturerId;
	} 
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}   
	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}   
	
 
	
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	} 
	
	public String getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public List<Opinion> getOpinions() {
		return opinions;
	}

	public void setOpinions(List<Opinion> opinions) {
		this.opinions = opinions;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Feature> getFeatures() {
		return features;
	}

	public void setFeatures(List<Feature> features) {
		this.features = features;
	}

	public List<ProductInstance> getProductInstances() {
		return productInstances;
	}

	public void setProductInstances(List<ProductInstance> productInstances) {
		this.productInstances = productInstances;
	}
   
}
