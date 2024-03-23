package hk.polyu.webservice.spark.DBAP.status;

public enum Status{
	
	ERROR (404, ""),
	NO_SUCH_TIME_RECORD (400, ""),
	NO_SUCH_DRIVER_RECORD (300, ""),
	RESULT_FOUND (200, "");
	
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