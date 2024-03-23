package hk.polyu.webservice.spark.DBAP.entity;


import org.springframework.stereotype.Component;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Component
public class DriverRecord{
	
	/*
	 * This current version does not require JPA or database, 
	 * and, Assume data is stored locally
	 * 
	 * When migrating to AWS, if require database storage, modify by configuring @Entity notation
	 * */
		
	private String latitude;
	private String longtitude;
	
	private String speed;
	private String direction;
	private String siteName;
	private String time;
	
	private String isRapidlySpeedup;
	private String isRapidlySlowdown;
	
	private String isNeutralSlide;
	private String isNeutralSlideFinished;
	private String neutralSlideTime;
	
	private String isOverspeed;
	private String isOverspeedFinished;
	private String overspeedTime;
	
	private String isFatigueDriving;
	private String isHthrottleStop;
	private String isOilLeak;
	
	
	public void setLatitude(String latitude) { this.latitude = latitude; }
	public String getLatitude() { return this.latitude; }
	
	public void setLongtitude(String longtitude) { this.longtitude = longtitude; }
	public String getLongtitude() { return this.longtitude; }
	
	public void setSpeed(String speed) { this.speed = speed; }
	public String getSpeed() { return this.speed; }
	
	public void setDirection(String direction) { this.direction = direction; }
	public String getDirection() { return this.direction; }
	
	public void setSiteName(String siteName) { this.siteName = siteName; }
	public String getSiteName() { return this.siteName; }
	
	public void setTime(String time) { this.time = time; }
	public String getTime() { return this.time; }
	
	public void setIsRapidlySpeedup(String isRapidlySpeedup) { this.isRapidlySpeedup = isRapidlySpeedup; }
	public String getIsRapidlySpeedup() { return this.isRapidlySpeedup; }
	
	public void setIsRapidlySlowdown(String isRapidlySlowdown) { this.isRapidlySlowdown = isRapidlySlowdown; }
	public String getIsRapidlySlowdown() { return this.isRapidlySlowdown; }
	
	public void setIsNeutralSlide(String isNeutralSlide) { this.isNeutralSlide = isNeutralSlide; }
	public String getIsNeutralSlide() { return this.isNeutralSlide; }
	
	public void setIsNeutralSlideFinished(String isNeutralSlideFinished) { this.isNeutralSlideFinished = isNeutralSlideFinished; }
	public String getIsNeutralSlideFinished() { return this.isNeutralSlideFinished; }
	
	public void setNeutralSlideTimeD(String neutralSlideTime) { this.neutralSlideTime = neutralSlideTime; }
	public String getNeutralSlideTime() { return this.neutralSlideTime; }
	
	public void setIsOverspeed(String isOverspeed) { this.isOverspeed = isOverspeed; }
	public String getIsOverspeed() { return this.isOverspeed; }
	
	public void setIsOverspeedFinished(String isOverspeedFinished) { this.isOverspeedFinished = isOverspeedFinished; }
	public String getIsOverspeedFinished() { return this.isOverspeedFinished; }
	
	public void setOverspeedTime(String overspeedTime) { this.overspeedTime = overspeedTime; }
	public String getOverspeedTime() { return this.overspeedTime; }
	
	public void setIsFatigueDriving(String isFatigueDriving) { this.isFatigueDriving = isFatigueDriving; }
	public String getIsFatigueDriving() { return this.isFatigueDriving; }
	
	public void setIsHthrottleStop(String isHthrottleStop) { this.isHthrottleStop = isHthrottleStop; }
	public String getIsHthrottleStop() { return this.isHthrottleStop; }
	
	public void setIsOilLeak(String isOilLeak) { this.isOilLeak = isOilLeak; }
	public String getIsOilLeak() { return this.isOilLeak; }

}