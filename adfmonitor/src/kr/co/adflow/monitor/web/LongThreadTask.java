package kr.co.adflow.monitor.web;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

public class LongThreadTask extends TimerTask {
	private static Logger logger = Logger.getLogger(LongThreadTask.class);
	private static String InRun = "public void run()...";
	private static String ThreadcurrentThread = "Thread.currentThread():";
	private static String ThreadKey = "ThreadKey:";
	private static String ThradID = "ThreadID:";
	private static String LongRunningThread = "Find..longRunning..Thread";
	private static String FirstCheck = "FirstCheck!";
	private static String LONGRunningThreadCount = "LongRunningCount:";
	private static int longRunningThreadCount = 0;
	private Timer timer;
	private StatsdClient client;
	private Hashtable ht;

	public LongThreadTask() {
	}

	public LongThreadTask(Timer timer, Hashtable ht, StatsdClient client) {
		this.timer = timer;
		this.ht = ht;
		this.client = client;
		if (ht == null) {
			ht = new Hashtable();
		}
	}

	public void checkThread() {

		Enumeration enKey = ht.keys();
		while (enKey.hasMoreElements()) {
			String key = (String) enKey.nextElement();
			logger.debug(ThreadKey + key);
			long startTime = (Long) ht.get(key);
			long checkTime = System.currentTimeMillis();
			long threadRunningTime = checkTime - startTime;
			logger.debug(threadRunningTime);
			if (threadRunningTime > 6000) {
				longRunningThreadCount++;
				logger.debug(LONGRunningThreadCount + longRunningThreadCount);
				logger.debug(LongRunningThread);
				logger.debug(ThradID + key);
				client.timing("LongRunningThread.Count",
						longRunningThreadCount, 1);
				this.getStackTrace(key);
				ht.remove(key);
				timer.cancel();

			}
		}

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

	public void checkInt() {
		if (longRunningThreadCount > 1000000) {
			longRunningThreadCount = 0;
		}
	}

	@Override
	public void run() {
		logger.debug(InRun);
		logger.debug(ThreadcurrentThread + Thread.currentThread());
		checkThread();

	}
}
