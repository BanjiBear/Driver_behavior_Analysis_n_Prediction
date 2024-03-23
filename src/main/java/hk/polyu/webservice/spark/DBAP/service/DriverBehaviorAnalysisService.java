package hk.polyu.webservice.spark.DBAP.service;

import hk.polyu.webservice.spark.DBAP.status.ResponseFactory;

public interface DriverBehaviorAnalysisService{
	
	public ResponseFactory getAllDriverSummary();
		
	public ResponseFactory getDriverSummary(String driverID);
	
	public ResponseFactory getDriverSummaryWithTime(String driverID, String time);
	
}