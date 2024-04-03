package hk.polyu.webservice.spark.DBAP.repository;



import org.json.JSONArray;

//Spark dependencies
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;




public class DriverRepository{
	
	
	//Count col2, and group (col1, col2)
	public JSONArray getCountQueryResult(Dataset<Row> driversData, String col1, String col2) {
		return new JSONArray(
							driversData.toDF()
							.filter(driversData.col(col2).isNotNull())
							.groupBy(col1, col2).count()
							.toJSON().collectAsList().toString());
	}
	//Given date D, count col2, and group (col1, col2)
	public JSONArray getCountWithTimeQueryResult(Dataset<Row> driversData, String col1, String col2, String time) {
		return new JSONArray(
							driversData.toDF()
							.filter(driversData.col("time").contains(time))
							.filter(driversData.col(col2).isNotNull())
							.groupBy(col1, col2).count()
							.toJSON().collectAsList().toString());
	}
	
	
	//Add up col2, and group (col1, col2)
	public JSONArray getSumQueryResult(Dataset<Row> driversData, String col1, String col2) {
		return new JSONArray(
							driversData.toDF()
							.filter(driversData.col(col2).isNotNull())
							.groupBy(col1)
							.sum(col2)
							.toJSON().collectAsList().toString());
	}
	//Given data D, add up col2, and group (col1, col2)
	public JSONArray getSumWithTimeQueryResult(Dataset<Row> driversData, String col1, String col2, String time) {
		return new JSONArray(
							driversData.toDF()
							.filter(driversData.col("time").contains(time))
							.filter(driversData.col(col2).isNotNull())
							.groupBy(col1)
							.sum(col2)
							.toJSON().collectAsList().toString());
	}
	
	
	//Find col1 = col1Val, and group (col1, col2)
	public JSONArray getValueQueryResult(Dataset<Row> driversData, String col1, String value, String col2) {
		return new JSONArray(
							driversData.toDF()
							.filter(col1 + " ='" + value + "'")
							.groupBy(col1, col2).count()
							.toJSON().collectAsList().toString());
	}
	//Given data D, find col1 = col1Val
	public JSONArray getValueWithTimeQueryResult(Dataset<Row> driversData, String col1, String col1Val, String time) {
		return new JSONArray(
							driversData.toDF()
							.filter(driversData.col("time").contains(time))
							.filter(col1 + " ='" + col1Val + "'")
							.toJSON().collectAsList().toString());
	}

}