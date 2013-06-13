package kr.co.adflow.monitor.helper;

import java.util.Enumeration;
import java.util.Hashtable;

import org.apache.log4j.Logger;

public class OpenConnections extends Thread {

	private static OpenConnections openConnections = new OpenConnections();
	private static Hashtable cpendConnht = new Hashtable();
	private static Logger logger = Logger.getLogger(OpenConnections.class);
	private OpenConnections() {
		this.start();
	}

	/* Static 'instance' method */
	public static OpenConnections getInstance() {
		return openConnections;
	}

	public static Hashtable getCpendConnht() {
		return cpendConnht;
	}

	public static void setCpendConnht(Hashtable cpendConnht) {
		OpenConnections.cpendConnht = cpendConnht;
	}

	@Override
	public void run() {
		while (true) {
			
			
			// get openedConnections
			OpenConnections openedConnections = OpenConnections.getInstance();
			//
			Hashtable ht = openedConnections.getCpendConnht();
		
			long currentTime = System.currentTimeMillis();
			
			for (Enumeration e = ht.keys(); e.hasMoreElements();) {
				long threadID = (Long) e.nextElement();
				logger.debug("ConnectionthreadID : " + threadID);
				long startTime = (Long) ht.get(threadID);
				long elapsedTime = currentTime - startTime;
				logger.debug("ConnectionrelaseTime : " + elapsedTime);
		
				if (elapsedTime < 1000 ) {
				
				} else {
					logger.debug(" connectionLeak ");
				
				}

			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}
}
