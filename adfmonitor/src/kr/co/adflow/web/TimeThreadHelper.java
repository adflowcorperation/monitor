package kr.co.adflow.web;

import java.util.Timer;

import org.apache.log4j.Logger;
import org.jboss.byteman.rule.Rule;

public class TimeThreadHelper extends DefaultMonitoringHelper {

	private static Logger logger = Logger.getLogger(TimeThreadHelper.class);

	protected static java.util.Hashtable ht = new java.util.Hashtable();
	
	
	ElapsedTimerTask tThread = new ElapsedTimerTask(ht);
   
	private static long result = 0;
	private static long checkTime = 0;
	private static long longThreadCheck = 0;
	Timer timer = new Timer();

	protected TimeThreadHelper(Rule rule) {
		super(rule);
		// TODO Auto-generated constructor stub
	}

	public void start(String threadId, String threadName) {
		long startTime = System.currentTimeMillis();
		logger.debug("threadID:" + threadId + "       threadName:" + threadName);
		ht.put(threadId, startTime);
		

	}

	public void check() {

		tThread.run();
		timer.scheduleAtFixedRate(tThread, 0, 1000);

	}

	public void stop(String id) {
		logger.debug(id);
		long stopTime = System.currentTimeMillis();
		result = stopTime - (Long) ht.remove(id);
		logger.debug("elapesdTime:" + result);
	}

	public void ip(StringBuffer bf) {
		logger.debug(bf.toString());
	}
}
