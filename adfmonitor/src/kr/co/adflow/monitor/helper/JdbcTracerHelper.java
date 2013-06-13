package kr.co.adflow.monitor.helper;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jboss.byteman.rule.Rule;
import org.jboss.byteman.rule.helper.Helper;

public class JdbcTracerHelper extends Helper {
	private static Logger logger = Logger.getLogger(JdbcTracerHelper.class);
	private static String bytemanQuery = null;
	private static long result = 0;

	// long timeNow = System.currentTimeMillis();

	private String connId;
	private static SimpleDateFormat sdf = new SimpleDateFormat("MM/DD HH:mm:ss");

	protected JdbcTracerHelper(Rule rule) {
		super(rule);

		logger.info("JdbcTracerHelper(Rule rule)  Default constructorn loading :: ");

	}

	public String whoCalledMe() {
		StringBuffer whocme = new StringBuffer(300);
		StackTraceElement[] trace = new Throwable().getStackTrace();

		for (int i = 0; i < trace.length - 1; ++i) {
			whocme.append(trace[i].getClassName());
			whocme.append(".");
			whocme.append(trace[i].getMethodName());
			whocme.append("() line:");
			// whocme.append("() line:");
			whocme.append(trace[i].getLineNumber());
			// whocme.append("<BR>\n");
		}
		whocme.append(sdf.format(new Date()));
		return whocme.toString();
	}

	// getconnection �̶� sql exception ��� �� �Լ��� ���� ���� �б� �ϸ� ����
	// ������?
	public void registerOpenedConnection() throws SQLException {
		logger.info("getConnectionTracker method entry.......................");
		// openedConnections.put(getConnectionId(), whoCalledMe());
		OpenConnections openedConnections = OpenConnections.getInstance();
		Hashtable ht = openedConnections.getCpendConnht();
		int connectionCounter = ht.size();
		logger.info("connectionCounter :: " + connectionCounter);
		ht.put(Thread.currentThread().getId(), System.currentTimeMillis());
		Enumeration em = null;
		em = ht.keys();

		while (em.hasMoreElements()) {
			String key = em.nextElement().toString();
			logger.info("key :: " + ht.get(key));

		}

		logger.info("connId :: " + connId);

	}

	public void test(Connection conn) {
		logger.info(" test(Connection conn) wiht byteman rule");
		Connection connection = null;
		connection = conn;
		try {
			logger.info("conn.isClosed() :: " + conn.isClosed());
			if (!conn.isClosed()) {
				registerOpenedConnection();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	public void releaseConnection(Connection connection) {
		logger.info("releaseConnection..................");
		OpenConnections openedConnections = OpenConnections.getInstance();
		Hashtable ht = openedConnections.getCpendConnht();
		ht.remove(Thread.currentThread().getId());

	}

	public void getThreadId(HttpServletRequest request) {

		logger.debug("request.getContentType() :: " + request.getContentType());
		logger.debug("request.getContextPath() :: " + request.getContextPath());
		System.out
				.println("Thread.currentThread() : " + Thread.currentThread());
		System.out.println("Thread.currentThread().getId() : "
				+ Thread.currentThread().getId());
		System.out
				.println("HttpServletRequest request #####################################################################");
		System.out.println(request.getAuthType());
		System.out.println(request.getContentLength());
		System.out.println(request.getLocalAddr());
		System.out.println(request.getLocalName());
		System.out.println(request.getLocalPort());
		System.out.println("request.getContentType() :: "
				+ request.getContentType());
		System.out.println("request.getContextPath() :: "
				+ request.getContextPath());
	}

	public void executeQueryByteman(String str) {
		System.out.println("*****************************************");
		System.out.println("executeQueryByteman(String str)");
		System.out.println("getQuery with Byteman");
		bytemanQuery = str;
		System.out.println(str);
		System.out.println("*****************************************");
	}

}
