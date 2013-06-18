package kr.co.adflow.monitor.system;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

import org.apache.log4j.Logger;

public class ThreadStackTrace {
	private static Logger logger = Logger.getLogger(ThreadStackTrace.class);

	public void getStackTrace(String key) {

		ThreadMXBean threadmbean = ManagementFactory.getThreadMXBean();

		long ltID = Long.parseLong(key);
		logger.info("=============ThreadStackTraceClass Thread stack trace========================");
		ThreadInfo tr2 = threadmbean.getThreadInfo(ltID, Integer.MAX_VALUE);

		StackTraceElement[] ste1 = tr2.getStackTrace();
		for (StackTraceElement list2 : ste1) {
			logger.info(" className: " + list2.getClassName());
			logger.info(" methodName: " + list2.getMethodName());

		}
	}

}
