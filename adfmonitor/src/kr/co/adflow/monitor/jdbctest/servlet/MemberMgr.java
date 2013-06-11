package kr.co.adflow.monitor.jdbctest.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MemberMgr {
	private final String JDBC_DRIVER = "oracle.jdbc.OracleDriver";
	private final String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private final String user = "ADFMONITOR";
	private final String password = "1234";
	
	public MemberMgr() {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("Member 드라이버 로딩 성공...");
		}
	}
	
	public Boolean memberInsert(MemberBean memberBean){
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		
		try {
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("memeberInsert Connection Success...");
			String sql = "insert into memeber values(?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberBean.getId());
			pstmt.setString(2, memberBean.getPwd());
			pstmt.setString(3, memberBean.getName());
			pstmt.setInt(4, memberBean.getAge());
			pstmt.setString(5, memberBean.getGender());
			pstmt.setString(6, memberBean.getEmail());
			
			int count = pstmt.executeUpdate();
			if (count > 0) {
				flag = true;
			}
			
		} catch (SQLException e) {
			System.out.println("memberInsert Connection failed :: "+e);
		}finally{
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					System.out.println("pstmt close Failed " + e);
				}
			}
			if(conn != null){
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
