package com.company.eshop.service;


import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.company.eshop.domain.DeliveryAddress;
import com.company.eshop.domain.DeliveryAddressPK;
import com.company.eshop.domain.Opinion;
import com.company.eshop.domain.Order;
import com.company.eshop.domain.OrderPK;
import com.company.eshop.domain.Product;
import com.company.eshop.domain.ProductPK;
import com.company.eshop.domain.UsrPK;



public interface Shopping {

	
	public abstract void addProductToShoppingCart(HttpSession session,ProductPK productPK);

	public abstract void removeProductFromShoppingCart(HttpSession session,ProductPK productPK);
	public abstract List<Product> getShoppingCart(HttpSession session);	
	
	public abstract boolean isShoppingCartValid(HttpSession session);	
	public abstract void placeOrder(Order order,HttpSession session);		
	public abstract void refreshShoppingCart(HttpSession session,List<Product> products);
	
	public abstract Map<Integer,Integer> getShoppingCartAsProductPKMap(HttpSession session);
	public abstract Set<Product> getShoppingCartAsProductSet(HttpSession session);
		
	
	public abstract boolean isOrderValid(UsrPK login,OrderPK orderPK);		
		
	
	public abstract boolean hasDeliveryAddressDependentObjects(DeliveryAddressPK deliveryAddressPK);
		
	public abstract DeliveryAddress getDeliveryAddress(DeliveryAddressPK deliveryAddressPK); 
	public abstract List<DeliveryAddress> getDeliveryAddresses(UsrPK usrPK);
	public abstract boolean isDeliveryAddress(DeliveryAddressPK deliveryAddressPK);	

		

	
	
	
	
	public abstract void addDeliveryAddress(DeliveryAddress deliveryAddress);
	public abstract void saveDeliveryAddress(DeliveryAddress deliveryAddress);
	public abstract void deleteDeliveryAddress(DeliveryAddressPK deliveryAddressPK);
	
	public abstract boolean isProductBoughtByClient(ProductPK productPK,UsrPK usrPK);
	
	public abstract List<Opinion> getOpinionsByProduct(ProductPK productPK);
	public abstract void addOpinion(Opinion opinion);
	
}
