package hk.polyu.webservice.spark.DBAP.service;


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

	
}