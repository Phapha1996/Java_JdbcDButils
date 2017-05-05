package apache_dbutils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JdbcUtils_dbutils {

	private static ComboPooledDataSource ds = null;
	private static QueryRunner qr = null;
	
	static{
		ds = new ComboPooledDataSource("mysql_Ca");
		qr = new QueryRunner(ds);
	}

	
	public static Connection getConnection() throws SQLException{
		return ds.getConnection();
	}
	
	
	public static DataSource getDataSource(){
		return ds;
	}
	
	
	public static QueryRunner getQueryRunner(){
		return qr;
	}
	
}
