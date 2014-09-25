package com.company.eshop.service;


import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.eshop.domain.Client;
import com.company.eshop.domain.Usr;
import com.company.eshop.domain.UsrPK;
import com.company.eshop.repository.UsrDAO;
import com.twmacinta.util.MD5;


@Service
public class UserService {
	
	@Autowired
	private UsrDAO usrDAO;	
	
	@Autowired
	@Qualifier(value="javaMailSender")
	private JavaMailSenderImpl javaMailSender;
	
	
	public UsrDAO getUsrDAO() {
		return usrDAO;
	}

	public void setUsrDAO(UsrDAO usrDAO) {
		this.usrDAO = usrDAO;
	}	

	private static String getEncryptedPassword(String password){
		
		MD5 md5 = new MD5();
		
	    try {
			md5.Update(password,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}		    
    	
	    return 	md5.asHex();	
	}	
	
	private static final String getRandomPassword(){

		return RandomStringUtils.randomAlphanumeric(10);
	}	
	
	@Transactional
	public void registerUser(Usr usr){
		
		String plainPassword=usr.getPassword();
		usr.setPassword(UserService.getEncryptedPassword(plainPassword));
		usr.setIsEnabled(true);
		UsrPK usrPK=usr.getId();
		
		Client client=usr.getClient();
		client.setId(usrPK);
		usr.setAdmin(null);
		
		getUsrDAO().getCrud().insert(usr);
		getUsrDAO().getClientCRUD().insert(client);
		

		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
			
		mailMessage.setTo(usr.getEmail());
		mailMessage.setSubject("Rejestracja zakończona pomyślnie");			
		mailMessage.setText("Rejestracja przebiegła bez problemów. Szczegóły twojego konta: \n\n\n login: " + usr.getId().getLogin()+"\n hasło: "+plainPassword+
		               "\n\n\n   Życzymy udanych zakupów !!!");
		
			
		
		javaMailSender.send(mailMessage);
		
		
	}
	
	@Transactional(readOnly=true)
	public boolean isUser(UsrPK usrPK){
		
		if(getUsrDAO().findUserByLogin(usrPK.getLogin())!=null){
				return true;
			}else
				return false;
		
	}
	
	@Transactional(readOnly=true)
	public boolean isUser(String email){
		
		if(getUsrDAO().findUserByEmail(email)!=null){
				return true;
			}else
				return false;
		
	}

	
	public void saveSessionUserDetails(HttpSession session,Usr usr){
		session.setAttribute("usr",usr);
	}
	
	@Transactional(readOnly=true)
	public Client getClient(UsrPK usrPK){
		Client client=getUsrDAO().getClientCRUD().read(usrPK);
		
		if(client!=null)
			client.getUsr().toString();
		
		return client;
	}
	
	
	@Transactional(readOnly=true)
	public boolean isClient(UsrPK usrPK){
		
		if(getUsrDAO().getClientCRUD().read(usrPK)!=null){
			return true;
		}else
			return false;
		
	}
	
	@Transactional(readOnly=true)
	public List<Client> getClients(String givenName,String familyName){	
		return getUsrDAO().findClients(givenName, familyName);
	}
	
	@Transactional
	public void updateUserDetails(Usr usr){
	
		UsrPK usrPK=usr.getId();
		
		Usr populated=getUsrDAO().getCrud().read(usrPK);
		
		usr.setPassword(UserService.getEncryptedPassword(usr.getPassword()));
		usr.setIsEnabled(true);
		usr.setEmail(populated.getEmail());
	
		
		Client client=usr.getClient();
				
		if(client!=null){
			client.setId(usrPK);
			getUsrDAO().getClientCRUD().update(client);
		}
	
		
		getUsrDAO().getCrud().update(usr);
		
		
	}
	
	
	@Transactional(readOnly=true)
	public Usr getUsr(UsrPK usrPK){
		
		Usr usr=getUsrDAO().getCrud().read(usrPK);
		
		if(usr.getClient()!=null)			
			usr.getClient().toString();
		if(usr.getAdmin()!=null)
			usr.getAdmin().toString();
		
		return usr;		
	}
	
	
	
	@Transactional
	public void createClientAccountForAdmin(Client client){	
		getUsrDAO().getClientCRUD().insert(client);				
	}
	
	
	@Transactional(readOnly=true)
	public boolean isUser(String login,String email){
		
		if(getUsrDAO().findUserByLoginAndEmail(login,email)!=null){
				return true;
			}else
				return false;
		
	}
	
	@Transactional
	public void resetPassword(String login, String email){
		
		Usr usr=usrDAO.findUserByLoginAndEmail(login, email);	
		
		String password=getRandomPassword();	
		String plainPassword=password;
		
		
		usr.setPassword(getEncryptedPassword(password));
	
				
		usrDAO.getCrud().update(usr);
		
		
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
			
		mailMessage.setTo(usr.getEmail());
		mailMessage.setSubject("Twoje hasło zostało zresetowane");			
		mailMessage.setText("Twoje hasło zostało pomyślnie zresetowane.\n\n\n login: " + usr.getId().getLogin()+"\n wygenerowane hasło: "+plainPassword+
		               "\n\n\n");
		
			
		
		javaMailSender.send(mailMessage);
		
				
	}	

}
