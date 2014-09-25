package com.company.eshop.domain;

import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Size;

/**
 * Entity implementation class for Entity: FeatureType
 *
 */
@Entity
@Table(name="feature_type")
@Cacheable(false)
public class FeatureType implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	@Valid
	private FeatureTypePK id;
	
	@Column(length = 300)
	@Size(max=300)
	private String description;
	
	@OneToMany(mappedBy = "featureType", fetch = LAZY)
	private List<Feature> features;

	public FeatureType() {
	}   
	
	public FeatureTypePK getId() {
		return id;
	}
	
	public void setId(FeatureTypePK id) {
		this.id = id;
	}
	
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Feature> getFeatures() {
		return features;
	}

	public void setFeatures(List<Feature> features) {
		this.features = features;
	}
   
}
