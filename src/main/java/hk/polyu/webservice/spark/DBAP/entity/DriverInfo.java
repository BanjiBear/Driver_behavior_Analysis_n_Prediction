package hk.polyu.webservice.spark.DBAP.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Component
public class DriverInfo{
	
	/*
	 * This current version does not require JPA or database, 
	 * and, Assume data is stored locally
	 * 
	 * When migrating to AWS, if require database storage, modify by configuring @Entity notation
	 * */
	private String driverID;
	private String carPlateNumber;
	private final DriverRecord driverRecord;
	
	// Constructor DI vs Filed DI: https://odrotbohm.de/2013/11/why-field-injection-is-evil/
	@Autowired
	public DriverInfo(DriverRecord driverRecord) {
		this.driverRecord = driverRecord;
	}
	
	public DriverRecord getDriverRecord() { return this.driverRecord; }
	
	public void setDriverID(String driverID) { this.driverID = driverID; }
	public String getDriverID() { return this.driverID; }
	
	public void setCarPlateNumber(String carPlateNumber) { this.carPlateNumber = carPlateNumber; }
	public String getCarPlateNumber() { return this.carPlateNumber; }
}