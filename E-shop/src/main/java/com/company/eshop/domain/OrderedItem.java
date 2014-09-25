package com.company.eshop.domain;

import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: OrderedItem
 *
 */
@Entity
@Table(name="ordered_item")
@Cacheable(false)
public class OrderedItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private OrderedItemPK id;	

	@ManyToOne(fetch = LAZY)	
	@JoinColumn(name="product_instance_id", referencedColumnName="product_instance_id",insertable=false,updatable=false)		
	private ProductInstance productInstance;
	
	@ManyToOne(fetch = LAZY)	
	@JoinColumn(name="order_id", referencedColumnName="order_id",insertable=false,updatable=false)		
	private Order order;
	
	public OrderedItem() {
	}

	public OrderedItemPK getId() {
		return id;
	}

	public void setId(OrderedItemPK id) {
		this.id = id;
	}

	public ProductInstance getProductInstance() {
		return productInstance;
	}

	public void setProductInstance(ProductInstance productInstance) {
		this.productInstance = productInstance;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}   
	
	
   
}
