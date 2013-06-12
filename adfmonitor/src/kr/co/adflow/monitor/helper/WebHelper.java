package kr.co.adflow.monitor.helper;

import java.util.HashSet;
import java.util.Hashtable;

import kr.co.adflow.monitor.statsd.StatsdClient;
import kr.co.adflow.monitor.util.ReqUrlUtil;
import kr.co.adlfow.monitor.check.LongThreadCheck;

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
		Hashtable hash = null;
		try {
			long startTime = System.currentTimeMillis();
			logger.debug("startTime:" + startTime);
			hash = instance.getThreadHt();
			HashSet set = instance.getSet();
			hash.put(threadId, startTime);
			int threadCount = hash.size();
			logger.debug("threadCont:" + threadCount);
			client.timing("threadCount", threadCount);
		} catch (Exception e) {
			e.printStackTrace();
			hash.remove(threadId);
		}
	}

	public void stopTime(String threadID, StringBuffer bf) {
		try {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
