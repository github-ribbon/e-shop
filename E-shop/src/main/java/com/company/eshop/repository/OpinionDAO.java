package com.company.eshop.repository;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.company.eshop.domain.Opinion;
import com.company.eshop.domain.OpinionPK;

@Repository
public class OpinionDAO {

	@Autowired
	@Qualifier(value="opinionCRUD")
	private GenericDAOImpl<Opinion,OpinionPK> crud;

	public GenericDAOImpl<Opinion,OpinionPK> getCrud() {
		return crud;
	}

	public void setCrud(GenericDAOImpl<Opinion,OpinionPK> crud) {
		this.crud = crud;
	}
	

	@SuppressWarnings("unchecked")
	public List<Opinion> findOpinionsByProduct(int productId){
		
		Query query=getCrud().getEntityManager().createQuery("SELECT o FROM Opinion o WHERE o.productId=:productId ORDER BY o.added DESC");
		query.setParameter("productId", productId);
		return query.getResultList();
	}
	
}
