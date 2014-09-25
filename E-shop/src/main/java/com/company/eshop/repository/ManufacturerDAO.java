package com.company.eshop.repository;

import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import com.company.eshop.domain.Manufacturer;
import com.company.eshop.domain.ManufacturerPK;



@Repository
public class ManufacturerDAO{

	
	@Autowired
	@Qualifier(value="manufacturerCRUD")
	private GenericDAOImpl<Manufacturer,ManufacturerPK> crud;
	

	public GenericDAOImpl<Manufacturer,ManufacturerPK> getCrud() {
		return crud;
	}

	public void setCrud(GenericDAOImpl<Manufacturer,ManufacturerPK> crud) {
		this.crud = crud;
	}

	public Long getDependentObjectsNumber(ManufacturerPK manufacturerPK){
		Query query=getCrud().getEntityManager().createQuery("SELECT count(*) FROM Manufacturer m JOIN m.products p WHERE m.manufacturerId=:manufacturerId");
		query.setParameter("manufacturerId",manufacturerPK.getManufacturerId());
		
		return (Long) query.getSingleResult();
	}	
	
}
