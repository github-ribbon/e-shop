package com.company.eshop.repository;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.company.eshop.domain.FeatureType;
import com.company.eshop.domain.FeatureTypePK;



@Repository
public class FeatureTypeDAO {

	@Autowired
	@Qualifier(value="featureTypeCRUD")
	private GenericDAOImpl<FeatureType,FeatureTypePK> crud;

	public GenericDAOImpl<FeatureType,FeatureTypePK> getCrud() {
		return crud;
	}

	public void setCrud(GenericDAOImpl<FeatureType,FeatureTypePK> crud) {
		this.crud = crud;
	}
	

	public Long getDependentObjectsNumber(FeatureTypePK featureTypePK){
		Query query=getCrud().getEntityManager().createQuery("SELECT count(*) FROM FeatureType ft JOIN ft.features f WHERE ft.id.featureTypeId=:featureTypeId");
		query.setParameter("featureTypeId",featureTypePK.getFeatureTypeId());
		
		return (Long) query.getSingleResult();
	}
	
}
