package kr.co.adflow.monitor.web;

import java.util.Hashtable;
import java.util.Timer;

import org.apache.log4j.Logger;
import org.jboss.byteman.rule.Rule;

public class WebHelper extends DefaultMonitoringHelper {

	private static Logger logger = Logger.getLogger(WebHelper.class);
	private static String Thread_ID = "Thread_ID:";
	private static String Thread_NAME = "Thread_NAME:";
	private static String ThreadCount = "ThreadCount";
	private static String HelperStart = "start()";
	private static String HelperStop = "stop()";
	private static String ElapesdTime = "ElapesdTime:";
	private static long elapesdTime = 0;
	private static int threadCount = 0;
	private static int startCount = 0;
	private static int stopCount = 0;
	private Timer timer;
	private static Hashtable elapesdTimeHt = new Hashtable();

	protected WebHelper(Rule rule) {
		super(rule);
		// TODO Auto-generated constructor stub
	}

	public void longThreadCheck(String threadId, String threadName) {
		startCount++;
		if (startCount > 1) {
			long startTime = System.currentTimeMillis();
			logger.debug(Thread_ID + threadId);
			logger.debug(Thread_NAME + threadName);
			Hashtable ht = new Hashtable();
			ht.put(threadId, startTime);
			timer = new Timer();
			LongThreadTask myTask = new LongThreadTask(timer, ht, client);
			timer.schedule(myTask, 0, 1000);
		} else {
			logger.debug(HelperStart);
		}
	}

	public void elapesdTimeStrat(String threadId) {
		long startTime = System.currentTimeMillis();
		elapesdTimeHt.put(threadId, startTime);
		threadCount = elapesdTimeHt.size();
	}

	public void elapesdTimeStop(String threadId) {
		long stopTime = System.currentTimeMillis();
		elapesdTime = stopTime - (Long) elapesdTimeHt.remove(threadId);
		logger.debug(ElapesdTime + elapesdTime);

	}

	public void elapesdTimeSend(StringBuffer bf) {
		bf.delete(0, 7);
		String key = bf.toString();
		key = key.replace(".", "-");
		key = key.replace("/", ".");
		key = key.replace(":", "-");
		logger.debug("key:" + key);
		bf.delete(0, bf.toString().length());
		if (!key.contains("localhost")) {
			client.timing(key, (int) elapesdTime, 1.0);
		}
	}

	public void threadCountSend() {
		logger.debug(ThreadCount+":"+threadCount);
		client.timing(ThreadCount, threadCount);
	}
	
	


}
