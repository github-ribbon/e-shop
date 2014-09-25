package com.company.eshop.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.eshop.domain.DeliveryAddress;
import com.company.eshop.domain.FeatureType;
import com.company.eshop.domain.Manufacturer;
import com.company.eshop.domain.UsrPK;
import com.company.eshop.service.ShopAccess;
import com.company.eshop.service.ShopManagement;
import com.company.eshop.service.Shopping;
import com.company.eshop.util.Pagination;

@Controller
@RequestMapping(value="/choose")
public class ChooseController {

	@Autowired
	private ShopManagement shopManagement;
	
	@Autowired
	private Pagination<FeatureType> featureTypePagination;
	
	@Autowired
	private Pagination<DeliveryAddress> deliveryAddressPagination;
	
	@Autowired
	private Pagination<Manufacturer> manufacturerPagination;
	
	@Autowired
	private Shopping shopping;
	
	@Autowired
	@Qualifier("shopAccessImpl")
	private ShopAccess shopAccess;

	
	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public String chooseCategory(@RequestParam(required=true,value="source") String source,
			@RequestParam(value="category_id",required=false) String categoryId,
			@RequestParam(value="product_id",required=false) Integer productId,
			@RequestParam(value="_params",required=true) String _params,
			@RequestParam(value="action",required=true) String action,
			HttpServletRequest request,Model model,Authentication authentication) {
				
		
		boolean isAdmin=false;
		
		if(authentication!=null){			
			if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
				isAdmin=true;
			}		
		}
		
		
		boolean isProductList;
		
		if((isProductList=((source.equals("product"))&&(action.equals("list"))))||(isAdmin)){
			
			boolean isObjectIncorrect;
			
			if((((source.equals("category"))||(source.equals("product")))&&((action.equals("create"))||(action.equals("view"))))||(isProductList)){
				isObjectIncorrect=false;
			}else{
				isObjectIncorrect=true;
			}
			
			model.addAttribute("isObjectIncorrect", isObjectIncorrect);
			
		}else{
			return "redirect:/denied";
		}
		
		return "chooseCategory";
	}
	

	@PreAuthorize(value="hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/feature-type", method = RequestMethod.GET)
	public String chooseFeatureType(	
			@RequestParam(value="product_id",required=true) int productId,
			@RequestParam(value="page",required=false) Integer page,
			@RequestParam(value="_params",required=true) String _params,
			HttpServletRequest request,Model model) {
			
		
		featureTypePagination.preparePage(model, shopManagement.getAllFeatureTypes(), page,"featureTypes");
		
		return "chooseFeatureType";
	}
	
	@PreAuthorize(value="hasRole('ROLE_CLIENT')")
	@RequestMapping(value = "/delivery-address", method = RequestMethod.GET)
	public String chooseDeliveryAddress(@RequestParam(value="page",required=false) Integer page,HttpServletRequest request,Model model,Principal principal) {
			
		UsrPK usrPK=new UsrPK();
		usrPK.setLogin(principal.getName());
		
		deliveryAddressPagination.preparePage(model, shopping.getDeliveryAddresses(usrPK), page,"deliveryAddresses");
		
		model.addAttribute("login",principal.getName());
		
		return "chooseDeliveryAddress";
	}
	
	
	

	@RequestMapping(value = "/manufacturer", method = RequestMethod.GET)
	public String chooseManufacturer(	
			@RequestParam(value="page",required=false) Integer page,
			@RequestParam(value="product_id",required=false) Integer productId,
			@RequestParam(value="action",required=true) String action,
			@RequestParam(value="_params",required=true) String _params,
			HttpServletRequest request,Model model,Authentication  authentication) {
			
		
		
		boolean isObjectIncorrect;
		
		if(action.equals("create")||action.equals("view")||action.equals("list")){
			isObjectIncorrect=false;
		}else{
			isObjectIncorrect=true;
		}
		
		
		if(authentication==null){			
			
			if(action.equals("list")){
				isObjectIncorrect=false;
			}else if(action.equals("create")||action.equals("view")){
				return "redirect:/denied";
			}
			
		}else{
			
			if((action.equals("create")||action.equals("view"))&&(!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))){		
				return "redirect:/denied";
			}

		}
		
		model.addAttribute("isObjectIncorrect", isObjectIncorrect);
		
		
		if(!isObjectIncorrect)
			manufacturerPagination.preparePage(model, shopManagement.getAllManufacturers(), page,"manufacturers");
				
		return "chooseManufacturer";
	}

}
