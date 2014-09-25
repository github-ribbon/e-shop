package com.company.eshop.domain;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;

/**
 * Entity implementation class for Entity: Order
 *
 */
@Entity
@Table(name="ord")
@IdClass(OrderPK.class)
@Cacheable(false)
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "order_id")
	@GeneratedValue(strategy = SEQUENCE, generator = "order_seq")	
	@SequenceGenerator(name = "order_seq", allocationSize = 1)
	private int orderId;
	
	@Column(name = "db_status_id", nullable = false, length = 1)
	private String dbStatusId;
	
	@Column(nullable = false)
	@Temporal(TIMESTAMP)
	private Date created;
	
	@Column(nullable = false)
	@Temporal(TIMESTAMP)
	private Date modified;
	
	
	@Column(nullable = false, name = "delivery_address_id")
	private int deliveryAddressId;
	
	@ManyToOne(fetch = LAZY)	
	@JoinColumn(name="db_status_id", referencedColumnName="db_status_id",insertable=false,updatable=false)		
	private DbStatus dbStatus;

	@OneToMany(mappedBy = "order", fetch = LAZY)
	private List<OrderedItem> orderedItems;

	@ManyToOne(fetch = LAZY)	
	@JoinColumn(name="delivery_address_id", referencedColumnName="delivery_address_id",insertable=false,updatable=false)		
	private DeliveryAddress deliveryAddress;
	
	public Order() {
	} 
	
	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}   
	
	public String getDbStatusId() {
		return this.dbStatusId;
	}

	public void setDbStatusId(String dbStatusId) {
		this.dbStatusId = dbStatusId;
	}   
	
	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}   
	
	public Date getModified() {
		return this.modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}  
	
	public int getDeliveryAddressId() {
		return this.deliveryAddressId;
	}

	public void setDeliveryAddressId(int deliveryAddressId) {
		this.deliveryAddressId = deliveryAddressId;
	}  

	public DbStatus getDbStatus() {
		return dbStatus;
	}

	public void setDbStatus(DbStatus dbStatus) {
		this.dbStatus = dbStatus;
	}

	public List<OrderedItem> getOrderedItems() {
		return orderedItems;
	}

	public void setOrderedItems(List<OrderedItem> orderedItems) {
		this.orderedItems = orderedItems;
	}

	public DeliveryAddress getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	
   
}
