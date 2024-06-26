package hk.polyu.webservice.spark.DBAP.status;

public enum Status{
	
	ERROR (404, "No query records or results found! Please verify the submitted information"),
	NO_SUCH_TIME_RECORD (600, "No records or results found during the given time for this driver!"),
	NO_SUCH_DRIVER_RECORD (500, "Driver does not exist! No records or results found for this driver!"),
	INPUT_TIME_FORMAT_ERROR (400, "The input time format is not correct! "
			+ "Please input time as in: 'YYYY-MM-DD' or 'YYYY-MM-DD hh:mm:ss'"),
	UNEXPECTED_ERROR (300, "Unexpected Error Occur, currently cannot provide any results!"),
	RESULT_FOUND (200, "Query results found!");
	
	private int statusCode;
	private String statusMessage;
	
	Status(int statusCode, String statusMessage) {
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
	}
	
	
	//Example access: Status.ERROR.getStatusCode()
	//Reference: https://stackoverflow.com/questions/24529739/tuple-enum-in-java
	public int getStatusCode(){ return this.statusCode; }
	public String getStatusMessage(){ return this.statusMessage; }
	
}