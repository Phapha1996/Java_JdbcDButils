package test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import domain.Account;
import domain.ListHandler;
import pool.BeanHandler;
import pool.JdbcUils_C3P0;
import pool.JdbcUtils_DBCP;
import pool.Jdbc_JNDI_DBCP;

public class TestClass {

	@Test
	public void testDBCP() {
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			
			con = JdbcUils_C3P0.getConnection();
			con.setAutoCommit(false);
			String sql = "insert into account(name,salary) values (?,?)";
			st = con.prepareStatement(sql);
			st.setString(1, "ôô��");
			st.setInt(2, 2000);
			st.executeUpdate();
			con.commit();
			System.out.println("�ɹ���");
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			JdbcUtils_DBCP.release(con, st, rs);
		}

	}
	
	
	@Test 
	public void testAddframe(){
		try {
			String sql = "insert into account(name,salary) values(?,?)";
			Object param[] = {"���Ƿ�", 10000};

			JdbcUtils_DBCP.update(sql, param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Test
	public void testBeanSelected(){
		
		try{
			String sql = "select * from account where name=?";	
			Object param[] = {"���Ƿ�"};
		
			Account a = (Account) JdbcUtils_DBCP.query(sql, param, new BeanHandler(Account.class));
			System.out.print(a.getId()+a.getName()+a.getSalary());
		}catch(SQLException e){
			
			e.printStackTrace();
			System.out.print("��ѯʧ�ܣ���");
		}
		
	}
	
	@Test
	public void testList(){
		try{
		String sql = "select * from account";
		Object param[] = {};
		List<Account> list = (List) JdbcUtils_DBCP.query(sql, param, new ListHandler(Account.class));
		for(int i=0;i<list.size();i++)
		System.out.println(list.get(i).getName());
		}catch(Exception e){
			System.out.print("��ѯʧ�ܣ���");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testJNDI(){
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try{
			con = Jdbc_JNDI_DBCP.getConnection();
			String sql = "insert into account(name,salary) values('������','2500')";
			st = con.prepareStatement(sql);
			st.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			Jdbc_JNDI_DBCP.release(con, st, rs);
		}
	}
	
}
