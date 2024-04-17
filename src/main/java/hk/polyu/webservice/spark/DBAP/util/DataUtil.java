package hk.polyu.webservice.spark.DBAP.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataUtil{
	public static final List<String> columnName = Collections.unmodifiableList( new ArrayList<String>() {
		{
	        add("driverID");
	        add("carPlateNumber");
	        add("latitude");
	        add("longtitude");
	        add("speed");
	        add("direction");
	        add("siteName");
	        add("time");
	        add("isRapidlySpeedup");
	        add("isRapidlySlowdown");
	        add("isNeutralSlide");
	        add("isNeutralSlideFinished");
	        add("neutralSlideTime");
	        add("isOverspeed");
	        add("isOverspeedFinished");
	        add("overspeedTime");
	        add("isFatigueDriving");
	        add("isHthrottleStop");
	        add("isOilLeak");
	    }
	} );
	public static Integer NUMBER_OF_FEATURES = 19;
}