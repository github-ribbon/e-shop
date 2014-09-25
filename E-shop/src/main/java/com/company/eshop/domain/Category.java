package com.company.eshop.domain;

import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Size;

/**
 * Entity implementation class for Entity: Category
 *
 */
@Entity
@Table(name="category")
@Cacheable(false)
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	@Valid
	private CategoryPK id;	
	
	@Column(length = 300)
	@Size(max=300)
	private String description;
	
	@Column(name = "sup_category_id", length = 50)
	private String supCategoryId;
	
	@OneToMany(mappedBy = "category", fetch = LAZY)
	private List<Product> products;

	@OneToMany(fetch = LAZY, mappedBy = "supCategory")
	private List<Category> subCategories;
	
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "sup_category_id", referencedColumnName = "category_id",insertable=false,updatable=false)	
	private Category supCategory;
	
	public Category() {
	}  
	
	public CategoryPK getId() {
		return id;
	}

	public void setId(CategoryPK id) {
		this.id = id;
	}	 
	 
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSupCategoryId() {
		return supCategoryId;
	}

	public void setSupCategoryId(String supCategoryId) {
		this.supCategoryId = supCategoryId;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<Category> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(List<Category> subCategories) {
		this.subCategories = subCategories;
	}

	public Category getSupCategory() {
		return supCategory;
	}

	public void setSupCategory(Category supCategory) {
		this.supCategory = supCategory;
	}
   
}
