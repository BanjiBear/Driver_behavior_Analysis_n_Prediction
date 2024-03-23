package hk.polyu.webservice.spark.DBAP.service;


import java.util.ArrayList;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
//Spring dependencies
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;


//Package dependencies
import hk.polyu.webservice.spark.DBAP.status.ResponseFactory;



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
	private ArrayList<Dataset<Row>> getData;
	private ResponseFactory responseFactory = new ResponseFactory();
	
	@Override
	public ResponseFactory getAllDriverSummary(){
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
	
	
	//private ResponseFactory responseFormation(){}
	
}