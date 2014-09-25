package com.company.eshop.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.eshop.domain.Feature;
import com.company.eshop.domain.FeaturePK;
import com.company.eshop.domain.FeatureTypePK;
import com.company.eshop.domain.Product;
import com.company.eshop.domain.ProductPK;
import com.company.eshop.service.ShopAccess;
import com.company.eshop.service.ShopManagement;
import com.company.eshop.validation.FeatureValidator;

@Controller
@RequestMapping(value="/feature")
@PreAuthorize(value="hasRole('ROLE_ADMIN')")
public class FeatureController {

	@Autowired
	private ShopManagement shopManagement;
	
	@Autowired
	@Qualifier("shopAccessImpl")
	private ShopAccess shopAccess;
	
	@Autowired
	private FeatureValidator featureValidator;
	
	

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
	}		
	
	public Feature getDecodedInstance(String encodedString)
			throws UnsupportedEncodingException, ParseException {		
		
		Feature feature=new Feature();
		feature.setId(new FeaturePK());
		
		String decodedString="";		
		
		try {
			decodedString = URLDecoder.decode(encodedString,"utf8");
			
			String[] params = decodedString.split("&"); 		  
		    	    
		    for (String param : params) {
		    	
		        String name = param.split("=")[0]; 		        
		        String value = "";
		     
		        if(param.split("=").length==2){
		        	value=param.split("=")[1];
		        }	        		        	        	
		       
				value = URLDecoder.decode(value, "utf-8");				
		      
		        
		        if(name.equals("value")){ 
		        	System.out.println("value");
		        	feature.setValue(value);
		        }
		       		        	        
		    }		
		} catch (UnsupportedEncodingException e) {
		}		
		
		return feature;		
	}
	
	
	@RequestMapping(value="/new",params = "choose_feature_type", method = RequestMethod.POST)
	public String chooseFeatureType(Feature feature,BindingResult bindingResult,HttpServletRequest request) throws UnsupportedEncodingException {
						
		String _params="value="+URLEncoder.encode(feature.getValue(),"utf8");		
		
		_params=URLEncoder.encode(_params,"utf8");		
		
		return "redirect:/choose/feature-type?product_id="+feature.getId().getProductId()+"&_params="+_params;		
	}	
	

	@RequestMapping(value = "/new/product/{productId}", method = RequestMethod.GET)
	public String displayNewFeatureForm(@PathVariable int productId,
			@RequestParam(value="_params",required=false) String _params,
			@RequestParam(value="feature_type_id",required=false) String featureTypeId,
			HttpServletRequest request,Model model) throws UnsupportedEncodingException, ParseException {
				
		Feature feature=new Feature();
		
		FeaturePK featurePK=new FeaturePK();
		featurePK.setProductId(productId);
		
		ProductPK productPK=new ProductPK();
		productPK.setProductId(productId);
		
		if(shopAccess.isProduct(productPK)){
					
			if(_params!=null){
				
				feature=getDecodedInstance(_params);
				
				FeatureTypePK featureTypePK=new FeatureTypePK();
				featureTypePK.setFeatureTypeId(featureTypeId);							
				
				if(shopManagement.isFeatureType(featureTypePK)){
					featurePK.setFeatureTypeId(featureTypeId);
				}
			}				
			
			feature.setId(featurePK);
			
			model.addAttribute("feature", feature);	
			
			Product product=shopAccess.getProduct(productPK);
			model.addAttribute("product", product);
			
			if(product.getCategoryId()!=null){				
				model.addAttribute("supCategories",shopAccess.getSupCategoryNames(product.getCategoryId()));
			}
			
		}else{
			model.addAttribute("isProductIncorrect",true);
		}
		
		return "newFeatureForm";
	}	
	
	@RequestMapping(value = "/product/{productId}/feature-type/{featureTypeId}", method = RequestMethod.GET)
	public String displayFeatureDetails(@PathVariable String featureTypeId,@PathVariable int productId,@RequestParam(value="created",required=false) String created,
			@RequestParam(value="updated",required=false) String updated,HttpServletRequest request,Model model) throws UnsupportedEncodingException {
			
		featureTypeId=URLDecoder.decode(featureTypeId,"utf8");
		
		FeaturePK featurePK=new FeaturePK();
		featurePK.setFeatureTypeId(featureTypeId);
		featurePK.setProductId(productId);
		
		if(shopManagement.isFeature(featurePK)){
			
			ProductPK productPK=new ProductPK();
			productPK.setProductId(productId);
			
			Product product=shopAccess.getProduct(productPK);
			
			if(product.getCategoryId()!=null){
				model.addAttribute("originalCategoryId", product.getCategoryId());
				model.addAttribute("supCategories",shopAccess.getSupCategoryNames(product.getCategoryId()));
			}
			
			model.addAttribute("productName", product.getName());
			
			Feature feature=shopManagement.getFeature(featurePK);
			
			model.addAttribute("feature",feature);
		}else{
			model.addAttribute("isFeatureIncorrect",true);
		}		
		
		return "featureView";
	}
	
	
	
	@RequestMapping(value = "/new",params = "create",method = RequestMethod.POST)
	public String createFeature(@Valid Feature feature,BindingResult bindingResult,Model model) throws UnsupportedEncodingException {
			
		featureValidator.validate(feature, bindingResult);
		
		if(bindingResult.hasErrors()){
			
			ProductPK productPK=new ProductPK();
			productPK.setProductId(feature.getId().getProductId());
			
			model.addAttribute("product", shopAccess.getProduct(productPK));
			
			return "newFeatureForm";
		}else{			
			shopManagement.addFeature(feature);
			return "redirect:/feature/product/"+feature.getId().getProductId()+"/feature-type/"+URLEncoder.encode(feature.getId().getFeatureTypeId(),"utf8")+"?created";
		}		
	}
	
	
	@RequestMapping(value = "/",params = "update",method = RequestMethod.POST)
	public String saveFeature(@Valid Feature feature,BindingResult bindingResult,Model model) throws UnsupportedEncodingException {
					
		if(bindingResult.hasErrors()){	
			
			ProductPK productPK=new ProductPK();
			productPK.setProductId(feature.getId().getProductId());
			
			model.addAttribute("productName", shopAccess.getProduct(productPK).getName());			
			
			return "featureView";
		}else{			
			shopManagement.saveFeature(feature);
			
			return "redirect:/feature/product/"+feature.getId().getProductId()+"/feature-type/"+URLEncoder.encode(feature.getId().getFeatureTypeId(),"utf8")+"?updated";
		}		
	}
	
	

	@RequestMapping(value = "/",params = "delete",method = RequestMethod.POST)
	public String deleteFeature(Feature feature,BindingResult bindingResult,Model model) {
							
			shopManagement.deleteFeature(feature.getId());
			
			return "redirect:/product/"+feature.getId().getProductId()+"?feature_deleted";				
	}	
}
