package com.company.eshop.domain;

import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Admin
 *
 */
@Entity
@Table(name="admin")
@Cacheable(false)
public class Admin implements Serializable {

	private static final long serialVersionUID = 1L;
	
//	Shared primary key
	@EmbeddedId
	private UsrPK id;	

	@OneToOne(fetch = LAZY, optional = false)	
	@JoinColumn(name="login", referencedColumnName="login",insertable=false,updatable=false)		
	private Usr usr;
	
	public Admin() {
	}

	public UsrPK getId() {
		return id;
	}

	public void setId(UsrPK id) {
		this.id = id;
	}

	public Usr getUsr() {
		return usr;
	}

	public void setUsr(Usr usr) {
		this.usr = usr;
	} 
	
}
