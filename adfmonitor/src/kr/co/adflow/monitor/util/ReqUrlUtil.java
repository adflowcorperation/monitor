package kr.co.adflow.monitor.util;

import org.apache.log4j.Logger;

public class ReqUrlUtil {

	private static ReqUrlUtil reqUrlUtil = new ReqUrlUtil();
	private static Logger logger = Logger.getLogger(ReqUrlUtil.class);
	private ReqUrlUtil() {

	}

	public static ReqUrlUtil urlUtilgetInstance() {

		return reqUrlUtil;
	}

	public String sendStatsdUrl(StringBuffer bf) {
		logger.debug("requestUrl:" + bf.toString());
		bf.delete(0, 7);
		String key = bf.toString();
		key = key.replace(".", "-");
		key = key.replace("/", ".");
		key = key.replace(":", "-");
		logger.debug("key:" + key);
		return key;
	}
}
