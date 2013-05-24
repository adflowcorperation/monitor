package kr.co.adflow.web;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.TimerTask;

import org.apache.log4j.Logger;

public class ElapsedTimerTask extends TimerTask {

	private static Logger logger = Logger.getLogger(ElapsedTimerTask.class);
	Hashtable ht = new Hashtable();
	Hashtable longCount = new Hashtable();
	int count = 0;

	public ElapsedTimerTask(Hashtable ht) {
		this.ht = ht;
	}

	@Override
	public void run() {
		try {
			logger.debug("Thread running...");
			logger.debug("현재 실행중인 쓰레드:" + Thread.currentThread());
			Enumeration enKey = ht.keys();
			if (!enKey.hasMoreElements()) {
				logger.debug("Thread Stop...");
				Thread.currentThread().interrupt();
				if (Thread.currentThread().interrupted()) {
					throw new InterruptedException();
				}

			}
			while (enKey.hasMoreElements()) {
				String key = (String) enKey.nextElement();
				logger.debug("thread key:" + key);
				long temp = (Long) ht.get(key);
				long checkTime = System.currentTimeMillis();
				long longThreadCheck = checkTime - temp;
				if (longThreadCheck > 3000) {
					logger.debug("longRunningThread!!    ThreadID:"
							+ key);
					longCount.put(key, Thread.currentThread());
					logger.debug("longRunningThread!!    Count:"
							+ longCount.size());
				}
				logger.debug("longThreadCheck:" + longThreadCheck);
			}
		} catch (InterruptedException e) {
			logger.debug("InterruptedException!!");
			return;
		}

	}

}
