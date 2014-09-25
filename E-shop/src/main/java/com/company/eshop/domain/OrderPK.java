package com.company.eshop.domain;

import java.io.Serializable;

public class OrderPK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int orderId;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + orderId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof OrderPK))
			return false;
		OrderPK other = (OrderPK) obj;
		if (orderId != other.orderId)
			return false;
		return true;
	}

	
}
