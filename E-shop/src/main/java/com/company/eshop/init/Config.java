package com.company.eshop.init;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.company.eshop.domain.Admin;
import com.company.eshop.domain.Category;
import com.company.eshop.domain.CategoryPK;
import com.company.eshop.domain.Client;
import com.company.eshop.domain.DeliveryAddress;
import com.company.eshop.domain.DeliveryAddressPK;
import com.company.eshop.domain.Feature;
import com.company.eshop.domain.FeaturePK;
import com.company.eshop.domain.FeatureType;
import com.company.eshop.domain.FeatureTypePK;
import com.company.eshop.domain.Image;
import com.company.eshop.domain.ImagePK;
import com.company.eshop.domain.Manufacturer;
import com.company.eshop.domain.ManufacturerPK;
import com.company.eshop.domain.Opinion;
import com.company.eshop.domain.OpinionPK;
import com.company.eshop.domain.Order;
import com.company.eshop.domain.OrderPK;
import com.company.eshop.domain.OrderedItem;
import com.company.eshop.domain.OrderedItemPK;
import com.company.eshop.domain.Product;
import com.company.eshop.domain.ProductInstance;
import com.company.eshop.domain.ProductInstancePK;
import com.company.eshop.domain.ProductPK;
import com.company.eshop.domain.Usr;
import com.company.eshop.domain.UsrPK;
import com.company.eshop.repository.GenericDAOImpl;



@Configuration
//@EnableWebMvc
//@ComponentScan(basePackages = { "com.company.eshop" })
//@ImportResource({"classpath:servlet-context.xml"}) 
//@EnableTransactionManagement
public class Config 

