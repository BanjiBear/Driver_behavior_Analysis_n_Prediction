package hk.polyu.webservice.spark.DBAP.config;


import java.io.File;
import java.util.ArrayList;

//Spring dependency
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//Spark dependency
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;


// Root Configuration
@Configuration
@ComponentScan(basePackages = "hk.polyu.webservice.spark.DBAP.service")
public class AppConfig {
	
	//Define Beans here
	/*
	 * Currently Deprecated with explicitly configuring Beans here
	 * Due to the comparison between Autowire and getBean(class)
	 * Reference: https://stackoverflow.com/questions/54869486/performance-of-spring-autowire-vs-getbeanclassname
	 * 
	 * Note: Keep it in case of further individual usage
	 * */
	
	@Value("${spring.application.name}")
	private String applicationName;
	
	@Value("${spring.application.data.path}")
	private String dataPath;
	
	private ArrayList<Dataset<Row>> data = new ArrayList<Dataset<Row>>();
	
	@Bean
	public SparkSession sparkSession(){
		/*
		 * Comparison of using SparkSession vs SparkContext
		 * Reference: https://www.ksolves.com/blog/big-data/spark/sparksession-vs-sparkcontext-what-are-the-differences
		 * */
		SparkSession spark = SparkSession.builder()
								.master("local[1]")
								.appName(applicationName)
								.getOrCreate();
		return spark;
	}
	
	@Bean
	public ArrayList<Dataset<Row>> getData(){
		SparkSession sparkSession = this.sparkSession();
		File[] allDataFiles = new File(dataPath).listFiles();
		
		for(int i = 0; i < allDataFiles.length; i++){
			this.data.add(sparkSession.read().csv(dataPath + allDataFiles[i].getName()));
		}		
		return this.data;
	}
}






