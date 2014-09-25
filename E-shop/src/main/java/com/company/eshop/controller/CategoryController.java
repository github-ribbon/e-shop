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

import com.company.eshop.domain.Category;
import com.company.eshop.domain.CategoryPK;
import com.company.eshop.service.ShopAccess;
import com.company.eshop.service.ShopManagement;
import com.company.eshop.validation.CategoryValidator;
 
@Controller
@RequestMapping(value="/category")
@PreAuthorize(value="hasRole('ROLE_ADMIN')")
public class CategoryController{

	@Autowired
	private ShopManagement shopManagement;
	
	
	@Autowired
	@Qualifier("shopAccessImpl")
	private ShopAccess shopAccess;
	
	@Autowired
	private CategoryValidator categoryValidator;
	
	
	public Category getDecodedInstance(String encodedString)
			throws UnsupportedEncodingException, ParseException {
		
		
		Category category=new Category();
		category.setId(new CategoryPK());
		
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
		        
		        
		        if(name.equals("description")){ 
		        	category.setDescription(value);
		        }else if(name.equals("category_id")){
		        	category.getId().setCategoryId(value);
		        }
		       		        	        
		    }		
		} catch (UnsupportedEncodingException e) {
		}
		
		
		return category;		
	}	
	
	
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
	}	
	
	
	@RequestMapping(value="/new",params = "choose_category", method = RequestMethod.POST)
	public String chooseSupCategory(Category category,BindingResult bindingResult,HttpServletRequest request) throws UnsupportedEncodingException {
						
		String _params="category_id="+URLEncoder.encode(category.getId().getCategoryId(),"utf8")+"&description="+URLEncoder.encode(category.getDescription(),"utf8");		
		
		_params=URLEncoder.encode(_params,"utf8");		
		
		return "redirect:/choose/category?source=category&action=create&_params="+_params;		
	}	
	
	@RequestMapping(value="/",params = "choose_category", method = RequestMethod.POST)
	public String chooseSupCategory(Category category,HttpServletRequest request) throws UnsupportedEncodingException {
						
		String _params="description="+URLEncoder.encode(category.getDescription(),"utf8");		
		
		_params=URLEncoder.encode(_params,"utf8");		
		
		return "redirect:/choose/category?source=category&category_id="+URLEncoder.encode(category.getId().getCategoryId(),"utf8")+"&action=view&_params="+_params;		
	}	
	
	@RequestMapping(value="/",params = "delete_category", method = RequestMethod.POST)
	public String deleteSupCategory(Category category,HttpServletRequest request) throws UnsupportedEncodingException {
						
		String _params="description="+URLEncoder.encode(category.getDescription(),"utf8");		
		
		_params=URLEncoder.encode(_params,"utf8");		
		
		return "redirect:/category/"+URLEncoder.encode(category.getId().getCategoryId(),"utf8")+"?sup_category_id=&_params="+_params;		
	}	
	
	@RequestMapping(value="/new",params = "delete_category", method = RequestMethod.POST)
	public String new_deleteSupCategory(Category category,HttpServletRequest request) throws UnsupportedEncodingException {
						
		String _params="description="+URLEncoder.encode(category.getDescription(),"utf8");		
		
		_params=URLEncoder.encode(_params,"utf8");		
		
		return "redirect:/category/new?sup_category_id=&_params="+_params;		
	}
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String displayCategories(@RequestParam(value="deleted",required=false) String deleted,HttpServletRequest request){
		
		return "categoryList";
	}
	
	
	@RequestMapping(value = "/{categoryId}", method = RequestMethod.GET)
	public String displayCategoryDetails(@PathVariable String categoryId,@RequestParam(value="created",required=false) String created,
			@RequestParam(value="updated",required=false) String updated,
			@RequestParam(value="_params",required=false) String _params,
			@RequestParam(value="sup_category_id",required=false) String supCategoryId,
			HttpServletRequest request,Model model) throws UnsupportedEncodingException, ParseException {
			
		
		categoryId=URLDecoder.decode(categoryId,"utf8");
		
		CategoryPK categoryPK=new CategoryPK();
		categoryPK.setCategoryId(categoryId);
		
		Category category=new Category();
		
		if(_params!=null){
			category=getDecodedInstance(_params);
			category.setId(categoryPK);
			
			category.setSupCategoryId(supCategoryId);
			
			CategoryPK supCategoryPK=new CategoryPK();
			supCategoryPK.setCategoryId(supCategoryId);
			
			if(!shopManagement.isCategory(supCategoryPK)){
				category.setSupCategoryId(null);
			}			
		}
		
		if(shopManagement.isCategory(categoryPK)){
			
			if(_params==null){				
				category=shopAccess.getCategory(categoryPK);
				
				
			}
			
			model.addAttribute("isDependent",shopManagement.hasCategoryDependentObjects(categoryPK));
			
			
			model.addAttribute("category",category);
		}else{
			model.addAttribute("isCategoryIncorrect",true);
		}		
		
		
		return "categoryView";
	}
	
	
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String displayNewCategoryForm(@RequestParam(value="_params",required=false) String _params,
			@RequestParam(value="sup_category_id",required=false) String supCategoryId,
			HttpServletRequest request,Model model) throws UnsupportedEncodingException, ParseException {
		
		Category category=new Category();
		
		if(_params!=null){
			category=getDecodedInstance(_params);
			category.setSupCategoryId(supCategoryId);
			
			CategoryPK categoryPK=new CategoryPK();
			categoryPK.setCategoryId(supCategoryId);
			
			if(!shopManagement.isCategory(categoryPK)){
				category.setSupCategoryId(null);
			}			
		}
		
		model.addAttribute("category", category);
		
		return "newCategoryForm";
	}
	
	
	
	@RequestMapping(value = "/new",params = "create",method = RequestMethod.POST)
	public String createCategory(@Valid Category category,BindingResult bindingResult,Model model) throws UnsupportedEncodingException {
			
		categoryValidator.validateBeforeCreating(category, bindingResult);
		
		if(bindingResult.hasErrors()){
			
			return "newCategoryForm";
		}else{			
			shopManagement.addCategory(category);
			return "redirect:/category/"+URLEncoder.encode(category.getId().getCategoryId(),"utf8")+"?created";
		}		
	}
	
	
	@RequestMapping(value = "/",params = "update",method = RequestMethod.POST)
	public String saveCategory(@Valid Category category,BindingResult bindingResult,Model model) throws UnsupportedEncodingException {
					
		categoryValidator.validateBeforeUpdate(category, bindingResult);
		
		if(bindingResult.hasErrors()){		
			
			model.addAttribute("isDependent",shopManagement.hasCategoryDependentObjects(category.getId()));
			
			return "categoryView";
		}else{			
			shopManagement.saveCategory(category);
			
			return "redirect:/category/"+URLEncoder.encode(category.getId().getCategoryId(),"utf8")+"?updated";
		}		
	}
	
	
	
	@RequestMapping(value = "/",params = "delete",method = RequestMethod.POST)
	public String deleteCategory(Category category,BindingResult bindingResult,Model model) {
						
			shopManagement.deleteCategory(category.getId());
			
			return "redirect:/category?deleted";				
	}


	
	
}
