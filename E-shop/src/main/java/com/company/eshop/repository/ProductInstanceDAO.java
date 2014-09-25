package com.company.eshop.repository;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.company.eshop.domain.ProductInstance;
import com.company.eshop.domain.ProductInstancePK;



@Repository
public class ProductInstanceDAO {


	@Autowired
	@Qualifier(value="productInstanceCRUD")
	private GenericDAOImpl<ProductInstance,ProductInstancePK> crud;

	public GenericDAOImpl<ProductInstance,ProductInstancePK> getCrud() {
		return crud;
	}

	public void setCrud(GenericDAOImpl<ProductInstance,ProductInstancePK> crud) {
		this.crud = crud;
	}
	
	
	public List<ProductInstance> findProductInstances(int productId){
		
		Query query=crud.getEntityManager().createQuery("SELECT pi FROM ProductInstance pi WHERE pi.productId=:productId ORDER BY pi.added");
		query.setParameter("productId", productId);
				
		@SuppressWarnings("unchecked")
		List<ProductInstance> list=query.getResultList();
			
		return list;		
	}
	
	
	
	
	public Long getDependentObjectsNumber(ProductInstancePK productInstancePK){
			
		Query query=getCrud().getEntityManager().createQuery("SELECT count(*) FROM ProductInstance pi " +
				" JOIN pi.orderedItems oi WHERE pi.productInstanceId=:productInstanceId");
		query.setParameter("productInstanceId",productInstancePK.getProductInstanceId());
		
		return (Long) query.getSingleResult();
	
	}
	
	
	@SuppressWarnings("unchecked")
	public List<ProductInstance> findAvailableProductInstances(int productId,int size){
		
			Query query=crud.getEntityManager().createQuery("SELECT pi FROM ProductInstance pi WHERE pi.productId=:productId AND " +
					" NOT EXISTS (SELECT oi FROM OrderedItem oi JOIN oi.order o WHERE o.dbStatusId != 'D' AND " +
					" oi.id.productInstanceId=pi.productInstanceId)");
			
			query.setParameter("productId",productId);
			query.setMaxResults(size);
			
			return query.getResultList();		
		}
		
		
	@SuppressWarnings("unchecked")
	public List<ProductInstance> findProductInstancesByOrder(int orderId){
		
		Query query=crud.getEntityManager().createQuery("SELECT pi FROM OrderedItem oi JOIN oi.productInstance pi WHERE oi.id.orderId=:orderId");
		
		query.setParameter("orderId",orderId);
		
		
		return query.getResultList();		
	}
	
	
	public Long getBoughtProductInstancesNumber(int productId,String login){
		
		Query query=crud.getEntityManager().createQuery("SELECT count(pi) FROM ProductInstance pi " +
				" JOIN pi.orderedItems oi JOIN oi.order o JOIN o.deliveryAddress da " +
				" WHERE pi.productId=:productId AND o.dbStatusId='F' AND da.login=:login");
		query.setParameter("productId", productId);
		query.setParameter("login", login);
		
		return (Long) query.getSingleResult();		
	}
	
}
