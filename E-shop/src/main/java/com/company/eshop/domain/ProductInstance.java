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
 * Entity implementation class for Entity: ProductInstance
 *
 */
@Entity
@Table(name="product_instance")
@IdClass(com.company.eshop.domain.ProductInstancePK.class)
@Cacheable(false)
public class ProductInstance implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "product_instance_id")
	@GeneratedValue(strategy = SEQUENCE, generator = "product_instance_seq")
	@SequenceGenerator(name = "product_instance_seq", allocationSize = 1)
	private int productInstanceId;
	
	@Column(name = "product_id", nullable = false)
	private int productId;
	
	@Column(nullable = false)
	@Temporal(TIMESTAMP)
	private Date added;	

	@ManyToOne(fetch = LAZY)	
	@JoinColumn(name="product_id", referencedColumnName="product_id",insertable=false,updatable=false)		
	private Product product;
	
	@OneToMany(mappedBy = "productInstance", fetch = LAZY)
	private List<OrderedItem> orderedItems;
	
	public ProductInstance() {
	}   
	
	public int getProductInstanceId() {
		return this.productInstanceId;
	}

	public void setProductInstanceId(int productInstanceId) {
		this.productInstanceId = productInstanceId;
	}   
	public int getProductId() {
		return this.productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}   
	public Date getAdded() {
		return this.added;
	}

	public void setAdded(Date added) {
		this.added = added;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<OrderedItem> getOrderedItems() {
		return orderedItems;
	}

	public void setOrderedItems(List<OrderedItem> orderedItems) {
		this.orderedItems = orderedItems;
	}
   
}
