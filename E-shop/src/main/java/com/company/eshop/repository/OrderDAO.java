package com.company.eshop.repository;

import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.company.eshop.domain.Order;
import com.company.eshop.domain.OrderPK;



@Repository
public class OrderDAO {

	@Autowired
	@Qualifier(value="orderCRUD")
	private GenericDAOImpl<Order,OrderPK> crud;

	public GenericDAOImpl<Order,OrderPK> getCrud() {
		return crud;
	}

	public void setCrud(GenericDAOImpl<Order,OrderPK> crud) {
		this.crud = crud;
	}
	
	
	
	public List<Order> findOrdersByProductInstance(int productInstanceId){
		
		Query query=crud.getEntityManager().createQuery("SELECT o FROM ProductInstance pi " +
				" JOIN pi.orderedItems oi JOIN oi.order o" +
				" WHERE pi.productInstanceId=:productInstanceId ORDER BY o.modified DESC");
		
		query.setParameter("productInstanceId",productInstanceId);
		
		@SuppressWarnings("unchecked")
		List<Order> orders=query.getResultList();
		
		Iterator<Order> iterator=orders.iterator();
		
		while(iterator.hasNext()){
			iterator.next().getDeliveryAddress().getClient().toString();
		}
	
		
		return orders;
	}
	
	

	public List<Order> findOrdersByClient(String login){
		
		Query query=crud.getEntityManager().createQuery("SELECT o FROM Order o JOIN o.deliveryAddress da " +
				" WHERE da.login=:login ORDER BY o.modified DESC");
		
		query.setParameter("login",login);
		
		@SuppressWarnings("unchecked")
		List<Order> orders=query.getResultList();
		
		Iterator<Order> iterator=orders.iterator();
		
		while(iterator.hasNext()){
			iterator.next().getDeliveryAddress().getClient().toString();
		}
	
		
		return orders;
	}
	
	

	public List<Order> findOrdersByClientAndStatus(String login,String dbStatusId){
		
		Query query=crud.getEntityManager().createQuery("SELECT o FROM Order o JOIN o.deliveryAddress da " +
				" WHERE da.login=:login AND o.dbStatusId=:status ORDER BY o.modified DESC");
		
		query.setParameter("login",login);
		query.setParameter("status",dbStatusId);
		

		@SuppressWarnings("unchecked")
		List<Order> orders=query.getResultList();
		
		Iterator<Order> iterator=orders.iterator();
		
		while(iterator.hasNext()){
			iterator.next().getDeliveryAddress().getClient().toString();
		}
	
		
		return orders;
	}
	
	public List<Order> findOrdersByStatus(String dbStatusId){
		
		Query query=crud.getEntityManager().createQuery("SELECT o FROM Order o " +
				" WHERE o.dbStatusId=:status ORDER BY o.modified DESC");
		
		query.setParameter("status",dbStatusId);
		
		@SuppressWarnings("unchecked")
		List<Order> orders=query.getResultList();
		
		Iterator<Order> iterator=orders.iterator();
		
		while(iterator.hasNext()){
			iterator.next().getDeliveryAddress().getClient().toString();
		}
	
		
		return orders;
	}
	
	public List<Order> findAllOrders(){
		
		Query query=crud.getEntityManager().createQuery("SELECT o FROM Order o " +
				" ORDER BY o.modified DESC");
		
		@SuppressWarnings("unchecked")
		List<Order> orders=query.getResultList();
		
		Iterator<Order> iterator=orders.iterator();
		
		while(iterator.hasNext()){
			iterator.next().getDeliveryAddress().getClient().toString();
		}
	
		
		return orders;
	}
	
}
