package hk.polyu.webservice.spark.DBAP.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// Root Configuration
@Configuration
@ComponentScan(basePackages = "hk.polyu.webservice.spark.DBAP.controller")
public class AppConfig {
	
	 //Define Beans here
	 /*
	  * Currently Deprecated with explicitly configuring Beans here
	  * Due to the comparison between Autowire and getBean(class)
	  * Reference: https://stackoverflow.com/questions/54869486/performance-of-spring-autowire-vs-getbeanclassname
	  * 
	  * Note: Keep it in case of further individual usage
	  * */
}