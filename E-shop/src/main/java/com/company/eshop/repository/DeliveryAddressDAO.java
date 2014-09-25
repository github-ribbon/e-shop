package com.company.eshop.repository;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.company.eshop.domain.DeliveryAddress;
import com.company.eshop.domain.DeliveryAddressPK;



@Repository
public class DeliveryAddressDAO {

	@Autowired
	@Qualifier(value="deliveryAddressCRUD")
	private GenericDAOImpl<DeliveryAddress,DeliveryAddressPK> crud;

	public GenericDAOImpl<DeliveryAddress,DeliveryAddressPK> getCrud() {
		return crud;
	}

	public void setCrud(GenericDAOImpl<DeliveryAddress,DeliveryAddressPK> crud) {
		this.crud = crud;
	}
	
	@SuppressWarnings("unchecked")
	public List<DeliveryAddress> findDeliveryAddressesByClient(String login){
		
		Query query=crud.getEntityManager().createQuery("SELECT a FROM DeliveryAddress a WHERE a.login=:login");
		query.setParameter("login",login);
		
		return query.getResultList();		
	}
	
	


	public Long getDependentObjectsNumber(DeliveryAddressPK deliveryAddressPK){
		
			Query query=getCrud().getEntityManager().createQuery("SELECT count(*) FROM DeliveryAddress da JOIN da.orders o WHERE da.deliveryAddressId=:deliveryAddressId");
			query.setParameter("deliveryAddressId",deliveryAddressPK.getDeliveryAddressId());
			
			return (Long) query.getSingleResult();
	
	}
}
