package hk.polyu.webservice.spark.DBAP.entity;

import org.springframework.stereotype.Component;

@Component
public class DriverStats{
	
	/*
	 * This current version does not require JPA or database, 
	 * and, Assume data is stored locally
	 * 
	 * When migrating to AWS, if require database storage, modify by configuring @Entity notation
	 * */
	
	private int numOfOverspeed = 0;
	private int totalOverspeedT = 0;
	private int numOfFatigue = 0;
	private int numOfNeutralSlide = 0;
	private int totalNeutralSlideT = 0;
	private int numOfHurryThrottleStop = 0;
	private int numOfOilLeak = 0;
	
	public void setNumOfOverspeed(int numOfOverspeed) { this.numOfOverspeed = numOfOverspeed; }
	public int getNumOfOverspeed() { return this.numOfOverspeed; }

	public void setTotalOverspeedT(int totalOverspeedT) { this.totalOverspeedT = totalOverspeedT; }
	public int getTotalOverspeedT() { return this.totalOverspeedT; }
	
	public void setNumOfFatigue(int numOfFatigue) { this.numOfFatigue = numOfFatigue; }
	public int getNumOfFatigue() { return this.numOfFatigue; }
	
	public void setNumOfNeutralSlide(int numOfNeutralSlide) { this.numOfNeutralSlide = numOfNeutralSlide; }
	public int getNumOfNeutralSlide() { return this.numOfNeutralSlide; }
	
	public void setTotalNeutralSlideT(int totalNeutralSlideT) { this.totalNeutralSlideT = totalNeutralSlideT; }
	public int getTotalNeutralSlideT() { return this.totalNeutralSlideT; }
	
	public void setNumOfHurryThrottleStop(int numOfHurryThrottleStop) { this.numOfHurryThrottleStop = numOfHurryThrottleStop; }
	public int getNumOfHurryThrottleStop() { return this.numOfHurryThrottleStop; }
	
	public void setNumOfOilLeak(int numOfOilLeak) { this.numOfOilLeak = numOfOilLeak; }
	public int getNumOfOilLeak() { return this.numOfOilLeak; }
}