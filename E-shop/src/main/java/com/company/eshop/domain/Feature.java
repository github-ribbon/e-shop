package com.company.eshop.domain;

import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Entity implementation class for Entity: Feature
 *
 */
@Entity
@Table(name="feature")
@Cacheable(false)
public class Feature implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private FeaturePK id;

	@Column(nullable = false, length = 300)
	@NotBlank
	@Size(max=300)
	private String value;
	
	@ManyToOne(fetch = LAZY)	
	@JoinColumn(name="feature_type_id", referencedColumnName="feature_type_id",insertable=false,updatable=false)		
	private FeatureType featureType;
	
	@ManyToOne(fetch = LAZY)	
	@JoinColumn(name="product_id", referencedColumnName="product_id",insertable=false,updatable=false)		
	private Product product;

	public Feature() {
	}   
	  
	public FeaturePK getId() {
		return id;
	}

	public void setId(FeaturePK id) {
		this.id = id;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public FeatureType getFeatureType() {
		return featureType;
	}

	public void setFeatureType(FeatureType featureType) {
		this.featureType = featureType;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
   
}
