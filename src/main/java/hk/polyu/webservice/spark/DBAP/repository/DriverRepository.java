package hk.polyu.webservice.spark.DBAP.repository;



import org.json.JSONArray;

//Spark dependencies
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;




public class DriverRepository{
	
	
	public JSONArray getCountQueryResult(Dataset<Row> driversData, String col1, String col2) {
		return new JSONArray(
							driversData.toDF()
							.filter(driversData.col(col2).isNotNull())
							.groupBy(col1, col2).count()
							.toJSON().collectAsList().toString());
	}
	
	public JSONArray getSumQueryResult(Dataset<Row> driversData, String col1, String col2) {
		return new JSONArray(
							driversData.toDF()
							.filter(driversData.col(col2).isNotNull())
							.groupBy(col1)
							.sum(col2)
							.toJSON().collectAsList().toString());
	}
	
	public JSONArray getValueQueryResult(Dataset<Row> driversData, String col1, String value, String col2) {
		return new JSONArray(
							driversData.toDF()
							.filter(col1 + " ='" + value + "'")
							.groupBy(col1, col2).count()
							.toJSON().collectAsList().toString());
	}

}