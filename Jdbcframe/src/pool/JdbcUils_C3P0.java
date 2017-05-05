package pool;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JdbcUils_C3P0 {

	private static ComboPooledDataSource ds = null;
	
	static {
		try {
//			ds = new ComboPooledDataSource();
//			ds.setDriverClass("com.mysql.jdbc.Driver");
//			ds.setJdbcUrl("jdbc:mysql://localhost:3306/test");
//			ds.setUser("root");
//			ds.setPassword("root");
//			ds.setInitialPoolSize(10);
//			ds.setMinPoolSize(10);
//			ds.setMaxPoolSize(20);
			
 			ds = new ComboPooledDataSource("mysql_Ca");
			
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}

	}
	
	
	public static Connection getConnection() throws SQLException{
		return ds.getConnection();
	}
	
	
	public static void release(Connection con,Statement st,ResultSet rs){
		if(con!=null){
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				con = null;
			}
		}
		if(st!=null){
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				st = null;
			}
		}
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				rs = null;
			}
		}

	}
	
}
