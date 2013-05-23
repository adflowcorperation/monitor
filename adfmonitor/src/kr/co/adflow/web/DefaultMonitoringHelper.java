package kr.co.adflow.web;

import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.jboss.byteman.rule.Rule;
import org.jboss.byteman.rule.helper.Helper;

public class DefaultMonitoringHelper extends Helper {

	protected static StatsdClient client = null;
	protected static StatsdProperties properties = null;
	protected static String host=null;
	protected static int port=0;
	private final String FHOST = "host";
	private final String FPORT = "port";
	private static Logger logger = Logger.getLogger(DefaultMonitoringHelper.class);

	protected DefaultMonitoringHelper(Rule rule) {
		super(rule);
		properties = new StatsdProperties();
		host = properties.read(FHOST);
		String portTemp = properties.read(FPORT);
		port = Integer.parseInt(portTemp);
		logger.info("statsdHost::" + host);
		logger.info("statsdPort::" + port);
		if (client == null) {
			try {
				client = new StatsdClient(host, port);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
