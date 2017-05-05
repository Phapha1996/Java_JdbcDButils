package test;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.Test;

import domain.Account;
import apache_dbutils.JdbcUtils_dbutils;

public class TestDButils {

	@Test
	public void testAdd() {

		try {
			QueryRunner qr = new QueryRunner(JdbcUtils_dbutils.getDataSource());

			String sql = "insert into account(name,salary) values(?,?)";
			Object params[] = { "马备安", 2000 };
			qr.update(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDelete() {
		try {
			QueryRunner qr = new QueryRunner(JdbcUtils_dbutils.getDataSource());

			String sql = "delete from account where id=?";
			Object param = 5;
			qr.update(sql, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	//经过改造方立勋的jdbcutils
	@Test
	public void testSelect(){
		try{
			
			String sql = "select * from account where id=?";
			Object param = 13;
			Account a = JdbcUtils_dbutils.getQueryRunner().query(sql, param, new BeanHandler<Account>(Account.class));
			System.out.println(a.getId()+a.getName()+a.getSalary());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	
	@Test
	public void testBeanList(){
		try{
			QueryRunner qr = new QueryRunner(JdbcUtils_dbutils.getDataSource());
			
			String sql = "select * from account";
			List<Account> list = qr.query(sql, new BeanListHandler<Account>(Account.class));
			System.out.println(list.size());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
		

}