package kr.co.adflow.monitor.connleak;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import kr.co.adflow.monitor.statsd.StatsdClient;

import org.apache.log4j.Logger;

public class OpenConnections extends Thread {

	private static OpenConnections openConnections = new OpenConnections();

	// connection instance
	private static Hashtable cpendConnht = new Hashtable();

	private static StatsdClient client = StatsdClient.getChanInstance();

	private static Logger logger = Logger.getLogger(OpenConnections.class);

	private OpenConnections() {
		this.start();
	}

	/* Static 'instance' method */
	public static OpenConnections getInstance() {
		return openConnections;
	}

	public static Hashtable getCpendConnht() {
		if (cpendConnht == null) {
			logger.debug("null..cpendConnht..hash");
			cpendConnht = new Hashtable();
		}
		return cpendConnht;
	}

	public void run() {
		while (true) {
			// get openedConnections
			OpenConnections openedConnections = OpenConnections.getInstance();
			//
			Hashtable ht = openedConnections.getCpendConnht();

//			logger.info("openedConnections.getCpendConnht() :: " + ht.size());
			// �ð�üũ��������
			long currentTime = System.currentTimeMillis();

			for (Enumeration e = ht.keys(); e.hasMoreElements();) {
				long threadID = (Long) e.nextElement();
				logger.info("threadID : " + threadID);
				long startTime = (Long) ht.get(threadID);
				long elapsedTime = currentTime - startTime;
				logger.info("currentTime : " + currentTime + " * startTime : " + startTime);
				logger.info("elapsedTime : " + elapsedTime);
				// �ɸ��ð��� ���� �б�
				if (elapsedTime < 3000) {
					// ����
					// pass
				} else {
					logger.info(" connectionLeak ");
					// connectionLeak �ϰ��
					// statsd �� ����

					Date date = new Date();
					System.out.println(date);
					String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
					.format(Calendar.getInstance().getTime());
			
					logger.info(timeStamp);
					String clientKey = "connLeak." + timeStamp +"."+Long.toString(threadID);
					int leakConunter = ht.size();
					logger.info("clientKey :: " + clientKey
							+ " * leakConunter :: " + leakConunter);
					
					
					client.timing(clientKey, leakConunter);

				}

			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}
}
