package com.company.eshop.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.eshop.domain.Product;
import com.company.eshop.domain.ProductInstance;
import com.company.eshop.domain.ProductInstancePK;
import com.company.eshop.domain.ProductPK;
import com.company.eshop.service.ShopAccess;
import com.company.eshop.service.ShopManagement;
import com.company.eshop.service.Shopping;
import com.company.eshop.util.Pagination;
import com.company.eshop.validation.ProductInstanceValidator;


@Controller
@RequestMapping(value="/product-instance")
@PreAuthorize(value="hasRole('ROLE_ADMIN')")
public class ProductInstanceController {

	@Autowired
	private ProductInstanceValidator productInstanceValidator;
	
	@Autowired
	@Qualifier("shopAccessImpl")
	private ShopAccess shopAccess;
	
	@Autowired
	private ShopManagement shopManagement;
	

	@Autowired
	private Shopping shopping;
	
	@Autowired
	private Pagination<ProductInstance> pagination;
	
	
	@RequestMapping(value = "/product/{productId}",method = RequestMethod.GET)
	public String displayProductInstances(@PathVariable int productId,@RequestParam(value="page",required=false) Integer page,HttpServletRequest request,Model model) {
		
			ProductPK productPK=new ProductPK();
			productPK.setProductId(productId);
			
			if(shopAccess.isProduct(productPK)){
				Product product=shopAccess.getProduct(productPK);
				
				model.addAttribute("product",product);
				
				pagination.preparePage(model,shopManagement.getProductInstances(productPK), page,"productInstances");
				
				if(product.getCategoryId()!=null){
					model.addAttribute("supCategories",shopAccess.getSupCategoryNames(product.getCategoryId()));				
				}
				
			}else{
				model.addAttribute("isProductIncorrect",true);
			}
			
			return "productInstanceList";				
	}	
	
	
	@RequestMapping(value = "/{productInstanceId}", method = RequestMethod.GET)
	public String displayProductInstanceDetails(@PathVariable int productInstanceId,@RequestParam(value="created",required=false) String created,
			HttpServletRequest request,Model model) {
			
		
		ProductInstancePK productInstancePK=new ProductInstancePK();
		productInstancePK.setProductInstanceId(productInstanceId);
		
		if(shopManagement.isProductInstance(productInstancePK)){
			ProductInstance productInstance=shopManagement.getProductInstance(productInstancePK);
			
			ProductPK productPK=new ProductPK();
			productPK.setProductId(productInstance.getProductId());
			
			Product product=shopAccess.getProduct(productPK);
			
			model.addAttribute("product",product);
			
			model.addAttribute("isDependent",shopManagement.hasProductInstanceDependentObjects(productInstancePK));
			model.addAttribute("productInstance",productInstance);
			
			model.addAttribute("orders", shopManagement.getOrdersByProductInstance(productInstancePK));
			

			if(product.getCategoryId()!=null){
				model.addAttribute("supCategories",shopAccess.getSupCategoryNames(product.getCategoryId()));				
			}
			
		}else{
			model.addAttribute("isProductInstanceIncorrect",true);
		}		
		
		return "productInstanceView";
	}
		

	@RequestMapping(value = "/new/product/{productId}", method = RequestMethod.GET)
	public String displayNewProductInstanceForm(@PathVariable int productId,HttpServletRequest request,Model model) {
		
		ProductPK productPK=new ProductPK();
		productPK.setProductId(productId);
		
		if(shopAccess.isProduct(productPK)){
			
			Product product=shopAccess.getProduct(productPK);
			
			ProductInstance productInstance=new ProductInstance();
			productInstance.setProductId(productId);
			
			model.addAttribute("product",product);
			model.addAttribute("productInstance",productInstance);
			
  
			if(product.getCategoryId()!=null){
				model.addAttribute("supCategories",shopAccess.getSupCategoryNames(product.getCategoryId()));				
			}
			
		}else{
			model.addAttribute("isProductIncorrect",true);
		}
		
		return "newProductInstanceForm";
	}
	

	@RequestMapping(value = "/new",params = "create",method = RequestMethod.POST)
	public String createProductInstance(ProductInstance productInstance,BindingResult bindingResult,Model model) {
			
		productInstanceValidator.validate(productInstance, bindingResult);
		
		if(bindingResult.hasErrors()){
			
			return "newProductInstanceForm";
		}else{			
			shopManagement.addProductInstance(productInstance);
			return "redirect:/product-instance/"+productInstance.getProductInstanceId()+"?created";
		}		
	}
	
	
	
	@RequestMapping(value = "/",params = "delete",method = RequestMethod.POST)
	public String deleteProductInstance(ProductInstance productInstance,BindingResult bindingResult,Model model) {
							
			ProductInstancePK productInstancePK=new ProductInstancePK();
			productInstancePK.setProductInstanceId(productInstance.getProductInstanceId());
			
			int productId=shopManagement.getProductInstance(productInstancePK).getProductId();
			
			shopManagement.deleteProductInstance(productInstancePK);
			
			return "redirect:/product-instance/product/"+productId+"?deleted";				
	}	
}
