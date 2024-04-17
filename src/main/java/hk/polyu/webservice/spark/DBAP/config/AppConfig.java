package hk.polyu.webservice.spark.DBAP.config;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;

//Spring dependency
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//Spark dependency
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import hk.polyu.webservice.spark.DBAP.repository.DriverRepository;
import hk.polyu.webservice.spark.DBAP.util.DataUtil;


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
	
	private DriverRepository repo = new DriverRepository();
	private Dataset<Row> data;
	
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
	public Dataset<Row> driversData(){
		SparkSession sparkSession = this.sparkSession();
		List<Dataset<Row>> dataList = new ArrayList<Dataset<Row>>();
		File[] allDataFiles = new File(dataPath).listFiles();
		//dataPreProcess(allDataFiles);
		for(int i = 0; i < allDataFiles.length; i++){
			Dataset<Row> dataset = sparkSession.read().csv(dataPath + allDataFiles[i].getName());
			dataList.add(this.updateColumnName(dataset));
		}	
		
		this.data = dataList.get(0);
		for(int i = 1; i < dataList.size(); i++) {
			this.data = this.data.union(dataList.get(i));
		}
		
		this.driversStatsByEvent();
		
		return this.data;
	}
	
	@Bean
	public HashMap<String, JSONArray> driversStatsByEvent(){
		HashMap<String, JSONArray> stats = new HashMap<String, JSONArray>();
		
		stats.put("isOverspeed", this.repo.getCountQueryResult(this.data, "driverID", "isOverspeed"));
		stats.put("overspeedTime", this.repo.getSumQueryResult(this.data, "driverID", "overspeedTime"));
		stats.put("isFatigueDriving", this.repo.getCountQueryResult(this.data, "driverID", "isFatigueDriving"));
		stats.put("isNeutralSlide", this.repo.getCountQueryResult(this.data, "driverID", "isNeutralSlide"));
		stats.put("neutralSlideTime", this.repo.getSumQueryResult(this.data, "driverID", "neutralSlideTime"));
		stats.put("isHthrottleStop", this.repo.getCountQueryResult(this.data, "driverID", "isHthrottleStop"));
		stats.put("isOilLeak", this.repo.getCountQueryResult(this.data, "driverID", "isOilLeak"));
		
		return stats;
	}
	
	private Dataset<Row> updateColumnName(Dataset<Row> dataset) {
		for(int i = 0; i < DataUtil.NUMBER_OF_FEATURES; i++) {
			dataset = dataset.withColumnRenamed("_c" + Integer.toString(i), DataUtil.columnName.get(i));
		}
		dataset = dataset.drop("_c" + Integer.toString(19));
		
		//https://stackoverflow.com/questions/49826020/how-to-cast-all-columns-of-spark-dataset-to-string-using-java
		dataset = dataset.withColumn("isRapidlySpeedup", dataset.col("isRapidlySpeedup").cast("Integer"));
		dataset = dataset.withColumn("isRapidlySlowdown", dataset.col("isRapidlySlowdown").cast("Integer"));
		
		dataset = dataset.withColumn("isNeutralSlide", dataset.col("isNeutralSlide").cast("Integer"));
		dataset = dataset.withColumn("isNeutralSlideFinished", dataset.col("isNeutralSlideFinished").cast("Integer"));
		dataset = dataset.withColumn("neutralSlideTime", dataset.col("neutralSlideTime").cast("Integer"));
		
		dataset = dataset.withColumn("isOverspeed", dataset.col("isOverspeed").cast("Integer"));
		dataset = dataset.withColumn("isOverspeedFinished", dataset.col("isOverspeedFinished").cast("Integer"));
		dataset = dataset.withColumn("overspeedTime", dataset.col("overspeedTime").cast("Integer"));
		
		dataset = dataset.withColumn("isFatigueDriving", dataset.col("isFatigueDriving").cast("Integer"));
		dataset = dataset.withColumn("isHthrottleStop", dataset.col("isHthrottleStop").cast("Integer"));
		dataset = dataset.withColumn("isOilLeak", dataset.col("isOilLeak").cast("Integer"));
		
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
					if(count < DataUtil.NUMBER_OF_FEATURES - 1) {
						for(int j = 0; j < (DataUtil.NUMBER_OF_FEATURES - 1 - count); j++) {
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






