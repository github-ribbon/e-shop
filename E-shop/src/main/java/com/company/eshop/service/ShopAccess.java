package com.company.eshop.service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.company.eshop.domain.Category;
import com.company.eshop.domain.CategoryPK;
import com.company.eshop.domain.DbStatusPK;
import com.company.eshop.domain.DeliveryAddress;
import com.company.eshop.domain.Feature;
import com.company.eshop.domain.Image;
import com.company.eshop.domain.ManufacturerPK;
import com.company.eshop.domain.Order;
import com.company.eshop.domain.OrderPK;
import com.company.eshop.domain.Product;
import com.company.eshop.domain.ProductInstance;
import com.company.eshop.domain.ProductPK;
import com.company.eshop.domain.UsrPK;
import com.company.eshop.repository.ProductDAO;

public abstract class ShopAccess {


	@Autowired
	protected ProductDAO productDAO;
	
	@Autowired
	@Qualifier(value="javaMailSender")
	private JavaMailSenderImpl javaMailSender;
	
	protected String getOrderReport(Order order,DeliveryAddress deliveryAddress) {
		
		DecimalFormat decimalFormat = new DecimalFormat();
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator(',');
		symbols.setGroupingSeparator(' ');
		decimalFormat.setDecimalFormatSymbols(symbols);
		decimalFormat.setMinimumFractionDigits(2);
		decimalFormat.setMaximumFractionDigits(2);
		
		SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy H:mm:ss");
		
		
		double total=0;
		
		int orderId=order.getOrderId();
		
		List<Object[]> list=productDAO.findOrderedProducts(orderId);

		Iterator<Object[]> iterator=list.iterator();
	
		String report="Szczegóły zamówienia \n\n ID zamówienia: "+orderId+"\nzłożone: "+dateFormat.format(order.getCreated())
				+"\nzmodyfikowano: "+dateFormat.format(order.getModified())+" \n\nNazwa produktu | Cena | Sztuk | Koszt \n" +
						"---------------------------------------------------\n";
		
		while(iterator.hasNext()){	
			Object[] array=iterator.next();
			Product product=(Product) array[0];			
			long num=(Long) array[1];			
			
			double cost=num*product.getPrice();
			
			total+=cost;
			
			report+=product.getName()+" | "+decimalFormat.format(product.getPrice())+" zł | "+num+" | "+decimalFormat.format(cost)+" zł\n";
			
			
		}	
		
		
		report+="\n\n\nRazem do zapłaty: "+decimalFormat.format(total)+" zł\n\n\nAdres dostawy: \n\n";	
		
		
		
		
		
		report+=deliveryAddress.getStreet()+"\n"+deliveryAddress.getPostCode()+" " +
				" "+deliveryAddress.getCity();
		
		return report;
	}

	
	protected void sendMail(Order order,String email,String subject,String message) {
				

		SimpleMailMessage mailMessage = new SimpleMailMessage();
			
		mailMessage.setTo(email);
		mailMessage.setSubject(subject);			
		mailMessage.setText(message);
		
			
		try{
			javaMailSender.send(mailMessage);
		}catch(MailSendException e){			
			throw new MailSendException(e.getMessage(),e.getCause(),e.getFailedMessages());
		}
	}

	

	public abstract List<Category> getCategories();
	public abstract Category getCategory(CategoryPK categoryPK);
	public abstract boolean isSubCategory(Category category,CategoryPK categoryPK);
	
 
	public abstract List<Feature> getFeaturesByProduct(ProductPK productPK);
	
	public abstract List<Image> getImages(ProductPK productPK);
	public abstract Product getProduct(ProductPK productPK);
	public abstract List<ProductInstance> getProductInstancesByOrder(OrderPK orderPK); 
	public abstract boolean isProduct(ProductPK productPK);
	public abstract void reloadCategories();
	public abstract List<Product> getProducts(Category category,
			ManufacturerPK manufacturerPK, boolean ascending, String name);
	public abstract void cancelOrder(OrderPK orderPK);	
	public abstract Order getOrder(OrderPK orderPK);
	public abstract List<Order> getOrdersByClient(UsrPK usrPK); 
	public abstract List<Order> getOrdersByClientAndStatus(UsrPK usrPK,DbStatusPK dbStatusPK);
	public abstract List<Product> getProducts(List<ProductInstance> productInstances); 
	public abstract List<String> getSupCategoryNames(String categoryId);
	public abstract double getTotalPrice(List<Product> products);	
	
	public abstract boolean isOrder(OrderPK orderPK);
	
	public abstract List<Product> getBestsellers();
	
	
	
}
