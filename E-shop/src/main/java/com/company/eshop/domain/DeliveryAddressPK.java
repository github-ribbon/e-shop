package com.company.eshop.domain;

import java.io.Serializable;


/**
 * ID class for entity: DeliveryAddress
 *
 */ 
public class DeliveryAddressPK  implements Serializable {   
   
	private static final long serialVersionUID = 1L;
	
	private int deliveryAddressId;   	

	public DeliveryAddressPK() {
	}

	public int getDeliveryAddressId() {
		return this.deliveryAddressId;
	}

	public void setDeliveryAddressId(int deliveryAddressId) {
		this.deliveryAddressId = deliveryAddressId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + deliveryAddressId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DeliveryAddressPK))
			return false;
		DeliveryAddressPK other = (DeliveryAddressPK) obj;
		if (deliveryAddressId != other.deliveryAddressId)
			return false;
		return true;
	}
	
	
   
   
}
