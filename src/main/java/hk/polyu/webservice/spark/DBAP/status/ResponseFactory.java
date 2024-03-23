package hk.polyu.webservice.spark.DBAP.status;


import java.util.ArrayList;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;


public class ResponseFactory {
	
	private Status status;
	private ArrayList<Dataset<Row>> returnDataList;
	
	
	//Example access: Status.ERROR.getStatusCode()
	public void setStatus(Status status){ this.status = status; }
	public int getStatusCode() {return this.status.getStatusCode();}
	public String getStatusMsg() {return this.status.getStatusMessage();}
	
	public void setDriverDataList(ArrayList<Dataset<Row>> returnDataList){ this.returnDataList = returnDataList; }
	public ArrayList<Dataset<Row>> getReturnDataList(){ return this.returnDataList; }
	
}