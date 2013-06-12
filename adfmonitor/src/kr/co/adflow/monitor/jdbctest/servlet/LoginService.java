package kr.co.adflow.monitor.jdbctest.servlet;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginService {



	

	public boolean authenticated(String id, String pwd) {
	
		Connection conn = null;
		boolean flag = false;
		Statement stmt = null;
		ResultSet rs = null;

		
		try {

			conn =  ConnectDB.getInstance().getConnection();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
			
				e.printStackTrace();
			}
		
			stmt = conn.createStatement();
		
			
			System.out.println("conn.createStatement(); end");
			String sql = "select * from member where id='" + id + "'";
			System.out.println("during debugging.before.");
			System.out.println(sql);
					
			rs = stmt.executeQuery(sql);
		
			
			System.out.println("during debugging.after.");
			if (!rs.next()) {
				flag = false;
			} else {
				String correctPassword = rs.getString("pwd");
				if (pwd.equals(correctPassword)) {
					flag = true;

				} else {
					flag = false;

				}
			}

		} catch (SQLException e) {
			System.out.println("To Get Connection is failed..." + e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					System.out.println("Stmt close Failed " + e);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					System.out.println("Conn close Failed " + e);
				}
			}
		}
		return flag;
	}

}
