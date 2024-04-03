package hk.polyu.webservice.spark.DBAP.service;


import java.util.ArrayList;
import java.util.HashMap;
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
import hk.polyu.webservice.spark.DBAP.entity.DriverStats;



@Service
public class DriverBehaviorAnalysisServiceImpl implements DriverBehaviorAnalysisService{

	
	@Autowired
	private Dataset<Row> driversData;
	@Autowired
	private HashMap<String, JSONArray> driversStatsByEvent;
	private JSONArray query;
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
		this.query = this.getCountQueryResult("driverID", "carPlateNumber");
		for(int i = 0; i < this.query.length(); i++) {
			DriverInfo driver = new DriverInfo();
			driver.setDriverID(this.query.getJSONObject(i).getString("driverID"));
			driver.setCarPlateNumber(this.query.getJSONObject(i).getString("carPlateNumber"));
			driversInfo.add(driver);
		}
		
		for(int i = 0; i < driversInfo.size(); i++) {
			DriverStats driverStats = new DriverStats();
			for (String key : this.driversStatsByEvent.keySet()) {
				driverStats = configDriverStats(driversInfo.get(i).getDriverID(), driverStats, key, this.driversStatsByEvent.get(key));
			}
			driversInfo.get(i).setDriverStats(driverStats);
		}
		
		for(int i = 0; i < driversInfo.size(); i++) {
			System.out.println(driversInfo.get(i));
		}

		
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
	
	private JSONArray getCountQueryResult(String col1, String col2) {
		return new JSONArray(
							this.driversData.toDF()
							.filter(this.driversData.col(col2).isNotNull())
							.groupBy(col1, col2).count()
							.toJSON().collectAsList().toString());
	}
	
	private JSONArray getSumQueryResult(String col1, String col2) {
		return new JSONArray(
							this.driversData.toDF()
							.filter(this.driversData.col(col2).isNotNull())
							.groupBy(col1)
							.sum(col2)
				.toJSON().collectAsList().toString());
	}
	
	
	private DriverStats configDriverStats(String driverID, DriverStats driverStats, String event, JSONArray query) {
		for(int i = 0; i < query.length(); i++) {
			if(query.getJSONObject(i).getString("driverID").equals(driverID)) {
				if(event.equals("isOverspeed")) { driverStats.setNumOfOverspeed(query.getJSONObject(i).getInt("count")); }
				else if(event.equals("overspeedTime")) {driverStats.setTotalOverspeedT(query.getJSONObject(i).getInt("sum(overspeedTime)")); }
				else if(event.equals("isFatigueDriving")) { driverStats.setNumOfFatigue(query.getJSONObject(i).getInt("count")); }
				else if(event.equals("isNeutralSlide")) { driverStats.setNumOfNeutralSlide(query.getJSONObject(i).getInt("count")); }
				else if(event.equals("neutralSlideTime")) {driverStats.setTotalNeutralSlideT(query.getJSONObject(i).getInt("sum(neutralSlideTime)")); }
				else if(event.equals("isHthrottleStop")) { driverStats.setNumOfHurryThrottleStop(query.getJSONObject(i).getInt("count")); }
				else if(event.equals("isOilLeak")) { driverStats.setNumOfOilLeak(query.getJSONObject(i).getInt("count")); }
				break;
			}
		}
		return driverStats;
	}
	
	
	//private ResponseFactory responseFormation(){}
	
}