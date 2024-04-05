package hk.polyu.webservice.spark.DBAP.controller;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

//Package dependencies
import hk.polyu.webservice.spark.DBAP.service.DriverBehaviorAnalysisService;
import hk.polyu.webservice.spark.DBAP.status.ResponseFactory;


@Controller
public class ApplicationController{
	
	@Autowired
	DriverBehaviorAnalysisService driverBehaviorAnalysisService;
	
	
	@GetMapping("/test")
	//@ResponseBody is included in the @RestController annotation
	public String test() {
		return "The service is available at: " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
	}
	
	@GetMapping("/")
	public String home(Model model) {
		return "index";
	}
	
	@GetMapping("/about")
	public String aboutPage(Model model) {
		return "about";
	}

	@GetMapping("/search")
	public String searchPage(Model model) {
		return "search";
	}
	
	@GetMapping("/drivers")
	public String generateSummary(Model model) {
		//return driverBehaviorAnalysisService.getAllDriverSummary();
		model.addAttribute("response", driverBehaviorAnalysisService.getAllDriverSummary());
		return "drivers"; 
	}
	
	
	@GetMapping("/drivers/{driverID}")
	public String generateDriverSummary(
			@PathVariable(required = true) String driverID,
			@RequestParam(name = "time", defaultValue = "", required = false) String time,
			Model model){
		/*
		 * Here we assume one car per driver
		 * To-Do: verification of the above assumption
		 * 
		 * Guides to choose between PathVariable and RequestParam:
		 * https://stackoverflow.com/questions/37878684/when-to-choose-requestparam-over-pathvariable-and-vice-versa
		 * https://www.baeldung.com/spring-requestparam-vs-pathvariable
		 * */
		
		if(time.equals("") || time.isEmpty() || time.isBlank()) {
			model.addAttribute("response", driverBehaviorAnalysisService.getDriverSummary(driverID));
		}
		else {
			model.addAttribute("response", driverBehaviorAnalysisService.getDriverSummaryWithTime(driverID, time));
		}
		return "drivers";
	}

}