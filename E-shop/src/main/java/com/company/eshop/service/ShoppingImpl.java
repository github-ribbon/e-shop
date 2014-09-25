package com.company.eshop.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.RollbackException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.eshop.domain.DeliveryAddress;
import com.company.eshop.domain.DeliveryAddressPK;
import com.company.eshop.domain.Opinion;
import com.company.eshop.domain.Order;
import com.company.eshop.domain.OrderPK;
import com.company.eshop.domain.OrderedItem;
import com.company.eshop.domain.OrderedItemPK;
import com.company.eshop.domain.Product;
import com.company.eshop.domain.ProductInstance;
import com.company.eshop.domain.ProductPK;
import com.company.eshop.domain.UsrPK;
import com.company.eshop.repository.GenericDAOImpl;
import com.company.eshop.repository.OpinionDAO;
 

@Service
public class ShoppingImpl extends ShopAccessImpl implements Shopping {
	

	
	@Autowired
	@Qualifier(value="orderedItemCRUD")
	private GenericDAOImpl<OrderedItem,OrderedItemPK> orderedItemDAO;
	
	
	@Autowired
	private OpinionDAO opinionDAO;
	
	

	@Override
	@Transactional(readOnly=true)
	public DeliveryAddress getDeliveryAddress(
			DeliveryAddressPK deliveryAddressPK) {
		
		return deliveryAddressDAO.getCrud().read(deliveryAddressPK);
	}

	

	@Override
	@Transactional(readOnly=true)
	public boolean isDeliveryAddress(DeliveryAddressPK deliveryAddressPK) {
	
		if(deliveryAddressDAO.getCrud().read(deliveryAddressPK)!=null){
			return true;
		}else
			return false;
		
	}
	

