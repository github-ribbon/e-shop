package com.company.eshop.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.eshop.domain.DeliveryAddress;
import com.company.eshop.domain.DeliveryAddressPK;
import com.company.eshop.domain.UsrPK;
import com.company.eshop.service.ShopAccess;
import com.company.eshop.service.Shopping;
import com.company.eshop.util.Pagination;


@Controller
@RequestMapping(value="/delivery-address")
@PreAuthorize(value="hasRole('ROLE_CLIENT')")
public class DeliveryAddressController {
	
	
	@Autowired
	private Shopping shopping;
	
	@Autowired
	@Qualifier("shopAccessImpl")
	private ShopAccess shopAccess;
	
	@Autowired
	private Pagination<DeliveryAddress> pagination;
	
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
	}		
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String displayDeliveryAddresses(	@RequestParam(value="page",required=false) Integer page,
			@RequestParam(value="deleted",required=false) String deleted,HttpServletRequest request,
			Model model,Authentication authentication) {
			
		UsrPK usrPK=new UsrPK();				
		usrPK.setLogin(authentication.getName());
		
		pagination.preparePage(model,shopping.getDeliveryAddresses(usrPK), page,"deliveryAddresses");
	
		return "deliveryAddressList";			
	}
	
	 

	@RequestMapping(value = "/{deliveryAddressId}", method = RequestMethod.GET)
	public String displayDeliveryAddressDetails(@PathVariable int  deliveryAddressId,
			@RequestParam(value="created",required=false) String created,
			@RequestParam(value="updated",required=false) String updated,
			HttpServletRequest request,Model model,
			Authentication authentication) {
		
		
		DeliveryAddressPK deliveryAddressPK=new DeliveryAddressPK();
		deliveryAddressPK.setDeliveryAddressId(deliveryAddressId);
		
		if(shopping.isDeliveryAddress(deliveryAddressPK)){
			
			DeliveryAddress deliveryAddress=shopping.getDeliveryAddress(deliveryAddressPK);
			
			model.addAttribute("isDependent",shopping.hasDeliveryAddressDependentObjects(deliveryAddressPK));
			
			if(deliveryAddress.getLogin().equals(authentication.getName())){
				model.addAttribute("deliveryAddress",deliveryAddress);
			}else{
				model.addAttribute("isDeliveryAddressIncorrect",true);
			}
			
		}else{
			model.addAttribute("isDeliveryAddressIncorrect",true);
		}		
		
		return "deliveryAddressView";
	}
	
	
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String displayNewDeliveryAddressForm(HttpServletRequest request,Model model,Principal principal) {
					
		DeliveryAddress deliveryAddress=new DeliveryAddress();
		deliveryAddress.setLogin(principal.getName());
			
		model.addAttribute("deliveryAddress",deliveryAddress);				
		
		return "newDeliveryAddressForm";
	}	
	
	@RequestMapping(value = "/new",params = "create",method = RequestMethod.POST)
	public String createDeliveryAddress(@Valid DeliveryAddress deliveryAddress,BindingResult bindingResult,Model model,Principal principal) {
					
		if(!deliveryAddress.getLogin().equals(principal.getName())){
			bindingResult.rejectValue("login","error.incorrectClient");
		}		
		
		if(bindingResult.hasErrors()){			
			return "newDeliveryAddressForm";
		}else{			
			shopping.addDeliveryAddress(deliveryAddress);
			return "redirect:/delivery-address/"+deliveryAddress.getDeliveryAddressId()+"?created";
		}		
	}	

	@RequestMapping(value = "/",params = "update",method = RequestMethod.POST)
	public String saveDeliveryAddress(@Valid DeliveryAddress deliveryAddress,BindingResult bindingResult,Model model,Principal principal) {
					
		if(!deliveryAddress.getLogin().equals(principal.getName())){
			bindingResult.rejectValue("login","error.incorrectClient");
		}	
		
		if(bindingResult.hasErrors()){		
			
			DeliveryAddressPK deliveryAddressPK=new DeliveryAddressPK();
			deliveryAddressPK.setDeliveryAddressId(deliveryAddress.getDeliveryAddressId());
			
			model.addAttribute("isDependent",shopping.hasDeliveryAddressDependentObjects(deliveryAddressPK));
			
			return "deliveryAddressView";
		}else{			
			shopping.saveDeliveryAddress(deliveryAddress);			
			return "redirect:/delivery-address/"+deliveryAddress.getDeliveryAddressId()+"?updated";
		}		
	}
	
	
	@RequestMapping(value = "/",params = "delete",method = RequestMethod.POST)
	public String deleteDeliveryAddress(DeliveryAddress deliveryAddress,BindingResult bindingResult,Model model,Principal principal) {
		
		if(!deliveryAddress.getLogin().equals(principal.getName())){
			 throw new RuntimeException();
		}	
		
		DeliveryAddressPK deliveryAddressPK=new DeliveryAddressPK();
		deliveryAddressPK.setDeliveryAddressId(deliveryAddress.getDeliveryAddressId());
		
		shopping.deleteDeliveryAddress(deliveryAddressPK);
			
		return "redirect:/delivery-address?deleted";				
	}		
}
