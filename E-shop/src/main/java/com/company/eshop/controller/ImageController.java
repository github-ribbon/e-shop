package com.company.eshop.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.company.eshop.domain.Image;
import com.company.eshop.domain.ImagePK;
import com.company.eshop.domain.Product;
import com.company.eshop.domain.ProductPK;
import com.company.eshop.service.ShopAccess;
import com.company.eshop.service.ShopManagement;
import com.company.eshop.validation.ImageValidator;


@Controller
@RequestMapping(value="/image")
@PreAuthorize(value="hasRole('ROLE_ADMIN')")
public class ImageController {

	@Autowired
	private ImageValidator imageValidator;	
	
	@Autowired
	private ShopManagement shopManagement;
	
	@Autowired
	@Qualifier("shopAccessImpl")
	private ShopAccess shopAccess;
		

	@RequestMapping(value = "/product/{productId}",method = RequestMethod.GET)
	public String displayImages(@PathVariable int productId,
			@RequestParam(value="deleted",required=false) String deleted,
			@RequestParam(value="created",required=false) String created,
			@RequestParam(value="deleting_error",required=false) String deletingError,
			HttpServletRequest request,Model model) {
		
			ProductPK productPK=new ProductPK();
			productPK.setProductId(productId);
			
			if(shopAccess.isProduct(productPK)){
				
				Product product=shopAccess.getProduct(productPK);
				
				Image image=new Image();
				image.setProductId(product.getProductId());
				
				model.addAttribute("product",product);
				model.addAttribute("newImage",image);				
				model.addAttribute("images",shopAccess.getImages(productPK));
				
				if(product.getCategoryId()!=null){
					model.addAttribute("supCategories",shopAccess.getSupCategoryNames(product.getCategoryId()));
				}
			}else{
				model.addAttribute("isProductIncorrect",true);
			}			
			
			return "imageList";				
	} 
	
	
	
	@RequestMapping(value = "/new",params = "create",method = RequestMethod.POST)
	public String createImage(@RequestParam(value="binaryContent",required=true) MultipartFile binaryContent,@ModelAttribute(value="newImage") Image image,BindingResult bindingResult,Model model) {
						
		image.setBinaryContent(binaryContent);
		
		imageValidator.validate(image, bindingResult);		
		
		if(bindingResult.hasErrors()){
			
			ProductPK productPK=new ProductPK();
			productPK.setProductId(image.getProductId());
			
			if(shopAccess.isProduct(productPK)){				
				Product product=shopAccess.getProduct(productPK);
				
				model.addAttribute("product",product);			
				model.addAttribute("images",shopAccess.getImages(productPK));
			}else{				
				model.addAttribute("isProductIncorrect",true);				
			}
			
			return "imageList";
		}else{	
			
			try {
				shopManagement.addImage(image);
			} catch (IOException e) {
				
				bindingResult.rejectValue("binaryContent","error.upload");
				
				ProductPK productPK=new ProductPK();
				productPK.setProductId(image.getProductId());
				
				if(shopAccess.isProduct(productPK)){					
					Product product=shopAccess.getProduct(productPK);
					
					model.addAttribute("product",product);			
					model.addAttribute("images",shopAccess.getImages(productPK));
				}else{				
					model.addAttribute("isProductIncorrect",true);				
				}
				
				return "imageList";
			}
			
			return "redirect:/image/product/"+image.getProductId()+"?created";
		}		
	}
	
	

	@RequestMapping(value = "/",params = "delete",method = RequestMethod.POST)
	public String deleteImage(@RequestParam(value="image_id",required=true) int imageId,
			@RequestParam(value="product_id",required=true) int productId,HttpServletRequest request,
			Model model) {
							
			ImagePK imagePK=new ImagePK();
			imagePK.setImageId(imageId);
			
			try {
				shopManagement.deleteImage(imagePK);
			} catch (IOException e) {				
				return "redirect:/image/product/"+productId+"?deleting_error";	
			}
			
			return "redirect:/image/product/"+productId+"?deleted";				
	}	
}
