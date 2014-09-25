package com.company.eshop.repository;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.company.eshop.domain.Client;
import com.company.eshop.domain.Usr;
import com.company.eshop.domain.UsrPK;



@Repository
public class UsrDAO {

	@Autowired
	@Qualifier(value="usrCRUD")
	private GenericDAOImpl<Usr,UsrPK> crud;
	
	@Autowired
	@Qualifier(value="clientCRUD")
	private GenericDAOImpl<Client,UsrPK> clientCRUD;
	
	public GenericDAOImpl<Usr,UsrPK> getCrud() {
		return crud;
	}

	public void setCrud(GenericDAOImpl<Usr,UsrPK> crud) {
		this.crud = crud;
	}

	
	public GenericDAOImpl<Client,UsrPK> getClientCRUD() {
		return clientCRUD;
	}

	public void setClientCRUD(GenericDAOImpl<Client,UsrPK> clientCRUD) {
		this.clientCRUD = clientCRUD;
	}

	public Usr findUserByLogin(String login){			
		
		Query query = getCrud().getEntityManager().createQuery("SELECT u FROM Usr u WHERE u.id.login=:login");		
		query.setParameter("login",login);	
		
		Usr usr;
		
		try{
			usr=(Usr) query.getSingleResult();
		}catch(NoResultException e){
			return null;
		}	
				
		return usr;			
	}	

	public Usr findUserByEmail(String email){	
		
		Query query = getCrud().getEntityManager().createQuery("SELECT u FROM Usr u WHERE u.email=:email");				
		query.setParameter("email",email);	
	
		Usr usr;
		
		try{
			usr=(Usr) query.getSingleResult();
		}catch(NoResultException e){
			return null;
		}	
				
		return usr;						
	}
	
	
	public Usr findUserByLoginAndEmail(String login,String email){	
		
		Query query = getCrud().getEntityManager().createQuery("SELECT u FROM Usr u WHERE u.id.login=:login AND u.email=:email");				
		query.setParameter("email",email);	
		query.setParameter("login", login);
	
		Usr usr;
		
		try{
			usr=(Usr) query.getSingleResult();
		}catch(NoResultException e){
			return null;
		}	
				
		return usr;						
	}
	


	@SuppressWarnings("unchecked")
	public List<Client> findClients(String givenName,String familyName){
		
		
		Query query=crud.getEntityManager().createQuery("SELECT c FROM Client c WHERE c.givenName LIKE :givenName AND c.familyName LIKE :familyName " +
				" ORDER BY c.givenName,c.familyName");
		
		query.setParameter("givenName","%"+givenName+"%");
		query.setParameter("familyName","%"+familyName+"%");
		
		return query.getResultList();		
	}
	


}
