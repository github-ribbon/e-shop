package com.company.eshop.domain;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Entity implementation class for Entity: DeliveryAddress
 *
 */
@Entity
@Table(name="delivery_address")
@IdClass(DeliveryAddressPK.class)
@Cacheable(false)
public class DeliveryAddress implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "delivery_address_id")
	@GeneratedValue(strategy = SEQUENCE, generator = "delivery_address_seq")
	@SequenceGenerator(name = "delivery_address_seq", allocationSize = 1)
	private int deliveryAddressId;  
	
	
	@Column(nullable = false, length = 50)
	private String login;
	
	@Column(length = 50, nullable = false)
	@NotBlank
	@Size(min=3,max=50)
	private String street;
	
	@Column(nullable = false, length = 10, name = "post_code")
	@NotBlank
	@Size(max=10)
	private String postCode;
	
	@Column(nullable = false, length = 50)
	@NotBlank
	@Size(max=50)
	private String city;
	
	@OneToMany(mappedBy = "deliveryAddress", fetch = LAZY)
	private List<Order> orders;

	@ManyToOne(fetch = LAZY)	
	@JoinColumn(name="login", referencedColumnName="login",insertable=false,updatable=false)		
	private Client client;
	
	public DeliveryAddress() {
	}  
	
	public int getDeliveryAddressId() {
		return this.deliveryAddressId;
	}

	public void setDeliveryAddressId(int deliveryAddressId) {
		this.deliveryAddressId = deliveryAddressId;
	} 
	
	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}  
	
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
   
}
