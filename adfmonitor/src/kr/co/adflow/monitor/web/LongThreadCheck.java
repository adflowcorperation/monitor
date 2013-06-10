package kr.co.adflow.monitor.web;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;

import org.apache.log4j.Logger;

public class LongThreadCheck extends Thread {

	private static Logger logger = Logger.getLogger(LongThreadCheck.class);
	private static final LongThreadCheck LONG_THREAD_CHECK = new LongThreadCheck();
	private static Hashtable threadHt = new Hashtable();
	private static HashSet set = new HashSet();
	private static StatsdClient client = StatsdClient.getChanInstance();

	// Singleton
	private LongThreadCheck() {
		this.start();
	}

	public static LongThreadCheck getInstantce() {
		return LONG_THREAD_CHECK;
	}

	// getHashTable

	public static Hashtable getThreadHt() {
		return threadHt;
	}

	public static HashSet getSet() {
		return set;
	}

	
	//ThreadCheck
	@Override
	public void run() {
		while (true) {
			long checkTime = System.currentTimeMillis();
			String key = "";
			long threadRunningTime = 0;
			Hashtable ht = this.getThreadHt();
			Enumeration e = ht.keys();
			while (e.hasMoreElements()) {
				key = (String) e.nextElement();
				long startTime = (Long) ht.get(key);
				threadRunningTime = checkTime - startTime;
				logger.debug("threadRunningTime:" + threadRunningTime);
				if (threadRunningTime > 3000) {
					logger.debug("key:" + key);
					set = this.getSet();
					set.add(key);
					client.timing("longThreadCont", set.size());
					logger.debug("setSize:" + set.size());

				}

			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}

}
