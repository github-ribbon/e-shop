package com.company.eshop.init;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

public class ApplicationInitializer
//implements WebApplicationInitializer 
{
		 
//	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		/*
//		Creates the 'root' Spring application context
		AnnotationConfigWebApplicationContext rootContext=new AnnotationConfigWebApplicationContext();
		
		rootContext.register(Config.class);		
		
		
//		Manages the lifecycle of the root application context
		servletContext.addListener(new ContextLoaderListener(rootContext));
		servletContext.addListener(ServletContextListenerImpl.class); 
	     
	      
//		Registers and maps the dispatcher servlet
		ServletRegistration.Dynamic dispatcher=servletContext.addServlet("appServlet", new DispatcherServlet(rootContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
		
	      
	      
//	    Registers Spring security filter
		FilterRegistration.Dynamic springSecurityFilterChain=servletContext.addFilter("springSecurityFilterChain", DelegatingFilterProxy.class);
		springSecurityFilterChain.addMappingForUrlPatterns(null, true, "/*");
			
//	    Registers encoding filter
	    CharacterEncodingFilter characterEncodingFilter=new CharacterEncodingFilter();
	    characterEncodingFilter.setEncoding("UTF-8");
	    characterEncodingFilter.setForceEncoding(true);
	      
	    FilterRegistration.Dynamic encodingFilter=servletContext.addFilter("encodingFilter",characterEncodingFilter);
	    encodingFilter.addMappingForUrlPatterns(null,true,"/*");    
	 */     
	}
}
