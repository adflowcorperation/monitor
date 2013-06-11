package kr.co.adflow.monitor.jdbctest.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class JoinusService {

	private static Logger logger = Logger.getLogger(JoinusService.class);

	public Boolean memberInsert(MemberBean memberBean) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean flag = false;

		try {

			conn = ConnectDB.getInstance().getConnection();
			System.out.println("memeberInsert Connection Success...");
			String sql = "insert into  member values(?,?,?,?,?,?)";
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
			System.out.println("memberInsert Connection failed :: " + e);
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					System.out.println("pstmt close Failed " + e);
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
