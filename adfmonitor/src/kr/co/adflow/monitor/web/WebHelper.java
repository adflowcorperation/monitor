package kr.co.adflow.monitor.web;

import java.util.HashSet;
import java.util.Hashtable;

import kr.co.adflow.monitor.util.ReqUrlUtil;

import org.apache.log4j.Logger;
import org.jboss.byteman.rule.Rule;
import org.jboss.byteman.rule.helper.Helper;

import com.sun.jndi.toolkit.url.UrlUtil;

public class WebHelper extends Helper {

	private static Logger logger = Logger.getLogger(WebHelper.class);
	private LongThreadCheck instance = LongThreadCheck.getInstantce();
	private static StatsdClient client = StatsdClient.getChanInstance();
	private static ReqUrlUtil reqUrlUtil = ReqUrlUtil.urlUtilgetInstance();

	protected WebHelper(Rule rule) {
		super(rule);
		// TODO Auto-generated constructor stub
	}

	public void startTime(String threadId) {
		long startTime = System.currentTimeMillis();
		logger.debug("startTime:" + startTime);
		Hashtable hash = instance.getThreadHt();
		HashSet set = instance.getSet();
		hash.put(threadId, startTime);
		int threadCount = hash.size();
		logger.debug("threadCont:" + threadCount);
		client.timing("threadCount", threadCount);
	}

	public void stopTime(String threadID, StringBuffer bf) {
		long stopTime = System.currentTimeMillis();
		Hashtable hash = instance.getThreadHt();
		HashSet set = instance.getSet();
		long elapesdTime = stopTime - (Long) hash.remove(threadID);
		set.remove(threadID);
		logger.debug("elapesdTime:" + elapesdTime);
		String key = reqUrlUtil.sendStatsdUrl(bf);
		if (!key.contains("localhost")) {
			client.timing(key, (int) elapesdTime);
		}

	}
}
