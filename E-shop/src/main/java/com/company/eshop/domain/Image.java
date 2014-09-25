package com.company.eshop.domain;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;

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
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity
@IdClass(value = ImagePK.class)
@Table(name = "image")
@Cacheable(false)
public class Image implements Serializable {

	private static final long serialVersionUID = 1L;
	   
	@Id
	@Column(name = "image_id")
	@GeneratedValue(strategy = SEQUENCE, generator = "image_seq")
	@SequenceGenerator(name = "image_seq", allocationSize = 1)
	private int imageId;
	
	@Column(name = "product_id", nullable = false)
	private int productId;
	
	@ManyToOne(fetch = LAZY)	
	@JoinColumn(name="product_id", referencedColumnName="product_id",insertable=false,updatable=false)		
	private Product product;
	
	@Transient
	private MultipartFile binaryContent;
	
	public Image() {
	}   
	
	public int getImageId() {
		return this.imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
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

	public MultipartFile getBinaryContent() {
		return binaryContent;
	}

	public void setBinaryContent(MultipartFile binaryContent) {
		this.binaryContent = binaryContent;
	}
   
}
