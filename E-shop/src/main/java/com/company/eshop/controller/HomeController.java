package com.company.eshop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.eshop.service.ShopAccess;

@Controller
public class HomeController {

	
	@Autowired
	@Qualifier("shopAccessImpl")
	private ShopAccess shopAccess;
	
	

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(@RequestParam(value="failure",required=false) String failure,
			@RequestParam(value="logged_out",required=false) String loggedOut,			
			@RequestParam(value="page",required=false) Integer page,			
			HttpServletRequest request,HttpServletResponse response,
			Model model){
		
		
		model.addAttribute("bestsellers",shopAccess.getBestsellers());
		
		return "home";		
	}
		
	
	@RequestMapping(value = "/denied", method = RequestMethod.GET)
	public String accessDenied(HttpServletRequest request,HttpServletResponse response,Model model) throws Exception {	
		
		return "errors/accessDeniedException";		
	}
	
}
