package kr.co.adflow.web;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jboss.byteman.rule.Rule;

public class TimeThreadHelper extends DefaultMonitoringHelper {

	private static Logger logger = Logger.getLogger(TimeThreadHelper.class);

	protected static java.util.Hashtable ht = new java.util.Hashtable();
	protected static java.util.Hashtable longCount = new Hashtable();
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

		TimerTask timeTask = new TimerTask() {

			@Override
			public void run() {

				logger.debug("Thread running...");
				logger.debug("현재 실행중인 쓰레드:" + Thread.currentThread());
				Enumeration enKey = ht.keys();
				if (!enKey.hasMoreElements()) {
					logger.debug("Thread Stop...");
					timer.cancel();
				}

				while (enKey.hasMoreElements()) {
					String key = (String) enKey.nextElement();
					logger.debug("thread key:" + key);
					long temp = (Long) ht.get(key);
					long checkTime = System.currentTimeMillis();
					long longThreadCheck = checkTime - temp;
					if (longThreadCheck > 3000) {
						logger.debug("longRunningThread!!    ThreadID:" + key);
						longCount.put(key, Thread.currentThread());
						logger.debug("longRunningThread!!    Count:"
								+ longCount.size());
						getStackTrace(key);
					}
					logger.debug("longThreadCheck:" + longThreadCheck);
				}
			}
		};
		timer.scheduleAtFixedRate(timeTask, 0, 1000);

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

	public void getStackTrace(String threadID) {
		long id = Long.parseLong(threadID);
		int i = 0;
		ThreadMXBean threadmbean = ManagementFactory.getThreadMXBean();
		ThreadInfo tr2 = threadmbean.getThreadInfo(id, Integer.MAX_VALUE);
		StackTraceElement[] ste1 = tr2.getStackTrace();
		for (StackTraceElement list2 : ste1) {
			logger.debug("Method_Trace : " + list2.getMethodName() + " (" + i
					+ ")");
			i++;
		}
	}
}
