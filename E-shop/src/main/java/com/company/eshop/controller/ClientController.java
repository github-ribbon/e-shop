package com.company.eshop.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.eshop.domain.Client;
import com.company.eshop.domain.UsrPK;
import com.company.eshop.service.UserService;
import com.company.eshop.util.Pagination;

@Controller
@RequestMapping(value="/client")
@PreAuthorize(value="hasRole('ROLE_ADMIN')")
public class ClientController {
 
	@Autowired
	private UserService userService;
	

	@Autowired
	private Pagination<Client> pagination;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String displayClients(@RequestParam(value="given_name",required=false) String givenName,
			@RequestParam(value="family_name",required=false) String familyName,
			@RequestParam(value="page",required=false) Integer page,
			HttpServletRequest request,Model model) {
		
		
		if(givenName==null)
			givenName="";
		if(familyName==null)
			familyName="";
		
		
		pagination.preparePage(model,userService.getClients(givenName, familyName), page,"clients");
	
		return "clientList";
	}
	
	
	@RequestMapping(value="/{login}",method = RequestMethod.GET)
	public String displayClientDetails(@PathVariable String login,
			HttpServletRequest request,Model model,Principal principal) throws UnsupportedEncodingException {
		
		login=URLDecoder.decode(login,"utf8");
		
		UsrPK usrPK=new UsrPK();
		usrPK.setLogin(login);
		
		if(userService.isClient(usrPK)){
			
			if(login.equals(principal.getName())){
				model.addAttribute("isLogged",true);
			}else{
				model.addAttribute("isLogged",false);
			}
			
			model.addAttribute("client", userService.getClient(usrPK));
		}else{
			model.addAttribute("isClientIncorrect", true);
		}
		
		return "clientDetails";
	}
	
	
	
}
