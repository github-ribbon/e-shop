package com.company.eshop.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ID class for entity: OrderedItem
 *
 */ 
@Embeddable
public class OrderedItemPK  implements Serializable {   
   
	private static final long serialVersionUID = 1L;
	
	@Column(name = "product_instance_id")
	private int productInstanceId;
	
	@Column(name = "order_id")
	private int orderId;	

	public OrderedItemPK() {
	}

	public int getProductInstanceId() {
		return this.productInstanceId;
	}

	public void setProductInstanceId(int productInstanceId) {
		this.productInstanceId = productInstanceId;
	}	

	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + orderId;
		result = prime * result + productInstanceId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof OrderedItemPK))
			return false;
		OrderedItemPK other = (OrderedItemPK) obj;
		if (orderId != other.orderId)
			return false;
		if (productInstanceId != other.productInstanceId)
			return false;
		return true;
	}
	
   
   
   
}
