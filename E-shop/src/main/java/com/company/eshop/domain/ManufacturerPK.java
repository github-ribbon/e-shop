package com.company.eshop.domain;

import java.io.Serializable;


/**
 * The primary key class for the manufacturer database table.
 * 
 * @author Andrzej Stążecki
 *
 */
public class ManufacturerPK implements Serializable{

	private static final long serialVersionUID = 1L;		
	
	private int manufacturerId;
	
 
	public ManufacturerPK(){		
	}

	public int getManufacturerId() {
		return manufacturerId;
	}

	public void setManufacturerId(int manufacturerId) {
		this.manufacturerId = manufacturerId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + manufacturerId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ManufacturerPK))
			return false;
		ManufacturerPK other = (ManufacturerPK) obj;
		if (manufacturerId != other.manufacturerId)
			return false;
		return true;
	}	
	
	
}
