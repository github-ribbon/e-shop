package com.company.eshop.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.eshop.domain.DbStatusPK;
import com.company.eshop.domain.DeliveryAddress;
import com.company.eshop.domain.DeliveryAddressPK;
import com.company.eshop.domain.Order;
import com.company.eshop.domain.OrderPK;
import com.company.eshop.domain.Product;
import com.company.eshop.domain.ProductInstance;
import com.company.eshop.domain.ProductPK;
import com.company.eshop.domain.UsrPK;
import com.company.eshop.service.ShopAccess;
import com.company.eshop.service.ShopManagement;
import com.company.eshop.service.Shopping;
import com.company.eshop.service.UserService;
import com.company.eshop.util.Pagination;

@Controller
public class OrderController {

	@Autowired
	private UserService userService;
	
	@Autowired
	@Qualifier("shopAccessImpl")
	private ShopAccess shopAccess;
	
	@Autowired
	private Shopping shopping;
	
	@Autowired
	private ShopManagement shopManagement;
	
	@Autowired
	private Pagination<Order> pagination;
	
	
	@PreAuthorize(value="hasRole('ROLE_CLIENT')")
	@RequestMapping(value = "/cart/add/product/{productId}", method = RequestMethod.GET)
	public String addProductToShoppingCart(@PathVariable int productId,HttpServletRequest request,Model model){
			
		ProductPK productPK=new ProductPK();
		productPK.setProductId(productId);
		
		shopping.addProductToShoppingCart(request.getSession(),productPK);
		
		return "redirect:/cart";	
	}
	
	
	
	
	@PreAuthorize(value="hasRole('ROLE_CLIENT')")
	@RequestMapping(value = "/cart/remove/product/{productId}", method = RequestMethod.GET)
	public String removeProductFromShoppingCart(@PathVariable int productId,HttpServletRequest request,Model model){
			
		ProductPK productPK=new ProductPK();
		productPK.setProductId(productId);		
		
		shopping.removeProductFromShoppingCart(request.getSession(),productPK);
		
		return "redirect:/cart";		
	}
	
	
	
	@PreAuthorize(value="hasRole('ROLE_CLIENT')")
	@RequestMapping(value = "/cart", method = RequestMethod.GET)
	public String displayShoppingCartContent(HttpServletRequest request,Model model){
					
		model.addAttribute("products",shopping.getShoppingCartAsProductSet(request.getSession()));	
		model.addAttribute("counterMap",shopping.getShoppingCartAsProductPKMap(request.getSession()));
		model.addAttribute("totalPrice",shopAccess.getTotalPrice(shopping.getShoppingCart(request.getSession())));
		
		return "shoppingCartView";		
	}
	
	@PreAuthorize(value="hasRole('ROLE_CLIENT')")
	@RequestMapping(value = "/cart/validate", method = RequestMethod.GET)
	public String validateShoppingCart(HttpServletRequest request,Model model){
					
		if(shopping.isShoppingCartValid(request.getSession())){
			return "redirect:/order/finalize";
		}else{			
			
			model.addAttribute("products",shopping.getShoppingCartAsProductSet(request.getSession()));	
			model.addAttribute("counterMap",shopping.getShoppingCartAsProductPKMap(request.getSession()));
			model.addAttribute("totalPrice",shopAccess.getTotalPrice(shopping.getShoppingCart(request.getSession())));
			model.addAttribute("isShoppingCartIncorrect", true);
			
			return "shoppingCartView";	
		}
	
	}
	
	
	@PreAuthorize(value="hasRole('ROLE_CLIENT')")
	@RequestMapping(value = "/order/finalize", method = RequestMethod.GET)
	public String finalizeOrder(@RequestParam(value="delivery_address_id",required=false) Integer deliveryAddressId,HttpServletRequest request,Model model,Principal principal){
			
		Order order=new Order();
		
		if(deliveryAddressId!=null){		
				
				DeliveryAddressPK deliveryAddressPK=new DeliveryAddressPK();
				deliveryAddressPK.setDeliveryAddressId(deliveryAddressId);
				
			if(shopping.isDeliveryAddress(deliveryAddressPK)){
				
				DeliveryAddress deliveryAddress=shopping.getDeliveryAddress(deliveryAddressPK);
				
				if(deliveryAddress.getLogin().equals(principal.getName())){
					order.setDeliveryAddressId(deliveryAddressId);
					model.addAttribute("deliveryAddress",deliveryAddress);
				}else{
					model.addAttribute("isDeliveryAddressIncorrect",true);
				}
			}else{
				model.addAttribute("isDeliveryAddressIncorrect",true);
			}
		}		
		
		model.addAttribute("order", order);
		return "orderFinalizing";		
	}
	
	
	
