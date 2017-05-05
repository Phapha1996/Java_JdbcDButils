package pool;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

public class JdbcUtils_DBCP {
	private static DataSource ds = null;

	static {
		try {
			InputStream in = JdbcUtils_DBCP.class.getClassLoader().getResourceAsStream("dbcpconfig.properties");
			Properties pro = new Properties();
			pro.load(in);

			BasicDataSourceFactory factory = new BasicDataSourceFactory();			//使用Dbcp数据库连接池工厂创建出数据库池
			ds = factory.createDataSource(pro);			
			System.out.print(ds);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	public static Connection getConnection() throws SQLException{
		return ds.getConnection();
	}
	
	
	public static void release(Connection con,Statement st,ResultSet rs){			//销毁对象
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
	
	
	//替换增删改功能
	public static void update(String sql,Object param[]) throws SQLException{
		
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try{
			con = getConnection();
			st = con.prepareStatement(sql);
			
			for(int i=0;i<param.length;i++){
				st.setObject(i+1, param[i]);
			}
			
			st.executeUpdate();
		}finally{
			release(con, st, rs);
		}
	}
	

	
	//查询优化.接口实现，面向接口编程
	
	public static Object query(String sql,Object param[],ResultSetHandler rsh) throws SQLException{
		
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try{
			con = getConnection();
			st = con.prepareStatement(sql);
			
			for(int i=0;i<param.length;i++){
				st.setObject(i+1, param[i]);
			}
			
			rs = st.executeQuery();
			return rsh.handler(rs);
		}finally{
			release(con, st, rs);
		}
	}
	
}
