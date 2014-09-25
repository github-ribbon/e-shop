package com.company.eshop.domain;

import java.io.Serializable;

public class ProductInstancePK implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int productInstanceId;


	public int getProductInstanceId() {
		return productInstanceId;
	}

	public void setProductInstanceId(int productInstanceId) {
		this.productInstanceId = productInstanceId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + productInstanceId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ProductInstancePK))
			return false;
		ProductInstancePK other = (ProductInstancePK) obj;
		if (productInstanceId != other.productInstanceId)
			return false;
		return true;
	}

}
