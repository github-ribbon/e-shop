package com.company.eshop.controller;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import com.company.eshop.domain.ManufacturerPK;
import com.company.eshop.domain.Opinion;
import com.company.eshop.domain.Product;
import com.company.eshop.domain.ProductPK;
import com.company.eshop.domain.UsrPK;
import com.company.eshop.service.ShopAccess;
import com.company.eshop.service.ShopManagement;
import com.company.eshop.service.Shopping;
import com.company.eshop.util.Pagination;
import com.company.eshop.util.ProductSearch;
import com.company.eshop.validation.ProductValidator;

@Controller
@RequestMapping(value="/product")
public class ProductController {

	@Autowired
	private ShopManagement shopManagement;
	
	@Autowired
	private Shopping shopping;
	
	@Autowired
	@Qualifier("shopAccessImpl")
	private ShopAccess shopAccess;
	
	@Autowired
	private ProductValidator productValidator;
	
	@Autowired
	private Pagination<Product> pagination;
	
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
		
		DecimalFormat decimalFormat = new DecimalFormat();
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator(',');
		symbols.setGroupingSeparator(' ');
		decimalFormat.setDecimalFormatSymbols(symbols);
		decimalFormat.setMinimumFractionDigits(2);
		decimalFormat.setMaximumFractionDigits(2);

