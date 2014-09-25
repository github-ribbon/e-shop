package com.company.eshop.domain;

import java.io.Serializable;


/**
 * The primary key class for the product database table.
 * 
 * @author Andrzej Stążecki
 *
 */
public class ProductPK implements Serializable{

	private static final long serialVersionUID = 1L;		
	
	private int productId;	

	public ProductPK(){		
	}


	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + productId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ProductPK))
			return false;
		ProductPK other = (ProductPK) obj;
		if (productId != other.productId)
			return false;
		return true;
	}	
}
