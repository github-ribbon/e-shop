package com.company.eshop.domain;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Entity implementation class for Entity: Opinion
 *
 */
@Entity
@Table(name="opinion")
@IdClass(OpinionPK.class)
@Cacheable(false)
public class Opinion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "opinion_id")
	@GeneratedValue(strategy = SEQUENCE, generator = "opinion_seq")
	@SequenceGenerator(name = "opinion_seq", allocationSize = 1)
	private int opinionId;
	
	@Column(nullable = false, length = 300)
	@NotBlank
	@Size(min=5,max=300)
	private String content;
	
	@Column(nullable = false, name = "product_id")
	private int productId;	
	
	@Column(nullable = false)
	@Temporal(TIMESTAMP)
	private Date added;
	
	@ManyToOne(fetch = LAZY)	
	@JoinColumn(name="product_id", referencedColumnName="product_id",insertable=false,updatable=false)		
	private Product product;

	public Opinion() {
	}   
	
	public int getOpinionId() {
		return this.opinionId;
	}

	public void setOpinionId(int opinionId) {
		this.opinionId = opinionId;
	}   
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}   
	public Date getAdded() {
		return added;
	}

	public void setAdded(Date added) {
		this.added = added;
	}

	public int getProductId() {
		return this.productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
   
}
