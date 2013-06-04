package kr.co.adflow.monitor.web;

import java.util.HashSet;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.jboss.byteman.rule.Rule;
import org.jboss.byteman.rule.helper.Helper;

public class WebHelper extends Helper {

	private static Logger logger = Logger.getLogger(WebHelper.class);
	private LongThreadTable instance = LongThreadTable.getInstantce();
	private static Hashtable hash = new Hashtable();
	private static HashSet set = new HashSet();
	private static long elapesdTime = 0;
	private static int threadCount = 0;
	private static StatsdClient client = StatsdClient.getChanInstance();

	protected WebHelper(Rule rule) {
		super(rule);
		// TODO Auto-generated constructor stub
	}

	public void startTime(String threadId) {
		long startTime = System.currentTimeMillis();
		logger.debug("startTime:" + startTime);
		hash = instance.getThreadHt();
		set = instance.getSet();
		hash.put(threadId, startTime);
		threadCount = hash.size();

	}

	public void stopTime(String threadID) {
		long stopTime = System.currentTimeMillis();
		hash = instance.getThreadHt();
		set = instance.getSet();
		elapesdTime = stopTime - (Long) hash.remove(threadID);
		set.remove(threadID);
		logger.debug("elapesdTime:" + elapesdTime);

	}

	public void elapesdTimeSend(StringBuffer bf) {
		logger.debug("!!!!!!!!requrl:" + bf.toString());
		bf.delete(0, 7);
		String key = bf.toString();
		key = key.replace(".", "-");
		key = key.replace("/", ".");
		key = key.replace(":", "-");
		logger.debug("key:" + key);
		bf.delete(0, bf.toString().length());
		if (!key.contains("localhost")) {
			client.timing(key, (int) elapesdTime);
		}
	}

	public void threadCountSend() {
		logger.debug("ThreadCountTest:" + threadCount);
		client.timing("ThreadCountTest", threadCount);
	}

}
