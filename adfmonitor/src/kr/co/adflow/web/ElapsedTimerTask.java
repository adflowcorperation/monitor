package kr.co.adflow.web;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.TimerTask;

import org.apache.log4j.Logger;

public class ElapsedTimerTask extends TimerTask {

	private static Logger logger = Logger.getLogger(ElapsedTimerTask.class);
	Hashtable ht= new Hashtable();
	
	public ElapsedTimerTask (Hashtable ht){
		this.ht=ht;
	}
	
	@Override
	public void run() {
		logger.debug("in run...");
		logger.debug("현재 실행중인 쓰레드:"+Thread.currentThread());
		Enumeration enKey = ht.keys();

		if (!enKey.hasMoreElements()) {
			logger.debug("if");
			Thread.currentThread().stop();
		}
		while (enKey.hasMoreElements()) {
			String key = (String) enKey.nextElement();
			logger.debug("thread key:"+key);
			long temp = (Long) ht.get(key);
			long checkTime = System.currentTimeMillis();
			long longThreadCheck = checkTime - temp;
			if(longThreadCheck>3000){
				logger.debug("longRunningThread.....!!!!!!!:"+key);
			}
			logger.debug("longThreadCheck:" + longThreadCheck);
		}

	}

}