	@PreAuthorize(value="hasRole('ROLE_CLIENT')")
	@RequestMapping(value = "/order/new", params="create",method = RequestMethod.POST)
	public String placeOrder(Order order,HttpServletRequest request,Model model,Principal principal){
			
		DeliveryAddressPK deliveryAddressPK=new DeliveryAddressPK();
		deliveryAddressPK.setDeliveryAddressId(order.getDeliveryAddressId());
		
		if(shopping.isDeliveryAddress(deliveryAddressPK)&&(shopping.getDeliveryAddress(deliveryAddressPK).getLogin().equals(principal.getName()))){
			
			try{
				shopping.placeOrder(order, request.getSession());
				shopping.refreshShoppingCart(request.getSession(), new ArrayList<Product>());
			}catch(RuntimeException e){
				
//				An user does not receive any detailed response
				
				e.printStackTrace();				
				return "redirect:/cart";
			}
			
			return "redirect:/order/"+order.getOrderId()+"?own";
		}else{
			model.addAttribute("isDeliveryAddressIncorrect", true);
			return "orderFinalizing";	
		}
	}
	
	@PreAuthorize(value="hasAnyRole('ROLE_CLIENT','ROLE_ADMIN')")
	@RequestMapping(value = "/order/{orderId}", method = RequestMethod.GET)
	public String displayOrderDetails(@PathVariable int orderId,
			@RequestParam(value="edit",required=false) String edit,
			@RequestParam(value="own",required=false) String own,
			HttpServletRequest request,Model model,Authentication authentication){
				
		
		OrderPK orderPK=new OrderPK();
		orderPK.setOrderId(orderId);
		
		UsrPK usrPK=new UsrPK();
		usrPK.setLogin(authentication.getName());
		
		boolean isLogged=false;
		
		if((isLogged=shopping.isOrderValid(usrPK,orderPK))||				
				((shopAccess.isOrder(orderPK))
						&&(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))))){		
						
			boolean isRedirect=false;
			
			if((isLogged&&(own==null))&&(!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))))			
				isRedirect=true;			
			
			if(isRedirect)
				return "redirect:/order/"+String.valueOf(orderId)+"?own";
			
			Order order=shopAccess.getOrder(orderPK);
			model.addAttribute("order",order);
			
			DeliveryAddressPK deliveryAddressPK=new DeliveryAddressPK();
			deliveryAddressPK.setDeliveryAddressId(order.getDeliveryAddressId());
			
			DeliveryAddress deliveryAddress=shopping.getDeliveryAddress(deliveryAddressPK);
			model.addAttribute("deliveryAddress",deliveryAddress);
			
			List<ProductInstance> productInstances=shopAccess.getProductInstancesByOrder(orderPK);
					
			model.addAttribute("productInstances",productInstances);
			model.addAttribute("totalPrice",shopAccess.getTotalPrice(shopAccess.getProducts(productInstances)));	
			
			
			usrPK.setLogin(deliveryAddress.getLogin());			
			model.addAttribute("client",userService.getClient(usrPK));			
			
		}else{
			model.addAttribute("isOrderIncorrect", true);
		}		
		
		model.addAttribute("isLogged",isLogged);
		
		return "orderView";
	}
	
	@PreAuthorize(value="hasAnyRole('ROLE_CLIENT','ROLE_ADMIN')")
	@RequestMapping(value = "/order", params="cancel",method = RequestMethod.POST)
	public String cancelOrder(Order order,HttpServletRequest request,Model model,Authentication authentication){
			
		
		OrderPK orderPK=new OrderPK();
		orderPK.setOrderId(order.getOrderId());
		
		UsrPK usrPK=new UsrPK();
		usrPK.setLogin(authentication.getName());
		
		
		if(shopping.isOrderValid(usrPK,orderPK)||(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))){			
			shopAccess.cancelOrder(orderPK);			
		}	
		
		String own="";
		
		if(shopping.isOrderValid(usrPK,orderPK)){
			own="?own";
		}
		
		return "redirect:/order/"+order.getOrderId()+own;				
	}
	
	
	
	@PreAuthorize(value="hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/order", params="send",method = RequestMethod.POST)
	public String sendOrder(Order order,HttpServletRequest request,Model model){
					
		OrderPK orderPK=new OrderPK();
		orderPK.setOrderId(order.getOrderId());		
	
		shopManagement.sendOrder(orderPK);	
		
		return "redirect:/order/"+order.getOrderId();		
	}
	

	@PreAuthorize(value="hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/order", params="complete",method = RequestMethod.POST)
	public String completeOrder(Order order,HttpServletRequest request,Model model){
					
		OrderPK orderPK=new OrderPK();
		orderPK.setOrderId(order.getOrderId());		
	
		shopManagement.completeOrder(orderPK);	
		
		return "redirect:/order/"+order.getOrderId();		
	}
	
	
	@PreAuthorize(value="hasAnyRole('ROLE_CLIENT','ROLE_ADMIN')")
	@RequestMapping(value = "/order", method = RequestMethod.GET)
	public String displayOrdersByClientAndStatus(
			@RequestParam(value="page",required=false) Integer page,
			@RequestParam(value="login",required=false) String login,
			@RequestParam(value="status",required=false) String status,
			@RequestParam(value="own",required=false) String own,
			HttpServletRequest request,Model model,Authentication authentication) throws UnsupportedEncodingException{
			
//		the own parameter influences navigation in view
 
		
		if((status==null)||((!status.equals("C"))&&(!status.equals("U"))&&(!status.equals("F"))
				&&(!status.equals("D")))){
			status="";
		}	
		
		
		if((login!=null)&&(!login.isEmpty())){
			
			UsrPK usrPK=new UsrPK();
			usrPK.setLogin(login);
			
			
			boolean isLogged=false;
			
			if(((isLogged=authentication.getName().equals(login))||authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))&&(userService.isClient(usrPK))){
				
				boolean isRedirect=false;
				
				if((isLogged&&(own==null))&&(!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))))			
					isRedirect=true;			
				
				if(isRedirect)
					return "redirect:/order?login="+URLEncoder.encode(authentication.getName(),"utf8")+"&own";
				
				
				model.addAttribute("login",login);
				
				if(!status.isEmpty()){
					DbStatusPK dbStatusPK=new DbStatusPK();
					dbStatusPK.setDbStatusId(status);
					
					pagination.preparePage(model, shopAccess.getOrdersByClientAndStatus(usrPK,dbStatusPK), page,"orders");
					
				}else{					
					pagination.preparePage(model,shopAccess.getOrdersByClient(usrPK), page,"orders");	
				}
				
						
			}else{
				model.addAttribute("isClientIncorrect",true);
			}
			
			model.addAttribute("isLogged",isLogged);
		}else {
			
			if(!status.isEmpty()){
				DbStatusPK dbStatusPK=new DbStatusPK();
				dbStatusPK.setDbStatusId(status);
				
				pagination.preparePage(model,shopManagement.getOrdersByStatus(dbStatusPK), page,"orders");	
			}else{
				pagination.preparePage(model,shopManagement.getAllOrders(), page,"orders");	
			}
			
		}		
		
		return "orderList";		
	}	

}
