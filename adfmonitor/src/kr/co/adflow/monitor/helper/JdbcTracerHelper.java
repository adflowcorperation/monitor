package kr.co.adflow.monitor.helper;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;




import org.apache.log4j.Logger;
import org.jboss.byteman.rule.Rule;
import org.jboss.byteman.rule.exception.ExecuteException;
import org.jboss.byteman.rule.helper.Helper;
import java.lang.RuntimeException;

import kr.co.adflow.monitor.connleak.OpenConnections;
import kr.co.adflow.monitor.sqlquery.ElaspedConnctions;
import kr.co.adflow.monitor.statsd.StatsdClient;

public class JdbcTracerHelper extends Helper {
	private static Logger logger = Logger.getLogger(JdbcTracerHelper.class);

	private static StatsdClient client = StatsdClient.getChanInstance();

	protected JdbcTracerHelper(Rule rule) {
		super(rule);
	}

	// register connection when connection
	public void registerOpenedConnection(Connection conn) {
	
		logger.debug("in entry");
		if (conn != null) {
			//conn = connection;
			logger.info("getConnectionTracker method entry.......................");
			OpenConnections openedConnections = OpenConnections.getInstance();

			// connection leak monitor hashtable
			Hashtable ht = openedConnections.getCpendConnht();
			
			ht.put(Thread.currentThread().getId(), System.currentTimeMillis());
			logger.info("connection leak monitor hashtable connectionCounter after PUT:: "
					+ ht.size());
			Enumeration em = null;
			em = ht.keys();
			while (em.hasMoreElements()) {
				String key = em.nextElement().toString();
//				logger.info("key :: " + ht.get(key));

			}
		}

	}

	// when sqlexception, release connection
	public void getSQLException(SQLException sqlException) throws SQLException {
		OpenConnections openedConnections = OpenConnections.getInstance();
		Hashtable ht = openedConnections.getCpendConnht();
		System.out.println("ht.size() : " + ht.size());
		logger.info("remove connection in hashtable :: with sqlException *  before remove ht.size()"
				+ ht.size());
		// remove connection in hash
		releaseConnection();
	}

	// remove connection in hashtable when connection is closed
	public void releaseConnection() {
		OpenConnections openedConnections = OpenConnections.getInstance();
		// Hashtable ht = openedConnections.getCpendConnht();
		Hashtable ht = openedConnections.getCpendConnht();
		logger.info("before removing ht.size() : " + ht.size());
		ht.remove(Thread.currentThread().getId());
		logger.info("after removing ht.size() : " + ht.size());
	}

	// get query with byteman rule with PreparedStatement getQuery Rule
	public void executeQueryByteman(String str) {
		System.out
				.println("*********************************************************************************************************************************************");
		System.out.println("executeQueryByteman(String str)");
		System.out.println("getQuery with Byteman");
		String bytemanQuery = str;
		System.out.println(str);
		Thread thread = Thread.currentThread();
		System.out.println("id :: " + thread.getId());

		ElaspedConnctions elaspedConnctions = ElaspedConnctions.getInstance();
		StringBuffer queryBuff = elaspedConnctions.getQueryBuffer();
		if (queryBuff.length() == 0) {
			queryBuff.append(str);
		}
		if (queryBuff.length() != 0) {
			logger.info("queryBuff :: " + queryBuff.toString());
			System.out
					.println("*********************************************************************************************************************************************");
		}

	}

	// get connection time
	public void getConnectionTime() {
		Hashtable ht = null;
		try {
			long startTime = System.currentTimeMillis();
			logger.debug("startTime:" + startTime);
			
				ElaspedConnctions elaspedConnctions = ElaspedConnctions
						.getInstance();
				ht = elaspedConnctions.getElapsedQueryHt();
				int measureConnTimeCount = ht.size();
				logger.info("measureConnTimeCount :: " + measureConnTimeCount);
				ht.put(Thread.currentThread().getId(),
						System.currentTimeMillis());
				Enumeration em = null;
				em = ht.keys();
				while (em.hasMoreElements()) {
					String key = em.nextElement().toString();
					logger.info("key :: " + ht.get(key));
				}
			

		} catch (Exception e) {
			e.printStackTrace();
			ht.remove(Thread.currentThread().getId());
		}
	}

	// get close time
	public void releaseConnectionTime() {
		try {
			long stopTime = System.currentTimeMillis();
			logger.info("stopTime :: " + stopTime);

			ElaspedConnctions elaspedConnctions = ElaspedConnctions
					.getInstance();
			StringBuffer queryBuff = elaspedConnctions.getQueryBuffer();
			Hashtable ht = null;
			ht = elaspedConnctions.getElapsedQueryHt();
			int measureConnTimeCount = ht.size();
			logger.info("measureConnTimeCount :: " + measureConnTimeCount);
			if (ht.size() != 0) {
				logger.info("ht.size() :: " + ht.size());
				logger.info("Thread.currentThread().getId() :: "
						+ Thread.currentThread().getId());
				long elapesdTime = stopTime
						- (Long) ht.remove(Thread.currentThread().getId());
				logger.debug("elapesdTime:" + elapesdTime);
				logger.info("ht.size() :: " + ht.size());
				String threadId = Long.toString(Thread.currentThread().getId());

				logger.info("queryBuff :: " + queryBuff.toString());
				String clientKey = "query." + queryBuff.toString() + "."
						+ threadId;
				logger.info("clientKey :: " + clientKey);

				queryBuff.setLength(0);
				logger.info("after delete.....queryBuff.setLength(0) :: "
						+ queryBuff.toString());
				client.gauge(clientKey, elapesdTime);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
