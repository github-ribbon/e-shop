package com.company.eshop.repository;

import java.util.List;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import com.company.eshop.domain.Image;
import com.company.eshop.domain.ImagePK;



@Repository
public class ImageDAO {


	@Autowired
	@Qualifier(value="imageCRUD")
	private GenericDAOImpl<Image,ImagePK> crud;

	public GenericDAOImpl<Image,ImagePK> getCrud() {
		return crud;
	}

	public void setCrud(GenericDAOImpl<Image,ImagePK> crud) {
		this.crud = crud;
	}
	
	
	public List<Image> findImagesByProduct(int productId){
		
		Query query=crud.getEntityManager().
				createQuery("SELECT i FROM Image i WHERE i.productId=:productId ORDER BY i.imageId");
			
		query.setParameter("productId", productId);
					
		@SuppressWarnings("unchecked")
		List<Image> images=query.getResultList();
					
		return images;				
	} 
		
	
}
