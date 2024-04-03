package hk.polyu.webservice.spark.DBAP.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.text.SimpleDateFormat;  
import java.util.Date; 

// Spring dependencies
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;


//Package dependencies
import hk.polyu.webservice.spark.DBAP.entity.DriverInfo;
import hk.polyu.webservice.spark.DBAP.entity.DriverRecord;
import hk.polyu.webservice.spark.DBAP.service.DriverBehaviorAnalysisService;
import hk.polyu.webservice.spark.DBAP.status.ResponseFactory;


@RestController
public class ApplicationController{
	
	//TO-Do: Autowire a service and here
	@Autowired
	DriverBehaviorAnalysisService driverBehaviorAnalysisService;
	
	
	@GetMapping("/test")
	//@ResponseBody is included in the @RestController annotation
	public String test() {
		return "The service is available at: " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
	}
	
	@GetMapping("/")
	public String home() {
		return "Home Page"; 
	}
	
	@GetMapping("/drivers")
	public ResponseFactory generateSummary() {
		return driverBehaviorAnalysisService.getAllDriverSummary(); 
	}
	
	
	@GetMapping("/drivers/{driverID}")
	public void generateDriverSummary(
			@PathVariable(required = true) String driverID,
			@RequestParam(name = "time", defaultValue = "", required = false) String time){
		/*
		 * Here we assume one car per driver
		 * To-Do: verification of the above assumption
		 * 
		 * Guides to choose between PathVariable and RequestParam:
		 * https://stackoverflow.com/questions/37878684/when-to-choose-requestparam-over-pathvariable-and-vice-versa
		 * https://www.baeldung.com/spring-requestparam-vs-pathvariable
		 * */
	}

}