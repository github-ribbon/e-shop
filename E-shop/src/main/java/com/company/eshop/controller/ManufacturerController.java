package com.company.eshop.controller;

import javax.servlet.http.HttpServletRequest;
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

import com.company.eshop.domain.Manufacturer;
import com.company.eshop.domain.ManufacturerPK;
import com.company.eshop.service.ShopManagement;
import com.company.eshop.util.Pagination;


@Controller
@RequestMapping(value="/manufacturer")
@PreAuthorize(value="hasRole('ROLE_ADMIN')")
public class ManufacturerController {

	@Autowired
	private ShopManagement shopManagement;
	
	@Autowired
	private Pagination<Manufacturer> pagination;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
	}		
	

	@RequestMapping(method = RequestMethod.GET)
	public String displayManufacturers(@RequestParam(value="page",required=false) Integer page,
			@RequestParam(value="deleted",required=false) String deleted,HttpServletRequest request,Model model) {
						
			pagination.preparePage(model,shopManagement.getAllManufacturers(), page,"manufacturers");
					
			return "manufacturerList";				
	}

	@RequestMapping(value = "/{manufacturerId}", method = RequestMethod.GET)
	public String displayManufacturerDetails(@PathVariable int manufacturerId,@RequestParam(value="created",required=false) String added,
			@RequestParam(value="updated",required=false) String updated,HttpServletRequest request,Model model) {
			
		ManufacturerPK manufacturerPK=new ManufacturerPK();
		manufacturerPK.setManufacturerId(manufacturerId);
		
		if(shopManagement.isManufacturer(manufacturerPK)){
			Manufacturer manufacturer=shopManagement.getManufacturer(manufacturerPK);
			
			model.addAttribute("isDependent",shopManagement.hasManufacturerDependentObjects(manufacturerPK));
			model.addAttribute("manufacturer",manufacturer);
		}else{
			model.addAttribute("isManufacturerIncorrect",true);
		}		
		
		return "manufacturerView";
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String displayNewManufacturerForm(Model model) {
		
		model.addAttribute("manufacturer", new Manufacturer());
		
		return "newManufacturerForm";
	}

	@RequestMapping(value = "/new",params = "create",method = RequestMethod.POST)
	public String createManufacturer(@Valid Manufacturer manufacturer,BindingResult bindingResult,Model model) {
					
		if(bindingResult.hasErrors()){
			return "newManufacturerForm";
		}else{			
			shopManagement.addManufacturer(manufacturer);
			return "redirect:/manufacturer/"+manufacturer.getManufacturerId()+"?created";
		}		
	}
	
	@RequestMapping(value = "/",params = "update",method = RequestMethod.POST)
	public String saveManufacturer(@Valid Manufacturer manufacturer,BindingResult bindingResult,Model model) {
					
		if(bindingResult.hasErrors()){
			ManufacturerPK manufacturerPK=new ManufacturerPK();
			manufacturerPK.setManufacturerId(manufacturer.getManufacturerId());
			
			model.addAttribute("isDependent",shopManagement.hasManufacturerDependentObjects(manufacturerPK));
			
			return "manufacturerView";
		}else{			
			shopManagement.saveManufacturer(manufacturer);
			
			return "redirect:/manufacturer/"+manufacturer.getManufacturerId()+"?updated";
		}		
	}
	
	
	@RequestMapping(value = "/",params = "delete",method = RequestMethod.POST)
	public String deleteManufacturer(Manufacturer manufacturer,BindingResult bindingResult,Model model) {
				
			ManufacturerPK manufacturerPK=new ManufacturerPK();
			manufacturerPK.setManufacturerId(manufacturer.getManufacturerId());
			
			shopManagement.deleteManufacturer(manufacturerPK);
			
			return "redirect:/manufacturer?deleted";				
	}	
}
