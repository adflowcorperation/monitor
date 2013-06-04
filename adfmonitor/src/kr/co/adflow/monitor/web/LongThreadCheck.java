package kr.co.adflow.monitor.web;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class LongThreadCheck extends TimerTask implements
		ServletContextListener {

	ServletContext sc = null;

	private Timer timer = new Timer();
	private static Logger logger = Logger.getLogger(LongThreadCheck.class);
	private static StatsdClient client = StatsdClient.getChanInstance();
	private HashSet set;
	LongThreadTable longThreadTable = LongThreadTable.getInstantce();

	public LongThreadCheck() {
		// TODO Auto-generated constructor stub

	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		timer.cancel();
		client.close();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

		run();
		timer.scheduleAtFixedRate(this, 0, 1000);
	}

	@Override
	public void run() {
		long checkTime = System.currentTimeMillis();
		String key = "";
		long threadRunningTime = 0;
		Hashtable ht = longThreadTable.getThreadHt();
		Enumeration e = ht.keys();
		while (e.hasMoreElements()) {
			key = (String) e.nextElement();
			long startTime = (Long) ht.get(key);
			threadRunningTime = checkTime - startTime;
			logger.debug(threadRunningTime);
			if (threadRunningTime > 3000) {
				logger.debug("key:" + key);
				set = longThreadTable.getSet();
				set.add(key);
				client.timing("longThreadCont", set.size());
				logger.debug("setSize:" + set.size());

			}

		}

	}

}