		dataBinder.registerCustomEditor(Double.class, "price",
		     new CustomNumberEditor(Double.class, decimalFormat, true));
	}	
	
	
	public Product getDecodedInstance(String encodedString)
			throws UnsupportedEncodingException, ParseException {
		
		
		Product product=new Product();		
		
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
		      
		        
		        if(name.equals("manufacturer_id")){
		        	
		        	
		        	product.setManufacturerId(Integer.parseInt(value));
		        }else if(name.equals("name")){
		        	product.setName(value);
		        }else if(name.equals("price")){
		        	product.setPrice(Double.parseDouble(value));
		        }else if(name.equals("description")){
		        	product.setDescription(value);
		        }else if(name.equals("category_id")){
		        	product.setCategoryId(value);
		        }
		       		        	        
		    }		
		} catch (UnsupportedEncodingException e) {
		}
		
		 
		return product;		
	}	
	
	public ProductSearch getDecodedProductSearchInstance(String encodedString)
			throws UnsupportedEncodingException, ParseException {
		
		
		ProductSearch productSearch=new ProductSearch();
		
		
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
		      
		        
		        if(name.equals("manufacturer_id")){
		        	
		        	System.out.println("manufacturer");
		        	productSearch.setManufacturerId(Integer.parseInt(value));
		        }else if(name.equals("name")){
		        	System.out.println("name");
		        	productSearch.setName(value);
		        }else if(name.equals("ascending")){
		        	System.out.println("ascending");
		        	productSearch.setAscending(Boolean.parseBoolean(value));
		        }else if(name.equals("category_id")){
		        	System.out.println("category");
		        	productSearch.setCategoryId(value);
		        }
		       		        	        
		    }		
		} catch (UnsupportedEncodingException e) {
		}
		
		 
		return productSearch;		
	}	
	
	
	@PreAuthorize(value="hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/",params = "delete_category", method = RequestMethod.POST)
	public String deleteCategory(Product product,HttpServletRequest request) throws UnsupportedEncodingException {
						
		
		String _params="name="+URLEncoder.encode(product.getName(),"utf8")+
				"&manufacturer_id="+URLEncoder.encode(String.valueOf(product.getManufacturerId()),"utf8")+
				"&price="+URLEncoder.encode(String.valueOf(product.getPrice()),"utf8")+"" +
				"&description=" +
						""+URLEncoder.encode(product.getDescription(),"utf8")+
						"&category_id="+URLEncoder.encode(product.getCategoryId(),"utf8");
		
	
		_params=URLEncoder.encode(_params,"utf8");		
		
		return "redirect:/product/edit/"+product.getProductId()+"?category_id=&_params="+_params;		
	}	
	
	@PreAuthorize(value="hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/new",params = "delete_category", method = RequestMethod.POST)
	public String new_deleteCategory(Product product,HttpServletRequest request) throws UnsupportedEncodingException {
						
		
		String _params="name="+URLEncoder.encode(product.getName(),"utf8")+
				"&manufacturer_id="+URLEncoder.encode(String.valueOf(product.getManufacturerId()),"utf8")+
				"&price="+URLEncoder.encode(String.valueOf(product.getPrice()),"utf8")+"" +
				"&description=" +
						""+URLEncoder.encode(product.getDescription(),"utf8")+
						"&category_id="+URLEncoder.encode(product.getCategoryId(),"utf8");
		
	
		_params=URLEncoder.encode(_params,"utf8");		
		
		return "redirect:/product/new?category_id=&_params="+_params;		
	}
	
	@PreAuthorize(value="hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/new",params = "choose_manufacturer", method = RequestMethod.POST)
	public String chooseManufacturer(Product product,BindingResult bindingResult,HttpServletRequest request) throws UnsupportedEncodingException {
					
		String _params="name="+URLEncoder.encode(product.getName(),"utf8")+
				"&manufacturer_id="+URLEncoder.encode(String.valueOf(product.getManufacturerId()),"utf8")+
				"&price="+URLEncoder.encode(String.valueOf(product.getPrice()),"utf8")+"" +
				"&description=" +
						""+URLEncoder.encode(product.getDescription(),"utf8")+
						"&category_id="+URLEncoder.encode(product.getCategoryId(),"utf8");
		
	
		_params=URLEncoder.encode(_params,"utf8");		
		
		return "redirect:/choose/manufacturer?action=create&_params="+_params;		
	}	
	
	@PreAuthorize(value="hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/new",params = "choose_category", method = RequestMethod.POST)
	public String chooseCategory(Product product,BindingResult bindingResult,HttpServletRequest request) throws UnsupportedEncodingException {
					
		String _params="name="+URLEncoder.encode(product.getName(),"utf8")+
				"&manufacturer_id="+URLEncoder.encode(String.valueOf(product.getManufacturerId()),"utf8")+
				"&price="+URLEncoder.encode(String.valueOf(product.getPrice()),"utf8")+"" +
				"&description=" +
						""+URLEncoder.encode(product.getDescription(),"utf8")+"&category_id="+URLEncoder.encode(product.getCategoryId(),"utf8");
		
	
		_params=URLEncoder.encode(_params,"utf8");		
		
		return "redirect:/choose/category?source=product&product_id="+String.valueOf(product.getProductId())+"&action=create&_params="+_params;		
	}
	
	
	
	@PreAuthorize(value="hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/",params = "choose_manufacturer", method = RequestMethod.POST)
	public String vChooseManufacturer(Product product,BindingResult bindingResult,HttpServletRequest request) throws UnsupportedEncodingException {
					
		String _params="name="+URLEncoder.encode(product.getName(),"utf8")+
				"&manufacturer_id="+URLEncoder.encode(String.valueOf(product.getManufacturerId()),"utf8")+
				"&price="+URLEncoder.encode(String.valueOf(product.getPrice()),"utf8")+"" +
				"&description=" +
						""+URLEncoder.encode(product.getDescription(),"utf8")+"&category_id="+URLEncoder.encode(product.getCategoryId(),"utf8");
		
	
		_params=URLEncoder.encode(_params,"utf8");		
		
		return "redirect:/choose/manufacturer?product_id="+String.valueOf(product.getProductId())+"&action=view&_params="+_params;		
	}
	
	@PreAuthorize(value="hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/",params = "choose_category", method = RequestMethod.POST)
	public String vChooseCategory(Product product,BindingResult bindingResult,HttpServletRequest request) throws UnsupportedEncodingException {
					
		String _params="name="+URLEncoder.encode(product.getName(),"utf8")+
				"&manufacturer_id="+URLEncoder.encode(String.valueOf(product.getManufacturerId()),"utf8")+
				"&price="+URLEncoder.encode(String.valueOf(product.getPrice()),"utf8")+"" +
				"&description=" +
						""+URLEncoder.encode(product.getDescription(),"utf8")+"&category_id="+URLEncoder.encode(product.getCategoryId(),"utf8");
		
	
		_params=URLEncoder.encode(_params,"utf8");		
		
		return "redirect:/choose/category?source=product&product_id="+String.valueOf(product.getProductId())+"&action=view&_params="+_params;		
	}
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String displayProducts(@RequestParam(value="manufacturer_id",required=false) Integer manufacturerId,
			@RequestParam(value="category_id",required=false) String categoryId,
			@RequestParam(value="ascending",required=false) Boolean ascending,
			@RequestParam(value="name",required=false) String name,
			@RequestParam(value="search",required=true) boolean search,
			@RequestParam(value="_params",required=false) String _params,
			@RequestParam(value="deleted",required=false) String deleted,
			@RequestParam(value="choose_category",required=false) String chooseCategory,
			@RequestParam(value="choose_manufacturer",required=false) String chooseManufacturer,
			@RequestParam(value="page",required=false) Integer page,
			HttpServletRequest request,Model model) throws UnsupportedEncodingException, ParseException{
	
		
		if(_params!=null){
			
			ProductSearch productSearch=getDecodedProductSearchInstance(_params);
			
			if(manufacturerId==null)
			manufacturerId=productSearch.getManufacturerId();
			
			if(categoryId==null)
			categoryId=productSearch.getCategoryId();
			
			ascending=productSearch.isAscending();
			name=productSearch.getName();			
			
		}
		
		
		if(ascending==null){
			ascending=true;
		}
			
			if(chooseManufacturer!=null){
				
				String params="";
				
				if(name!=null){
					params+="&name="+URLEncoder.encode(name,"utf8");
				}
				
				if(manufacturerId!=null){
					params+="&manufacturer_id="+URLEncoder.encode(String.valueOf(manufacturerId),"utf8");
				}
				
				if(categoryId!=null){
					params+="&category_id="+URLEncoder.encode(categoryId,"utf8");
				}
				
				
				
				params+="&ascending="+URLEncoder.encode(String.valueOf(ascending),"utf8");
				
			
				params=URLEncoder.encode(params,"utf8");		
				
				return "redirect:/choose/manufacturer?&action=list&_params="+params;				
			}
			
			if(chooseCategory!=null){
				
				String params="";
				
				if(name!=null){
					params+="&name="+URLEncoder.encode(name,"utf8");
				}
				
				if(manufacturerId!=null){
					params+="&manufacturer_id="+URLEncoder.encode(String.valueOf(manufacturerId),"utf8");
				}
				
				if(categoryId!=null){
					params+="&category_id="+URLEncoder.encode(categoryId,"utf8");
				}
				
				
				
				params+="&ascending="+URLEncoder.encode(String.valueOf(ascending),"utf8");
				
			
				params=URLEncoder.encode(params,"utf8");
				
				return "redirect:/choose/category?source=product&action=list&_params="+params;
			}
			
			
			ManufacturerPK manufacturerPK=null;
			
			if(manufacturerId!=null){
				manufacturerPK=new ManufacturerPK();
				manufacturerPK.setManufacturerId(manufacturerId);
				
				if(!shopManagement.isManufacturer(manufacturerPK)){
					manufacturerPK=null;				
				}else{
					model.addAttribute("manufacturerName",shopManagement.getManufacturer(manufacturerPK).getName());
					model.addAttribute("manufacturerId",manufacturerId);
				}
			}
			
			
			if(name!=null){
				model.addAttribute("name", name);
			}
			
			CategoryPK categoryPK=null;
			Category category=null;
			
			if(categoryId!=null){
				categoryPK=new CategoryPK();
				categoryPK.setCategoryId(categoryId);			
				
				if(shopManagement.isCategory(categoryPK)){
					category=shopAccess.getCategory(categoryPK);
					model.addAttribute("categoryId",categoryId);
					model.addAttribute("supCategories",shopAccess.getSupCategoryNames(categoryId));				
				}
			}
			
			
			model.addAttribute("ascending",ascending);
			
			
			if(search){
				pagination.preparePage(model, shopAccess.getProducts(category, manufacturerPK, ascending,name), page,"products");				
			}
			
		return "productList";
	}
	
	
	@RequestMapping(value = "/{productId}", method = RequestMethod.GET)
	public String displayProductDetails(@PathVariable int productId,@RequestParam(value="feature_deleted",required=false) String featureDeleted,
			@RequestParam(value="opinion_created",required=false) String opinionCreated,
			HttpServletRequest request,Model model,Authentication authentication){
			
		ProductPK productPK=new ProductPK();
		productPK.setProductId(productId);
		
		 
		if(shopAccess.isProduct(productPK)){
			Product product=shopAccess.getProduct(productPK);
			model.addAttribute("product",product);
			
			ManufacturerPK manufacturerPK=new ManufacturerPK();
			manufacturerPK.setManufacturerId(product.getManufacturerId());
			
			model.addAttribute("manufacturerName",shopManagement.getManufacturer(manufacturerPK).getName());
			
			model.addAttribute("images",shopAccess.getImages(productPK));
			model.addAttribute("features",shopAccess.getFeaturesByProduct(productPK));
			model.addAttribute("opinions",shopping.getOpinionsByProduct(productPK));
			
			
			if(authentication!=null&&authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CLIENT"))){
				UsrPK usrPK=new UsrPK();
				usrPK.setLogin(authentication.getName());
			
				Opinion opinion=new Opinion();
				opinion.setProductId(productId);
				
				
				if(shopping.isProductBoughtByClient(productPK, usrPK)){
					model.addAttribute("opinion",opinion);
				}	
			
			}
			
			
			if(product.getCategoryId()!=null){
				model.addAttribute("supCategories",shopAccess.getSupCategoryNames(product.getCategoryId()));				
			}
			
		}else{
			model.addAttribute("isProductIncorrect",true);
		}
		
		return "productView";			
	}
	
	
	@PreAuthorize(value="hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String displayNewProductForm(
			@RequestParam(value="manufacturer_id",required=false) Integer manufacturerId,
			@RequestParam(value="category_id",required=false) String categoryId,
			@RequestParam(value="_params",required=false) String _params,
			
			HttpServletRequest request,Model model) throws UnsupportedEncodingException, ParseException {
				
		
		Product product=new Product();
		
		
		if(_params!=null){
		
			product=getDecodedInstance(_params);
			
			ManufacturerPK manufacturerPK;
			
			if(((Integer) product.getManufacturerId()!=null)&&(manufacturerId==null)){
				
				manufacturerPK=new ManufacturerPK();
				manufacturerPK.setManufacturerId(product.getManufacturerId());
				
				if(shopManagement.isManufacturer(manufacturerPK)){										
					model.addAttribute("manufacturerName",shopManagement.getManufacturer(manufacturerPK).getName());
				}else{
					product.setManufacturerId(0);
				}
				
			}else if(manufacturerId!=null){ 
				
				manufacturerPK=new ManufacturerPK();
				manufacturerPK.setManufacturerId(manufacturerId);
				
				if(shopManagement.isManufacturer(manufacturerPK)){
					product.setManufacturerId(manufacturerId);
					
					model.addAttribute("manufacturerName",shopManagement.getManufacturer(manufacturerPK).getName());
				}else{
					product.setManufacturerId(0);
				}
			}
			
			if(categoryId!=null){
				
				CategoryPK categoryPK=new CategoryPK();
				categoryPK.setCategoryId(categoryId);
				
				if(categoryId.isEmpty()){
					product.setCategoryId(null);
				}else if(shopManagement.isCategory(categoryPK)){
					product.setCategoryId(categoryId);					
				}
			}			
			
			
		}
		
		
		model.addAttribute("product",product);
		
		return "newProductForm";
	}
	
	
	
	@PreAuthorize(value="hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/edit/{productId}", method = RequestMethod.GET)
	public String editProductDetails(@PathVariable int productId,
			@RequestParam(value="updated",required=false) String updated,
			@RequestParam(value="manufacturer_id",required=false) Integer manufacturerId,
			@RequestParam(value="category_id",required=false) String categoryId,
			@RequestParam(value="_params",required=false) String _params,
			
			HttpServletRequest request,Model model) throws UnsupportedEncodingException, ParseException {
			
		
		Product product = new Product();
		
		
		ProductPK productPK=new ProductPK();
		productPK.setProductId(productId);
		
		if(shopAccess.isProduct(productPK)){
			
			String originalCategoryId=shopAccess.getProduct(productPK).getCategoryId();
			
			if(originalCategoryId!=null){
				model.addAttribute("originalCategoryId", originalCategoryId);
				model.addAttribute("supCategories",shopAccess.getSupCategoryNames(originalCategoryId));
			}
			
			ManufacturerPK manufacturerPK;
			
			if(_params!=null){
				
				product=getDecodedInstance(_params);			
				
				
				if(((Integer) product.getManufacturerId()!=null)&&(manufacturerId==null)){
					
					manufacturerPK=new ManufacturerPK();
					manufacturerPK.setManufacturerId(product.getManufacturerId());
					
					if(shopManagement.isManufacturer(manufacturerPK)){										
						model.addAttribute("manufacturerName",shopManagement.getManufacturer(manufacturerPK).getName());
					}else{
						product.setManufacturerId(0);
					}
					
				}else if(manufacturerId!=null){
					
					manufacturerPK=new ManufacturerPK();
					manufacturerPK.setManufacturerId(manufacturerId);
					
					if(shopManagement.isManufacturer(manufacturerPK)){
						product.setManufacturerId(manufacturerId);
						
						model.addAttribute("manufacturerName",shopManagement.getManufacturer(manufacturerPK).getName());
					}else{
						product.setManufacturerId(0);
					}
				}
				
				
				
				if(categoryId!=null){
					
					CategoryPK categoryPK=new CategoryPK();
					categoryPK.setCategoryId(categoryId);
					
					if(categoryId.isEmpty()){
						product.setCategoryId(null);
					}else if(shopManagement.isCategory(categoryPK)){
						product.setCategoryId(categoryId);					
					}
				}	
					
			}else{
				product=shopAccess.getProduct(productPK);
				
				manufacturerPK=new ManufacturerPK();
				manufacturerPK.setManufacturerId(product.getManufacturerId());
				
				if(shopManagement.isManufacturer(manufacturerPK)){
					product.setManufacturerId(product.getManufacturerId());
					
					model.addAttribute("manufacturerName",shopManagement.getManufacturer(manufacturerPK).getName());
				}
			}
			
			
			product.setProductId(productId);
			
			model.addAttribute("isDependent",shopManagement.hasProductDependentObjects(productPK));
			
			model.addAttribute("product",product);	
		}else{
			model.addAttribute("isProductIncorrect",true);
		}
			
		
		return "productEdit";
	}
	
	

	@PreAuthorize(value="hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/new",params = "create",method = RequestMethod.POST)
	public String createProduct(@Valid Product product,BindingResult bindingResult,Model model) {
			
		productValidator.validateBeforeCreating(product, bindingResult);
		
		if(bindingResult.hasErrors()){
			
			if(((Integer) product.getManufacturerId())!=null){
				
				ManufacturerPK manufacturerPK=new ManufacturerPK();
				manufacturerPK.setManufacturerId(product.getManufacturerId());
				
				if(shopManagement.isManufacturer(manufacturerPK)){
					model.addAttribute("manufacturerName",shopManagement.getManufacturer(manufacturerPK).getName());
				}
			}
			
			return "newProductForm";
		}else{			
			shopManagement.addProduct(product);
			return "redirect:/product/"+product.getProductId()+"?created";
		}		
	}
	
	
	@PreAuthorize(value="hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/",params = "update",method = RequestMethod.POST)
	public String saveProduct(@Valid Product product,BindingResult bindingResult,Model model) {
			
		productValidator.validateBeforeUpdate(product, bindingResult);
		
		if(bindingResult.hasErrors()){	
			
			if(((Integer) product.getManufacturerId())!=null){
				
				ManufacturerPK manufacturerPK=new ManufacturerPK();
				manufacturerPK.setManufacturerId(product.getManufacturerId());
				
				if(shopManagement.isManufacturer(manufacturerPK)){
					model.addAttribute("manufacturerName",shopManagement.getManufacturer(manufacturerPK).getName());
				}
			}			
			
			ProductPK productPK=new ProductPK();
			productPK.setProductId(product.getProductId());
			
			model.addAttribute("isDependent",shopManagement.hasProductDependentObjects(productPK));
			
			return "productEdit";
		}else{			
			shopManagement.saveProduct(product);
			
			return "redirect:/product/edit/"+product.getProductId()+"?updated";
		}		
	}
	
	
	@PreAuthorize(value="hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/",params = "delete",method = RequestMethod.POST)
	public String deleteProduct(Product product,BindingResult bindingResult,Model model) {
							
			ProductPK productPK=new ProductPK();
			productPK.setProductId(product.getProductId());
			
			shopManagement.deleteProduct(productPK);

			return "redirect:/product?search=false&deleted";				
	}	
	
	
	
}
