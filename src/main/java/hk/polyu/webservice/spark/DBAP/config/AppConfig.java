package hk.polyu.webservice.spark.DBAP.config;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Spring dependency
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//Spark dependency
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import static org.apache.spark.sql.functions.col;


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
	
	private List<Dataset<Row>> dataList = new ArrayList<Dataset<Row>>();
	private Dataset<Row> data;
	
	private static final List<String> columnName = Collections.unmodifiableList( new ArrayList<String>() {
		{
	        add("driverID");
	        add("carPlateNumber");
	        add("latitude");
	        add("longtitude");
	        add("speed");
	        add("direction");
	        add("siteName");
	        add("time");
	        add("isRapidlySpeedup");
	        add("isRapidlySlowdown");
	        add("isNeutralSlide");
	        add("isNeutralSlideFinished");
	        add("neutralSlideTime");
	        add("isOverspeed");
	        add("isOverspeedFinished");
	        add("overspeedTime");
	        add("isFatigueDriving");
	        add("isHthrottleStop");
	        add("isOilLeak");
	    }
	} );
	Integer NUMBER_OF_FEATURES = 19;
	
	@Bean
	public SparkSession sparkSession(){
		/*
		 * Comparison of using SparkSession vs SparkContext
		 * Reference: https://www.ksolves.com/blog/big-data/spark/sparksession-vs-sparkcontext-what-are-the-differences
		 * */
		SparkSession spark = SparkSession.builder()
								.master("local[4]")
								.appName(applicationName)
								.getOrCreate();
		return spark;
	}
	
	@Bean
	public List<Dataset<Row>> getData(){
		SparkSession sparkSession = this.sparkSession();
		File[] allDataFiles = new File(dataPath).listFiles();
		dataPreProcess(allDataFiles);
		for(int i = 0; i < allDataFiles.length; i++){
			Dataset<Row> dataset = sparkSession.read().csv(dataPath + allDataFiles[i].getName());
			this.dataList.add(this.updateColumnName(dataset));
		}	
		return this.dataList;
	}
	
	@Bean
	public Dataset<Row> unionData(){
		if(this.dataList.size() != 10) {
			this.getData();
		}
		
		this.data = this.dataList.get(0);
		for(int i = 1; i < this.dataList.size(); i++) {
			this.data = this.data.union(this.dataList.get(i));
		}
		return this.data;
	}
	
	private Dataset<Row> updateColumnName(Dataset<Row> dataset) {
		for(int i = 0; i < this.columnName.size(); i++) {
			dataset = dataset.withColumnRenamed("_c" + Integer.toString(i), AppConfig.columnName.get(i));
		}
		dataset = dataset.drop("_c" + Integer.toString(19));
		return dataset;
	}
	
	
	private void dataPreProcess(File[] allDataFiles) {
		//40440, 38931, 56715, 30801, 40494, 36082, 36856, 44499, 42787, 45845
		/*
		 * Reference:
		 * https://stackoverflow.com/questions/202148/replace-first-line-of-a-text-file-in-java#:~:text=First%20use%20BufferedReader%20's%20readLine,the%20copy%20line%20by%20line.
		 * https://stackoverflow.com/questions/20039980/java-replace-line-in-text-file
		 * */
		for(int i = 0; i < allDataFiles.length; i++){
			try {
				// input the file content to the StringBuffer "input"
				BufferedReader file = new BufferedReader(new FileReader(dataPath + allDataFiles[i].getName()));
				StringBuffer inputBuffer = new StringBuffer();
				String line;
				
				while ((line = file.readLine()) != null) {
					int count = 0;
					for (int j = 0; j < line.length(); j++) {
					    if (line.charAt(j) == ',') {
					        count++;
					    }
					}
					if(count < NUMBER_OF_FEATURES - 1) {
						for(int j = 0; j < (NUMBER_OF_FEATURES - 1 - count); j++) {
							line = line + ',';
						}
					}
					inputBuffer.append(line);
					inputBuffer.append('\n');
				}
				file.close();
				
				String inputStr = inputBuffer.toString();				
				
				// write the new string with the replaced line OVER the same file
				FileOutputStream fileOut = new FileOutputStream(dataPath + allDataFiles[i].getName());
				fileOut.write(inputStr.getBytes());
				fileOut.close();
				
				file = null;
				inputBuffer = null;
				line = null;
				inputStr = null;
				fileOut = null;
				System.gc();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}






