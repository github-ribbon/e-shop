package com.company.eshop.domain;

import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;



/**
 * Entity implementation class for Entity: Usr
 *
 */
@Entity
@Table(name="usr")
@Cacheable(false)
public class Usr implements Serializable {

	private static final long serialVersionUID = 1L;
	

	@EmbeddedId
	@Valid
	private UsrPK id;	
	
	@Column(nullable = false, length = 50)
	@NotBlank
	@Size(min=5,max=50)
	private String password;
	
	@Column(nullable = false, unique = true, length = 50)
	@NotBlank
	@Size(min=5,max=50)
	@Email
	private String email;
	

	@Column(name = "is_enabled", nullable = false)
	private boolean isEnabled;
	

	@OneToOne(fetch = LAZY, mappedBy = "usr")
	private Admin admin;
	
	@OneToOne(fetch = LAZY, mappedBy = "usr")
	@Valid
	private Client client;
	
	public Usr() {
	}   
	
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}   
	
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}   
	
	public boolean getIsEnabled() {
		return this.isEnabled;
	}

	public void setIsEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public UsrPK getId() {
		return id;
	}

	public void setId(UsrPK id) {
		this.id = id;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
   
}
