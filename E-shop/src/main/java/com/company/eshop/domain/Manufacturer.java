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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Entity implementation class for Entity: Manufacturer
 *
 */
@Entity
@IdClass(value = ManufacturerPK.class)
@Table(name="manufacturer")
@Cacheable(false)
public class Manufacturer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="manufacturer_id")
	@GeneratedValue(strategy = SEQUENCE, generator = "manufacturer_seq")
	@SequenceGenerator(name = "manufacturer_seq", allocationSize = 1)
	private int manufacturerId;
	
	@Column(length = 50, nullable = false)
	@NotBlank
	@Size(min=3,max=50)
	private String name;
	
	@Column(length = 300)
	@Size(max=300)
	private String description;	
	
	@Column(length = 50, name = "home_page")
	@Size(max=50)
	private String homePage;	
	
	@OneToMany(mappedBy = "manufacturer", fetch = LAZY)
	private List<Product> products;
	

	public Manufacturer() {
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
	
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}  
	
	public String getHomePage() {
		return this.homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
   
}
