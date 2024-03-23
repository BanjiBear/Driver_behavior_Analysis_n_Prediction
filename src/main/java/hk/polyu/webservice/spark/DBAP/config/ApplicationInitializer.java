package hk.polyu.webservice.spark.DBAP.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


public class ApplicationInitializer implements WebApplicationInitializer {
	
	// For if we wish to create/export a .WAR file for deployment on servers
	
	/*
	 * To-Do:
	 * When migrating to AWS with database, 
	 * add JPA configuration
	 * 
	 * */
	
	@Override
	public void onStartup(ServletContext container) throws ServletException{
		// Create the root Context
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(AppConfig.class);
		
		// Manage Lifecycle of the root Context (????)
		/*
		 * Difference between DispatcherServlet and ContextLoaderListener
		 * https://stackoverflow.com/questions/37253568/dispatcherservlet-and-contextloaderlistener-in-spring
		 * */
		container.addListener(new ContextLoaderListener(rootContext));
		
		// Create dispatcher context
		AnnotationConfigWebApplicationContext servletContext = new AnnotationConfigWebApplicationContext();
		servletContext.register(ServletConfig.class);
		
		// Register and map the dispatcher servlet
		ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", new DispatcherServlet(servletContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
	}
}