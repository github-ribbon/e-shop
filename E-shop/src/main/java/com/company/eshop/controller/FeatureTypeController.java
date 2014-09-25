package com.company.eshop.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.eshop.domain.FeatureType;
import com.company.eshop.domain.FeatureTypePK;
import com.company.eshop.service.ShopManagement;
import com.company.eshop.util.Pagination;
import com.company.eshop.validation.FeatureTypeValidator;



@Controller
@RequestMapping(value="/feature-type")
@PreAuthorize(value="hasRole('ROLE_ADMIN')")
public class FeatureTypeController {

	@Autowired
	private FeatureTypeValidator featureTypeValidator;
	
	@Autowired
	private Pagination<FeatureType> pagination;
	
	@Autowired
	private ShopManagement shopManagement;
	
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
	}		
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String displayFeatureTypes(@RequestParam(value="deleted",required=false) String deleted,
			@RequestParam(value="page",required=false) Integer page,
			HttpServletRequest request,Model model) {
				
			pagination.preparePage(model, shopManagement.getAllFeatureTypes(), page,"featureTypes");
		
			return "featureTypeList";				
	}
	
	
	@RequestMapping(value = "/{featureTypeId}", method = RequestMethod.GET)
	public String displayFeatureTypeDetails(@PathVariable String featureTypeId,@RequestParam(value="created",required=false) String added,
			@RequestParam(value="updated",required=false) String updated,HttpServletRequest request,Model model) throws UnsupportedEncodingException {
			
		featureTypeId=URLDecoder.decode(featureTypeId,"utf8");
		
		FeatureTypePK featureTypePK=new FeatureTypePK();
		featureTypePK.setFeatureTypeId(featureTypeId);
		
		if(shopManagement.isFeatureType(featureTypePK)){
			FeatureType featureType=shopManagement.getFeatureType(featureTypePK);
			
			model.addAttribute("isDependent",shopManagement.hasFeatureTypeDependentObjects(featureTypePK));
			model.addAttribute("featureType",featureType);
		}else{
			model.addAttribute("isFeatureTypeIncorrect",true);
		}		
		
		return "featureTypeView";
	}
		
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String displayNewFeatureTypeForm(Model model) {
		
		model.addAttribute("featureType", new FeatureType());
		
		return "newFeatureTypeForm";
	}
	
	
	@RequestMapping(value = "/new",params = "create",method = RequestMethod.POST)
	public String createFeatureType(@Valid FeatureType featureType,BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response,Model model) throws Exception {
			
		featureTypeValidator.validate(featureType, bindingResult);
		
		if(bindingResult.hasErrors()){
			return "newFeatureTypeForm";
		}else{			
			shopManagement.addFeatureType(featureType);
			
			return "redirect:/feature-type/"+URLEncoder.encode(featureType.getId().getFeatureTypeId(),"utf8")+"?created";
		}		
	}
	
	
	@RequestMapping(value = "/",params = "update",method = RequestMethod.POST)
	public String saveFeatureType(@Valid FeatureType featureType,BindingResult bindingResult,Model model) throws UnsupportedEncodingException {
					
		if(bindingResult.hasErrors()){				
			model.addAttribute("isDependent",shopManagement.hasFeatureTypeDependentObjects(featureType.getId()));			
			return "featureTypeView";
		}else{			
			shopManagement.saveFeatureType(featureType);			
			return "redirect:/feature-type/"+URLEncoder.encode(featureType.getId().getFeatureTypeId(),"utf8")+"?updated";
		}		
	}
	
	
	@RequestMapping(value = "/",params = "delete",method = RequestMethod.POST)
	public String deleteFeatureType(FeatureType featureType,BindingResult bindingResult,Model model) {
							
			shopManagement.deleteFeatureType(featureType.getId());			
			return "redirect:/feature-type?deleted";				
	}	
}
