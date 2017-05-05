package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pool.Jdbc_JNDI_DBCP;

public class TestJNDIServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		
		
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try{
			con = Jdbc_JNDI_DBCP.getConnection();
			String sql = "insert into account(name,salary) values('Áº¶¹¶¹','2500')";
			st = con.prepareStatement(sql);
			st.executeUpdate();
			
			System.out.print(con);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			Jdbc_JNDI_DBCP.release(con, st, rs);
		}
	
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
		
	}

}
