package com.company.eshop.domain;

import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: DbStatus
 *
 */
@Entity
@Table(name="db_status")
@Cacheable(false)
public class DbStatus implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private DbStatusPK id;

	@OneToMany(mappedBy = "dbStatus", fetch = LAZY)
	private List<Order> orders;
	
	public DbStatus() {
	}

	public DbStatusPK getId() {
		return id;
	}

	public void setId(DbStatusPK id) {
		this.id = id;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}   
	
	
}