	@Override
	@Transactional(readOnly=true)
	public List<DeliveryAddress> getDeliveryAddresses(UsrPK usrPK) {
		
		return deliveryAddressDAO.findDeliveryAddressesByClient(usrPK.getLogin());
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getShoppingCart(HttpSession session){
		
		return (List<Product>) session.getAttribute("cart");
	}
	
	@Override
	public void refreshShoppingCart(HttpSession session,List<Product> products){
		
		session.setAttribute("cart",products);
	}
	
	@Override
	@Transactional(readOnly=true)
	public void addProductToShoppingCart(HttpSession session, ProductPK productPK) {
		
		Product product=productDAO.getCrud().read(productPK);
		product.getImages().iterator();
		getShoppingCart(session).add(product);
	}

	

	@Override
	@Transactional(readOnly=true)
	public void removeProductFromShoppingCart(HttpSession session,ProductPK productPK) {
		
		Product product=null;
		
		List<Product> products=getShoppingCart(session);
	
		Iterator<Product> iterator=products.iterator();
		
		while(iterator.hasNext()){
			
			Product tempProduct=iterator.next();
			ProductPK temp=new ProductPK();
			temp.setProductId(tempProduct.getProductId());
			
			if(productPK.equals(temp)){
				product=tempProduct;
				break;
			}
		}
		
		if(product!=null){
			getShoppingCart(session).remove(product);
		}
	
	}

	

	@Override
	public Set<Product> getShoppingCartAsProductSet(HttpSession session){
		
		List<Product> productList=getShoppingCart(session);
		Set<Product> productSet=new HashSet<Product>();
		
		Iterator<Product> iterator=productList.iterator();
		
		while(iterator.hasNext()){
			Product product=iterator.next();
			
			Iterator<Product> setIt=productSet.iterator();
			
			boolean isFound=false;
			
			while(setIt.hasNext()){
				if(product.getProductId()==setIt.next().getProductId()){
					isFound=true;
					break;					
				}
			}
			
			
			if(!isFound){
				productSet.add(product);	
			}					
		}
	
		return productSet;
	}
	
	@Override
	public Map<Integer,Integer> getShoppingCartAsProductPKMap(HttpSession session){
		
		List<Product> products=getShoppingCart(session);
		
//		key = ProductPK , value = number of required instances		
		HashMap<Integer,Integer> counterMap=new HashMap<Integer,Integer>();
		
		Iterator<Product> iterator=products.iterator();
		
		while(iterator.hasNext()){
			Product product=iterator.next();
			
			if(counterMap.containsKey(product.getProductId())){
				counterMap.put(product.getProductId(), counterMap.get(product.getProductId())+1);
			}else{
				counterMap.put(product.getProductId(), 1);
			}
		}
		
		return counterMap;
	}
	
	@Override
	@Transactional(readOnly=true)
	public boolean isShoppingCartValid(HttpSession session) {
		
		boolean isValid=true;	
		
		Map<Integer,Integer> counterMap=getShoppingCartAsProductPKMap(session);	
		
		List<Product> products=new ArrayList<Product>();
		List<Product> temp=getShoppingCart(session);
		
		for (Map.Entry<Integer,Integer> entry: counterMap.entrySet()) {
			
			List<ProductInstance> instances=productInstanceDAO.findAvailableProductInstances(
					entry.getKey(),entry.getValue());
			int size=instances.size();
			
			if(size!=entry.getValue())
				isValid=false;
			
			
			Product product = null;
			
			for(Product p: temp){
				if(p.getProductId()==
						entry.getKey()){
					product=p;
					break;
				}
			}
			
			
			
			
			for(int i=1;i<=size;i++){				
				products.add(product);				
			}
					
		}	

		refreshShoppingCart(session, products);
		
		return isValid;
	}
	
	@Override
	@Transactional
	public void placeOrder(Order order,HttpSession session) {
	
		Map<Integer,Integer> counterMap=getShoppingCartAsProductPKMap(session);		
		
		for (Map.Entry<Integer,Integer> entry: counterMap.entrySet()) {
			
			List<ProductInstance> instances=productInstanceDAO.findAvailableProductInstances(
					entry.getKey(),entry.getValue());
			int size=instances.size();
			
			if(size==entry.getValue()){
				

				Date date=new Date();
				
				order.setCreated(date);
				order.setModified(date);
				order.setDbStatusId("C");
				
				orderDAO.getCrud().insert(order);
				
				int orderId=order.getOrderId();
				
				
				for(ProductInstance productInstance: instances){
					OrderedItem orderedItem=new OrderedItem();
					
					OrderedItemPK orderedItemPK=new OrderedItemPK();
					orderedItemPK.setOrderId(orderId);
					orderedItemPK.setProductInstanceId(productInstance.getProductInstanceId());
					
					orderedItem.setId(orderedItemPK);
					
					orderedItemDAO.insert(orderedItem);
				}
				
				
			}else{
				throw new RollbackException("No product instance provided");
			}
			
		}
		
		DeliveryAddressPK deliveryAddressPK=new DeliveryAddressPK();
		deliveryAddressPK.setDeliveryAddressId(order.getDeliveryAddressId());
		
		DeliveryAddress deliveryAddress=deliveryAddressDAO.getCrud().read(deliveryAddressPK);
		
		String message="Twoje zamówienie zostało przyjęte. Wkrótce powiadomimy Cię o wysyłce. \n\n"+getOrderReport(order,deliveryAddress);		
					
		sendMail(order,deliveryAddress.getClient().getUsr().getEmail(),"Nowe zamówienie "+order.getOrderId(),message);
		
}

	

	

	@Override
	@Transactional(readOnly=true)
	public boolean isOrderValid(UsrPK usrPK, OrderPK orderPK) {
		
		Order order=orderDAO.getCrud().read(orderPK);
		
		if((order!=null)&&(order.getDeliveryAddress().getLogin().equals(usrPK.getLogin()))){
			return true;
		}else
			return false;
	}

	

	



	

	@Override
	@Transactional
	public void addDeliveryAddress(DeliveryAddress deliveryAddress) {
		deliveryAddressDAO.getCrud().insert(deliveryAddress);
	}

	@Override
	@Transactional
	public void saveDeliveryAddress(DeliveryAddress deliveryAddress) {
		deliveryAddressDAO.getCrud().update(deliveryAddress);
	}

	@Override
	@Transactional
	public void deleteDeliveryAddress(DeliveryAddressPK deliveryAddressPK) {
		deliveryAddressDAO.getCrud().delete(deliveryAddressDAO.getCrud().read(deliveryAddressPK));
	}

	@Override
	@Transactional(readOnly=true)
	public boolean hasDeliveryAddressDependentObjects(
			DeliveryAddressPK deliveryAddressPK) {
		
		if(deliveryAddressDAO.getDependentObjectsNumber(deliveryAddressPK)>0)
			return true;
		else
			return false;
	}

	@Override
	@Transactional(readOnly=true)
	public boolean isProductBoughtByClient(ProductPK productPK, UsrPK usrPK) {
		
		if(productInstanceDAO.getBoughtProductInstancesNumber(productPK.getProductId(), usrPK.getLogin())>0)
			return true;
		else
			return false;
		
	}

	@Override
	@Transactional(readOnly=true)
	public List<Opinion> getOpinionsByProduct(ProductPK productPK) {
		return opinionDAO.findOpinionsByProduct(productPK.getProductId());
	}

	@Override
	@Transactional
	public void addOpinion(Opinion opinion) {
		opinion.setAdded(new Date());
		opinionDAO.getCrud().insert(opinion);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
