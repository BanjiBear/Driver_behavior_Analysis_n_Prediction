package hk.polyu.webservice.spark.DBAP.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


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
	private ArrayList<DriverRecord> driverRecordList = null;
	private DriverStats driverStats = null;
	
	// Constructor DI vs Filed DI: https://odrotbohm.de/2013/11/why-field-injection-is-evil/
//	@Autowired
//	public DriverInfo(DriverRecord driverRecord) {
//		this.driverRecord = driverRecord;
//	}
	
	public void setDriverStats(DriverStats driverStats) { this.driverStats = driverStats; }
	public DriverStats getDriverStats() { return this.driverStats; }
	
	public void setDriverRecordList(ArrayList<DriverRecord> driverRecordList) { this.driverRecordList = driverRecordList; }
	public ArrayList<DriverRecord> getDriverRecordList() { return this.driverRecordList; }
	
	public void setDriverID(String driverID) { this.driverID = driverID; }
	public String getDriverID() { return this.driverID; }
	
	public void setCarPlateNumber(String carPlateNumber) { this.carPlateNumber = carPlateNumber; }
	public String getCarPlateNumber() { return this.carPlateNumber; }
}