//extends WebMvcConfigurerAdapter
{

	/*
	@Autowired
	private Environment env;
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
		PropertySourcesPlaceholderConfigurer propertySources = new PropertySourcesPlaceholderConfigurer();
		Resource[] resources = new ClassPathResource[] { 
				new ClassPathResource("database.properties")};
		propertySources.setLocations(resources);
		
		return propertySources;
	}
	
	 */
	
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver(){
		InternalResourceViewResolver internalResourceViewResolver=new InternalResourceViewResolver();
		internalResourceViewResolver.setPrefix("/WEB-INF/views/");
		internalResourceViewResolver.setSuffix(".jsp");
		
		return internalResourceViewResolver;
	}
	
	@Bean
	public CommonsMultipartResolver multipartResolver(){
	
		CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(500000);
		
		return multipartResolver;
	}
	
//	CRUDs
	
	@Bean
	public GenericDAOImpl<Usr,UsrPK> usrCRUD(){
		
		GenericDAOImpl<Usr,UsrPK> dao=new GenericDAOImpl<Usr,UsrPK>(Usr.class);
		
		return dao;
	}
	
	@Bean
	public GenericDAOImpl<Admin,UsrPK> adminCRUD(){
		
		GenericDAOImpl<Admin,UsrPK> dao=new GenericDAOImpl<Admin,UsrPK>(Admin.class);
		
		return dao;
	}

	@Bean
	public GenericDAOImpl<Client,UsrPK> clientCRUD(){
		
		GenericDAOImpl<Client,UsrPK> dao=new GenericDAOImpl<Client,UsrPK>(Client.class);
		
		return dao;
	}
	
	@Bean
	public GenericDAOImpl<Manufacturer,ManufacturerPK> manufacturerCRUD(){
		
		GenericDAOImpl<Manufacturer,ManufacturerPK> dao=new GenericDAOImpl<Manufacturer,ManufacturerPK>(Manufacturer.class);
		
		return dao;
	}
	
	@Bean
	public GenericDAOImpl<FeatureType,FeatureTypePK> featureTypeCRUD(){
		
		GenericDAOImpl<FeatureType,FeatureTypePK> dao=new GenericDAOImpl<FeatureType,FeatureTypePK>(FeatureType.class);
		
		return dao;
	}
	
	@Bean
	public GenericDAOImpl<Category,CategoryPK> categoryCRUD(){
		
		GenericDAOImpl<Category,CategoryPK> dao=new GenericDAOImpl<Category,CategoryPK>(Category.class);
		
		return dao;
	}
	
	@Bean
	public GenericDAOImpl<Order,OrderPK> orderCRUD(){
		
		GenericDAOImpl<Order,OrderPK> dao=new GenericDAOImpl<Order,OrderPK>(Order.class);
		
		return dao;
	}

	@Bean
	public GenericDAOImpl<OrderedItem,OrderedItemPK> orderedItemCRUD(){
		
		GenericDAOImpl<OrderedItem,OrderedItemPK> dao=new GenericDAOImpl<OrderedItem,OrderedItemPK>(OrderedItem.class);
		
		return dao;
	}
	
	@Bean
	public GenericDAOImpl<Feature,FeaturePK> featureCRUD(){
		
		GenericDAOImpl<Feature,FeaturePK> dao=new GenericDAOImpl<Feature,FeaturePK>(Feature.class);
		
		return dao;
	}
	
	@Bean
	public GenericDAOImpl<Product,ProductPK> productCRUD(){
		
		GenericDAOImpl<Product,ProductPK> dao=new GenericDAOImpl<Product,ProductPK>(Product.class);
		
		return dao;
	}
	
	@Bean
	public GenericDAOImpl<ProductInstance,ProductInstancePK> productInstanceCRUD(){
		
		GenericDAOImpl<ProductInstance,ProductInstancePK> dao=new GenericDAOImpl<ProductInstance,ProductInstancePK>(ProductInstance.class);
		
		return dao;
	}
	
	@Bean
	public GenericDAOImpl<DeliveryAddress,DeliveryAddressPK> deliveryAddressCRUD(){
		
		GenericDAOImpl<DeliveryAddress,DeliveryAddressPK> dao=new GenericDAOImpl<DeliveryAddress,DeliveryAddressPK>(DeliveryAddress.class);
		
		return dao;
	}
	 
	@Bean
	public GenericDAOImpl<Image,ImagePK> imageCRUD(){
		
		GenericDAOImpl<Image,ImagePK> dao=new GenericDAOImpl<Image,ImagePK>(Image.class);
		
		return dao;
	}
	
	@Bean
	public GenericDAOImpl<Opinion,OpinionPK> opinionCRUD(){
		
		GenericDAOImpl<Opinion,OpinionPK> dao=new GenericDAOImpl<Opinion, OpinionPK>(Opinion.class);
		
		return dao;
	}
	
	

	
	
	/*
	 
	@Bean
	public DriverManagerDataSource dataSource(){
		DriverManagerDataSource dataSource=new DriverManagerDataSource();

		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		dataSource.setUrl(env.getRequiredProperty("jdbc.url"));
		dataSource.setUsername(env.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(env.getRequiredProperty("jdbc.password"));
		
		return dataSource;
	}
	
	@Bean
	public AnnotationSessionFactoryBean sessionFactory(){
		
		AnnotationSessionFactoryBean sessionFactory=new AnnotationSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		
		String[] packages={"com.company.ehop.domain"};
		sessionFactory.setPackagesToScan(packages);
		
		Properties properties=new Properties();
		properties.setProperty("dialect",env.getRequiredProperty("persistence.dialect"));
		sessionFactory.setHibernateProperties(properties);
		
		return sessionFactory;
	}
	
	@Bean
	public HibernateJpaDialect jpaDialect(){
		HibernateJpaDialect jpaDialect=new HibernateJpaDialect();
		return jpaDialect;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslationPostProcessor(){
		return new PersistenceExceptionTranslationPostProcessor();
	}
	
	@Bean
	public PersistenceAnnotationBeanPostProcessor persistenceAnnotationBeanPostProcessor(){
		return new PersistenceAnnotationBeanPostProcessor();
	}
	
	@Bean
	public JpaTransactionManager transactionManager(){
		JpaTransactionManager transactionManager=new JpaTransactionManager();
		transactionManager.setJpaDialect(jpaDialect());
		transactionManager.setEntityManagerFactory((EntityManagerFactory) entityManagerFactory());
		
		return transactionManager;
	}
	
	@Bean 
	public HibernateJpaVendorAdapter jpaVendorAdapter(){
		HibernateJpaVendorAdapter jpaVendorAdapter=new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabasePlatform(env.getRequiredProperty("persistence.dialect"));		
		jpaVendorAdapter.setDatabase(Database.valueOf(env.getRequiredProperty("persistence.database")));
		jpaVendorAdapter.setShowSql(true);
		jpaVendorAdapter.setGenerateDdl(true);
		
		return jpaVendorAdapter;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
		LocalContainerEntityManagerFactoryBean entityManagerFactory=new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource());
		entityManagerFactory.setJpaDialect(jpaDialect());
		entityManagerFactory.setPackagesToScan("com.company.eshop");
		entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter());
		entityManagerFactory.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
		
		return entityManagerFactory;
	}
	
	*/
	
	
	
	
	@Bean
	public JavaMailSenderImpl javaMailSender() throws IOException{		
		
		JavaMailSenderImpl sender=new JavaMailSenderImpl();
		
		Properties properties=new Properties();		
		properties.load(getClass().getClassLoader().getResourceAsStream("mail.properties"));
		
		sender.setHost(properties.getProperty("sender.host"));
		sender.setPort(Integer.parseInt(properties.getProperty("sender.port")));
		sender.setUsername(properties.getProperty("sender.username"));
		sender.setPassword(properties.getProperty("sender.password"));
				
		Properties smtpProps = new Properties();		
		smtpProps.setProperty("mail.smtp.auth", "true");
		smtpProps.setProperty("mail.smtp.starttls.enable", "true");
		smtpProps.setProperty("mail.smtp.timeout", "8500");
		sender.setJavaMailProperties(smtpProps);
		
		return sender;		
	}


	/*
	@Bean
	public SessionLocaleResolver localeResolver(){
		
		SessionLocaleResolver sessionLocaleResolver=new SessionLocaleResolver();	
		sessionLocaleResolver.setDefaultLocale(new Locale("pl"));
		
		return sessionLocaleResolver;		
	}
	
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor(){

		LocaleChangeInterceptor localeChangeInterceptor=new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("language");
		
		return localeChangeInterceptor;
	}
	
	@Bean
	public ResourceBundleMessageSource messageSource(){		
		ResourceBundleMessageSource resourceBundleMessageSource=new ResourceBundleMessageSource();		
		resourceBundleMessageSource.setBasename("messages");
		
		return resourceBundleMessageSource;		
	}
	
	

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

*/

//	@Override
	/*
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	*/
	
	
}
