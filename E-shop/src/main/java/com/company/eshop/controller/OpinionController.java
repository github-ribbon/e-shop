package com.company.eshop.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.company.eshop.domain.ManufacturerPK;
import com.company.eshop.domain.Opinion;
import com.company.eshop.domain.Product;
import com.company.eshop.domain.ProductPK;
import com.company.eshop.domain.UsrPK;
import com.company.eshop.service.ShopAccess;
import com.company.eshop.service.ShopManagement;
import com.company.eshop.service.Shopping;

@Controller
@RequestMapping(value="/opinion")
@PreAuthorize(value="hasRole('ROLE_CLIENT')")
public class OpinionController {
	
	@Autowired
	private Shopping shopping;
	
	@Autowired
	private ShopManagement shopManagement;
	
	@Autowired
	@Qualifier("shopAccessImpl")
	private ShopAccess shopAccess;
	
	

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
	}	
	
	@RequestMapping(value = "/new",params = "create",method = RequestMethod.POST)
	public String addOpinion(@Valid Opinion opinion,BindingResult bindingResult,Model model,Principal principal) {
			
		ProductPK productPK=new ProductPK();
		productPK.setProductId(opinion.getProductId());
		
		UsrPK usrPK=new UsrPK();
		usrPK.setLogin(principal.getName());
		
		if(!shopping.isProductBoughtByClient(productPK, usrPK)){
			throw new RuntimeException();
		}
		
		if(bindingResult.hasErrors()){			
			
			Product product=shopAccess.getProduct(productPK);
			model.addAttribute("product",product);
			
			ManufacturerPK manufacturerPK=new ManufacturerPK();
			manufacturerPK.setManufacturerId(product.getManufacturerId());
			
			model.addAttribute("manufacturerName",shopManagement.getManufacturer(manufacturerPK).getName());
			
			model.addAttribute("images",shopAccess.getImages(productPK));
			model.addAttribute("features",shopAccess.getFeaturesByProduct(productPK));
			model.addAttribute("opinions",shopping.getOpinionsByProduct(productPK));			
			
			
			if(shopping.isProductBoughtByClient(productPK, usrPK)){
				model.addAttribute("opinion",opinion);
			}					
			
			if(product.getCategoryId()!=null){
				model.addAttribute("supCategories",shopAccess.getSupCategoryNames(product.getCategoryId()));				
			}
			
			model.addAttribute("opinionErrors", true);
			
			return "productView";
		}else{	
			shopping.addOpinion(opinion);			
			return "redirect:/product/"+opinion.getProductId()+"?opinion_created";
		}		
	}

}
