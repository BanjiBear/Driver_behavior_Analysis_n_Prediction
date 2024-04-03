package hk.polyu.webservice.spark.DBAP.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.json.JSONArray;

//Spark dependencies
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

//Spring dependencies
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Package dependencies
import hk.polyu.webservice.spark.DBAP.status.ResponseFactory;
import hk.polyu.webservice.spark.DBAP.status.Status;
import hk.polyu.webservice.spark.DBAP.entity.DriverInfo;
import hk.polyu.webservice.spark.DBAP.entity.DriverRecord;
import hk.polyu.webservice.spark.DBAP.entity.DriverStats;
import hk.polyu.webservice.spark.DBAP.repository.DriverRepository;



@Service
public class DriverBehaviorAnalysisServiceImpl implements DriverBehaviorAnalysisService{

	
	@Autowired
	private Dataset<Row> driversData;
	@Autowired
	private HashMap<String, JSONArray> driversStatsByEvent;
	private JSONArray query;
	private DriverRepository repo = new DriverRepository();
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
		try {
			//Create Drivers
			this.query = this.repo.getCountQueryResult(this.driversData, "driverID", "carPlateNumber");
			for(int i = 0; i < this.query.length(); i++) {
				DriverInfo driver = new DriverInfo();
				driver.setDriverID(this.query.getJSONObject(i).getString("driverID"));
				driver.setCarPlateNumber(this.query.getJSONObject(i).getString("carPlateNumber"));
				driversInfo.add(driver);
			}
			
			//Create Driver Stats
			for(int i = 0; i < driversInfo.size(); i++) {
				DriverStats driverStats = new DriverStats();
				for (String key : this.driversStatsByEvent.keySet()) {
					driverStats = configDriverStats(driversInfo.get(i).getDriverID(), driverStats, key, this.driversStatsByEvent.get(key));
				}
				driversInfo.get(i).setDriverStats(driverStats);
			}
		}
		catch(Exception e) {
			return responseFormation(Status.UNEXPECTED_ERROR, driversInfo);
		}
		
		return responseFormation(Status.RESULT_FOUND, driversInfo);
	}
	
	@Override
	public ResponseFactory getDriverSummary(String driverID){
		List<DriverInfo> driversInfo = new ArrayList<DriverInfo>();
		
		try {
			//Verify Driver exist
			this.query = this.repo.getValueQueryResult(this.driversData, "driverID", driverID, "carPlateNumber");
			if(this.query.isEmpty() && this.query.isNull(0)) {
				return responseFormation(Status.NO_SUCH_DRIVER_RECORD, driversInfo);
			}
			
			//Create Driver
			DriverInfo driver = new DriverInfo();
			driver.setDriverID(driverID);
			driver.setCarPlateNumber(this.query.getJSONObject(0).getString("carPlateNumber"));
			
			//Create Driver Stats
			DriverStats driverStats = new DriverStats();
			for (String key : this.driversStatsByEvent.keySet()) {
				driverStats = configDriverStats(driver.getDriverID(), driverStats, key, this.driversStatsByEvent.get(key));
			}
			driver.setDriverStats(driverStats);
			driversInfo.add(driver);
			System.out.println(this.query.length());
		}
		catch(Exception e) {
			return responseFormation(Status.UNEXPECTED_ERROR, driversInfo);
		}

		return responseFormation(Status.RESULT_FOUND, driversInfo);
	}
	
	@Override
	public ResponseFactory getDriverSummaryWithTime(String driverID, String time){
		List<DriverInfo> driversInfo = new ArrayList<DriverInfo>();
		
		//Sample Time format: 2017-01-01 or 2017-01-01 08:02:10
		Pattern p1 = Pattern.compile("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}$");
		Pattern p2 = Pattern.compile("\\d{4}-\\d{2}-\\d{2}$");
		try {
			if (p1.matcher(time).find() || p2.matcher(time).find()) {
				//Verify Driver exist
				this.query = this.repo.getValueQueryResult(this.driversData, "driverID", driverID, "carPlateNumber");
				if(this.query.isEmpty() && this.query.isNull(0)) {
					return responseFormation(Status.NO_SUCH_DRIVER_RECORD, driversInfo);
				}
				
				//Query Result
				this.query = this.repo.getValueWithTimeQueryResult(this.driversData, "driverID", driverID, time);
				if(this.query.isEmpty() && this.query.isNull(0)) {
					return responseFormation(Status.NO_SUCH_TIME_RECORD, driversInfo);
				}
				
				//Create Driver
				DriverInfo driver = new DriverInfo();
				driver.setDriverID(driverID);
				driver.setCarPlateNumber(this.query.getJSONObject(0).getString("carPlateNumber"));
				
				//Create Driver Stats
				HashMap<String, JSONArray> tmp = new HashMap<String, JSONArray>();
				tmp.put("isOverspeed", this.repo.getCountWithTimeQueryResult(this.driversData, "driverID", "isOverspeed", time));
				tmp.put("overspeedTime", this.repo.getSumWithTimeQueryResult(this.driversData, "driverID", "overspeedTime", time));
				tmp.put("isFatigueDriving", this.repo.getCountWithTimeQueryResult(this.driversData, "driverID", "isFatigueDriving", time));
				tmp.put("isNeutralSlide", this.repo.getCountWithTimeQueryResult(this.driversData, "driverID", "isNeutralSlide", time));
				tmp.put("neutralSlideTime", this.repo.getSumWithTimeQueryResult(this.driversData, "driverID", "neutralSlideTime", time));
				tmp.put("isHthrottleStop", this.repo.getCountWithTimeQueryResult(this.driversData, "driverID", "isHthrottleStop", time));
				tmp.put("isOilLeak", this.repo.getCountWithTimeQueryResult(this.driversData, "driverID", "isOilLeak", time));
				
				DriverStats driverStats = new DriverStats();
				for (String key : tmp.keySet()) {
					driverStats = configDriverStats(driver.getDriverID(), driverStats, key, tmp.get(key));
				}
				driver.setDriverStats(driverStats);
				driversInfo.add(driver);
			}
			else {
				return responseFormation(Status.INPUT_TIME_FORMAT_ERROR, driversInfo);
			}
		}
		catch(Exception e) {
			return responseFormation(Status.UNEXPECTED_ERROR, driversInfo);
		}
		
		return responseFormation(Status.RESULT_FOUND, driversInfo);
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
	
	
	private ResponseFactory responseFormation(Status status, List<DriverInfo> resultList){
		this.responseFactory.setStatus(status);
		this.responseFactory.setReturnDataList(resultList);;
		return this.responseFactory;
	}
	
}