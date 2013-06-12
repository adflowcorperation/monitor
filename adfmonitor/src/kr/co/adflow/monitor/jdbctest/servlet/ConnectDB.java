package kr.co.adflow.monitor.jdbctest.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.net.ssl.SSLEngineResult.Status;

public class ConnectDB {

	private static Connection conn;
	private static ConnectDB instance;
	
	private final String JDBC_DRIVER = "oracle.jdbc.OracleDriver";
	private final String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	private final String user = "ADFMONITOR";
//	private final String password = "0308";
	private final String password = "1234";
	
	
	private ConnectDB()
	{

	    try {

	    	Class.forName(JDBC_DRIVER);
	      

	    }

	    catch(ClassNotFoundException e)
	    {

	        System.err.println(e.getMessage());

	    }   
	}
	
	
	public static ConnectDB getInstance()
	  {

	      if(instance == null) {

	          instance = new ConnectDB();

	      }

	      return instance;

	  }

	public Connection getConnection() {
		  //connect DB
    	try {
			conn = DriverManager.getConnection(url, user, password);
			Boolean satus = conn.isClosed();
			System.out.println("status :: "+satus);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
}
