package com.company.eshop.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;



public class GenericDAOImpl<T, PK extends Serializable> implements GenericDAO<T, PK>{
	
	
    public Class<T> entityClass;    
       
   
//  private static final String PERSISTENCE_UNIT_NAME = "E-shop";
    

//	private EntityManagerFactory factory=Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);	
	

    @PersistenceContext
	private EntityManager entityManager;  
    
    

	public GenericDAOImpl(Class<T> entityClass){       
       setEntityClass(entityClass);
//	   setEntityManager(factory.createEntityManager());		
    }    
   
	public GenericDAOImpl(){
		
	}
	
    public Class<T> getEntityClass(){
		return entityClass;
	}

   
	public void setEntityClass(Class<T> entityClass){
		this.entityClass = entityClass;
	}

	
	public synchronized EntityManager getEntityManager() {
		return entityManager;
	}
	
	public synchronized void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	
	
	public T insert(T t){
    			
//    	entityManager.getTransaction().begin();
//    	entityManager.persist(t);
//    	entityManager.getTransaction().commit();    	
    	
		
		entityManager.persist(t);
		
		
        return t;
    }

   
    public T read(PK pk){   	

    	T t=entityManager.find(getEntityClass(), pk);
    	    	
        return t;       
    }

  
    public void update(T t){
    	
//    	entityManager.getTransaction().begin();    	  
//    	entityManager.merge(t);        
//    	entityManager.getTransaction().commit();    	
    	
    	entityManager.merge(t);    	
    }

   
   
    public void delete(T t){
    	  
//    	entityManager.getTransaction().begin();   
//        t = entityManager.merge(t);
//        entityManager.remove(t);    
//        entityManager.getTransaction().commit();
    	    	
    	t = entityManager.merge(t);
    	entityManager.remove(t);     
    }
     
    
	public List<T> getAllRows(){
		
		String tableName=getEntityClass().getSimpleName();
		String orderBy="";
		
		if(tableName.equals("Manufacturer")) orderBy="o.name";
		else if(tableName.equals("FeatureType")) orderBy="o.id.featureTypeId";	
		
		
		Query query=entityManager.createQuery("SELECT o FROM "+tableName+" o ORDER BY "+orderBy);
		
		@SuppressWarnings("unchecked")
		List<T> resultList = query.getResultList();
		
		return resultList;		
	}	
}

