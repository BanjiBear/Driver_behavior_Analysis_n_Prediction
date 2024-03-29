package hk.polyu.webservice.spark.DBAP.service;


import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;

//Spark dependencies
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

//Spring dependencies
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Package dependencies
import hk.polyu.webservice.spark.DBAP.status.ResponseFactory;
import hk.polyu.webservice.spark.DBAP.entity.DriverInfo;
import hk.polyu.webservice.spark.DBAP.entity.DriverRecord;



@Service
public class DriverBehaviorAnalysisServiceImpl implements DriverBehaviorAnalysisService{
	
	/*
	 * 
	 * To-Do:
	 * When migrating to AWS with database, 
	 * add Autowire with repository and add Transactional
	 * 
	 * */
	
	@Autowired
	private List<Dataset<Row>> getData;
	@Autowired
	private Dataset<Row> unionData;
	private ResponseFactory responseFactory = new ResponseFactory();
	
	@Override
	public ResponseFactory getAllDriverSummary(){
		/*
		 * 
		 * Current approach:
		 * 1. Convert Dataset<Row> to DataFrame
		 * 3. Query DataFrame and get Dataset<Row> obejct
		 * 2. Convert Dataset<Row> to DataFrame to JSON String to JSONarray
		 * 
		 * Reference:
		 * https://stackoverflow.com/questions/45141929/dataframe-to-json-array-in-spark
		 * https://stackoverflow.com/questions/15609306/convert-string-to-json-array
		 * */
		List<DriverInfo> driversInfo = new ArrayList<DriverInfo>();
		JSONArray driversInfoQuery = new JSONArray(
											this.unionData.toDF()
											.groupBy("driverID", "carPlateNumber").count()
											.toJSON().collectAsList().toString());
		for(int i = 0; i < driversInfoQuery.length(); i++) {
			DriverInfo driver = new DriverInfo();
			driver.setDriverID(driversInfoQuery.getJSONObject(i).getString("driverID"));
			driver.setCarPlateNumber(driversInfoQuery.getJSONObject(i).getString("carPlateNumber"));
			driversInfo.add(driver);
		}
		driversInfoQuery = null;
		System.gc();
		
		//int count = 0;
		//for(int i = 0; i < jsonArray.length(); i++) {
		//	count = count + Integer.valueOf(jsonArray.getJSONObject(i).getInt("count"));
		//}
		//System.out.println(count);
		
		JSONArray driversRecQuery = new JSONArray(
				this.unionData.toDF()
				.toJSON().collectAsList().toString());
		
		return responseFactory;
	}
	
	@Override
	public ResponseFactory getDriverSummary(String driverID){
		return responseFactory;
	}
	
	@Override
	public ResponseFactory getDriverSummaryWithTime(String driverID, String time){
		return responseFactory;
	}
	
	private DriverRecord configDriverRecord(JSONArray query) {
		DriverRecord driverRecord = new DriverRecord();
		for(int i = 0; i < query.length(); i++) {
			//...
		}
		return driverRecord;
	}
	
	
	//private ResponseFactory responseFormation(){}
	
}