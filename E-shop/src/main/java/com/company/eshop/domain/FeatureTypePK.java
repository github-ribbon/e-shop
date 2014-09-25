package com.company.eshop.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
public class FeatureTypePK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "feature_type_id", length = 50)
	@NotBlank
	@Size(min=3,max=50)
	private String featureTypeId;

	public String getFeatureTypeId() {
		return featureTypeId;
	}	

	public void setFeatureTypeId(String featureTypeId) {
		this.featureTypeId = featureTypeId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((featureTypeId == null) ? 0 : featureTypeId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof FeatureTypePK))
			return false;
		FeatureTypePK other = (FeatureTypePK) obj;
		if (featureTypeId == null) {
			if (other.featureTypeId != null)
				return false;
		} else if (!featureTypeId.equals(other.featureTypeId))
			return false;
		return true;
	}
	
}
