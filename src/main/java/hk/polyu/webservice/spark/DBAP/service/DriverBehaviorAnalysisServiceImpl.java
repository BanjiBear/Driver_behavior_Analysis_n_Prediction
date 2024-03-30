package hk.polyu.webservice.spark.DBAP.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//Spark dependencies
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.types.IntegerType;

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
	
	/*
	 * 
	 * To-Do:
	 * When migrating to AWS with database, 
	 * add Autowire with repository and add Transactional
	 * 
	 * */
	
	@Autowired
	private Dataset<Row> getData;
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
		
		//int count = 0;
		//for(int i = 0; i < jsonArray.length(); i++) {
		//	count = count + Integer.valueOf(jsonArray.getJSONObject(i).getInt("count"));
		//}
		//System.out.println(count);
		
		for(int i = 0; i < driversInfo.size(); i++) {
			DriverStats driverStats = new DriverStats();
			driverStats = getEventStats(driversInfo.get(i).getDriverID(), driverStats, "isOverspeed", "setNumOfOverspeed");
			driverStats = getEventStats(driversInfo.get(i).getDriverID(), driverStats, "overspeedTime", "setTotalOverspeedT");
			driverStats = getEventStats(driversInfo.get(i).getDriverID(), driverStats, "isFatigueDriving", "setNumOfFatigue");
			driverStats = getEventStats(driversInfo.get(i).getDriverID(), driverStats, "isNeutralSlide", "setNumOfNeutralSlide");
			driverStats = getEventStats(driversInfo.get(i).getDriverID(), driverStats, "neutralSlideTime", "setTotalNeutralSlideT");
			driverStats = getEventStats(driversInfo.get(i).getDriverID(), driverStats, "isHthrottleStop", "setNumOfHurryThrottleStop");
			driverStats = getEventStats(driversInfo.get(i).getDriverID(), driverStats, "isOilLeak", "setNumOfOilLeak");
			driversInfo.get(i).setDriverStats(driverStats);
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
		this.query = new JSONArray(
							this.getData.toDF()
							.filter(this.getData.col(col2).isNotNull())
							.groupBy(col1, col2).count()
							.toJSON().collectAsList().toString());
		return this.query;
	}
	
	
	private DriverStats getEventStats(String driverID, DriverStats driverStats, String event, String setFunction) {
		this.query = this.getCountQueryResult("driverID", event);
		for(int i = 0; i < this.query.length(); i++) {
			if(this.query.getJSONObject(i).getString("driverID").equals(driverID)) {
				try {
					Method method = driverStats.getClass().getDeclaredMethod(setFunction, int.class);
					try {
						method.invoke(driverStats, this.query.getJSONObject(i).getInt("count"));
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
				break;
			}
		}
		return driverStats;
	}
	
	
	//private ResponseFactory responseFormation(){}
	
}