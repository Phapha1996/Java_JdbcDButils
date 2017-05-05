package pool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class Jdbc_JNDI_DBCP {

	private static DataSource ds = null;
	
	
	static {
		try {
			Context initCtx = new InitialContext();				//��ʼ��JNDI
			Context envCtx = (Context) initCtx.lookup("java:comp/env");			//�õ�JNDI
			ds = (DataSource) envCtx.lookup("jdbc/EmployeeDB");				//��JNDI��Ѱ��datasource�������

		} catch (Exception e) {
			e.printStackTrace();
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
