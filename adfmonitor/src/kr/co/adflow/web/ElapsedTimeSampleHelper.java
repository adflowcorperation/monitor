package kr.co.adflow.web;

import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.jboss.byteman.rule.Rule;

public class ElapsedTimeSampleHelper extends DefaultMonitoringHelper implements HelperIF {

	private static Hashtable ht = new Hashtable();
	private static Logger logger = Logger.getLogger(ElapsedTimeSampleHelper.class);
	private static long result = 0;
	private static StringBuffer bf = new StringBuffer();

	protected ElapsedTimeSampleHelper(Rule rule) {
		super(rule);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start() {
		long test = System.currentTimeMillis();
		ht.put(Thread.currentThread(), test);
		logger.debug("startTIme:" + test);
	}

	@Override
	public void stop() {
		long test = System.currentTimeMillis();
		System.out.println("stopTime:" + test);
		result = test - (Long) ht.remove(Thread.currentThread());
		logger.debug("elaspedTime:" + result);
		

	}

	public void ruleData(StringBuffer getRequestURL) {
		logger.debug("getRequestURL:" + getRequestURL.toString());
		bf.append(getRequestURL.toString());
	}

	public void send() {
		bf.delete(0, 7);
		String key = bf.toString();
		key = key.replace(".", "-");
		key = key.replace("/", ".");
		key = key.replace(":", "-");
		logger.debug("key:" + key);
		bf.delete(0, bf.toString().length());
		if (!key.contains("localhost")) {
			client.timing(key, (int) result, 1.0);
		}
	}
}
