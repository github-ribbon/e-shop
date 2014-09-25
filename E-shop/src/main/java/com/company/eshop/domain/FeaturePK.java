package com.company.eshop.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ID class for entity: Feature
 *
 */ 
@Embeddable
public class FeaturePK  implements Serializable {   
   
	private static final long serialVersionUID = 1L;
	
	@Column(name = "feature_type_id", length = 50)
	private String featureTypeId; 
	
	@Column(name = "product_id")
	private int productId;
	
	public FeaturePK() {
	}

	public String getFeatureTypeId() {
		return this.featureTypeId;
	}

	public void setFeatureTypeId(String featureTypeId) {
		this.featureTypeId = featureTypeId;
	}	

	public int getProductId() {
		return this.productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}	
   
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((featureTypeId == null) ? 0 : featureTypeId.hashCode());
		result = prime * result + productId;
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof FeaturePK))
			return false;
		FeaturePK other = (FeaturePK) obj;
		if (featureTypeId == null) {
			if (other.featureTypeId != null)
				return false;
		} else if (!featureTypeId.equals(other.featureTypeId))
			return false;
		if (productId != other.productId)
			return false;
		return true;
	}
}
