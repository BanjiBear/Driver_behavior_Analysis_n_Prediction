package hk.polyu.webservice.spark.DBAP.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
 
@Configuration
@ComponentScan(basePackages = "hk.polyu.webservice.spark.DBAP.config")
@EnableWebMvc
public class ServletConfig implements WebMvcConfigurer {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		if(!registry.hasMappingForPattern("/**")) {
			registry.addResourceHandler("/**")
					.addResourceLocations("classpath:/static/");
		}
	}
}