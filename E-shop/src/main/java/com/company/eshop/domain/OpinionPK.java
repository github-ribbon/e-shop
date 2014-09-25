package com.company.eshop.domain;

import java.io.Serializable;

public class OpinionPK implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int opinionId;

	public int getOpinionId() {
		return opinionId;
	}

	public void setOpinionId(int opinionId) {
		this.opinionId = opinionId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + opinionId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof OpinionPK))
			return false;
		OpinionPK other = (OpinionPK) obj;
		if (opinionId != other.opinionId)
			return false;
		return true;
	}
	
}
