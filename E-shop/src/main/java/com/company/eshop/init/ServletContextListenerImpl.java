package com.company.eshop.init;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.company.eshop.service.ShopAccess;



public class ServletContextListenerImpl implements ServletContextListener{
	
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		
		final WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		    
		((ShopAccess) springContext.getBean("shopAccessImpl")).reloadCategories();
	}

}
