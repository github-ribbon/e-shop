package com.company.eshop.domain;

import java.io.Serializable;

public class ImagePK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int imageId;

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + imageId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ImagePK))
			return false;
		ImagePK other = (ImagePK) obj;
		if (imageId != other.imageId)
			return false;
		return true;
	}
	
	
	
}
