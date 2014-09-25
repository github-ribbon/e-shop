package com.company.eshop.repository;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.company.eshop.domain.Feature;
import com.company.eshop.domain.FeaturePK;



@Repository
public class FeatureDAO {

	@Autowired
	@Qualifier(value="featureCRUD")
	private GenericDAOImpl<Feature,FeaturePK> crud;

	public GenericDAOImpl<Feature,FeaturePK> getCrud() {
		return crud;
	}

	public void setCrud(GenericDAOImpl<Feature,FeaturePK> crud) {
		this.crud = crud;
	}
	
	public List<Feature> findFeaturesByProduct(int productId){
		
		Query query=crud.getEntityManager().
		createQuery("SELECT f FROM Feature f WHERE f.id.productId=:productId ORDER BY f.id.featureTypeId");
	
		query.setParameter("productId", productId);
		
		
		@SuppressWarnings("unchecked")
		List<Feature> features=query.getResultList();
			
		return features;		
	}
}
