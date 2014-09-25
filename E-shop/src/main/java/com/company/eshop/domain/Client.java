package com.company.eshop.domain;

import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Entity implementation class for Entity: Client
 *
 */
@Entity
@Table(name="client")
@Cacheable(false)
public class Client implements Serializable {

	private static final long serialVersionUID = 1L;
	
//	Shared primary key
	@EmbeddedId
	private UsrPK id;
	
	@Column(name = "given_name", nullable = false, length = 50)
	@NotBlank
	@Size(max=50)
	private String givenName;
	
	@Column(length = 50, nullable = false, name = "family_name")
	@NotBlank
	@Size(max=50)
	private String familyName;
	
	@Column(name = "phone_number", nullable = false, length = 50)
	@NotBlank
	private String phoneNumber;
	
	@OneToOne(fetch = LAZY, optional = false)	
	@JoinColumn(name="login", referencedColumnName="login",insertable=false,updatable=false)		
	private Usr usr;
	
	@OneToMany(fetch = LAZY, mappedBy = "client")
	private List<DeliveryAddress> deliveryAddresses;
	
	public Client() {
	} 
	
	public String getGivenName() {
		return this.givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}  
	
	public String getFamilyName() {
		return this.familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}  
	
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public List<DeliveryAddress> getDeliveryAddresses() {
		return deliveryAddresses;
	}

	public void setDeliveryAddresses(List<DeliveryAddress> deliveryAddresses) {
		this.deliveryAddresses = deliveryAddresses;
	}
   
}
