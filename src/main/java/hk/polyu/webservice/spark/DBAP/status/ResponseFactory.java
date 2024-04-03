package hk.polyu.webservice.spark.DBAP.status;


import java.util.ArrayList;
import java.util.List;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import hk.polyu.webservice.spark.DBAP.entity.DriverInfo;


public class ResponseFactory {
	
	private Status status;
	private List<DriverInfo> returnDataList;
	
	
	//Example access: Status.ERROR.getStatusCode()
	public void setStatus(Status status){ this.status = status; }
	public int getStatusCode() {return this.status.getStatusCode();}
	public String getStatusMsg() {return this.status.getStatusMessage();}
	
	public void setReturnDataList(List<DriverInfo> returnDataList){ this.returnDataList = returnDataList; }
	public List<DriverInfo> getReturnDataList(){ return this.returnDataList; }
	
}