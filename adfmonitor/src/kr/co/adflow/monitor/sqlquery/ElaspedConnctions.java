package kr.co.adflow.monitor.sqlquery;

import java.util.Hashtable;

import kr.co.adflow.monitor.statsd.StatsdClient;

import org.apache.log4j.Logger;

public class ElaspedConnctions{

	private static ElaspedConnctions elaspedConnctions = new ElaspedConnctions();


	// for query elsapsed time , for each query , make query table
	private static Hashtable elapsedQueryHt = new Hashtable();

	// manager query for each connection
	private static StringBuffer queryBuffer = new StringBuffer();

	private static StatsdClient client = StatsdClient.getChanInstance();
	private static Logger logger = Logger.getLogger(ElaspedConnctions.class);
	private ElaspedConnctions() {
	
	}
	

	/* Static 'instance' method */
	public static ElaspedConnctions getInstance() {
		logger.info("  managing  query time..............");
		return elaspedConnctions;
	}
	
	public static Hashtable getElapsedQueryHt() {
		return elapsedQueryHt;
	}

	public static StringBuffer getQueryBuffer() {
		return queryBuffer;
	}
	

	public static void setQueryBuffer(StringBuffer queryBuffer) {
		ElaspedConnctions.queryBuffer = queryBuffer;
	}


	
}
