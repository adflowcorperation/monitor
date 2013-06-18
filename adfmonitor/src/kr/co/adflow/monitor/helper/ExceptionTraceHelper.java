package kr.co.adflow.monitor.helper;

import kr.co.adflow.monitor.statsd.StatsdClient;

import org.apache.log4j.Logger;
import org.jboss.byteman.rule.Rule;
import org.jboss.byteman.rule.helper.Helper;

public class ExceptionTraceHelper extends Helper {

	private static Logger logger = Logger.getLogger(ExceptionTraceHelper.class);
	private static StatsdClient client = StatsdClient.getChanInstance();
	protected ExceptionTraceHelper(Rule rule) {
		super(rule);

	}
	
	// exception 을 받아서  key= threadID, exception message, stack trace send
	public void needIntercepting(Exception exception){
		logger.info("needIntercepting(Exception exception) entry point..........");
		StringBuffer exceptionSb = null;
		String exceptionMsn = exception.getMessage();
		logger.info("exception.getMessage() ::"+exceptionMsn);
		
		try {
			throw new Exception(exception);
		} catch (Exception e) {
			// TODO Auto-generated catch block			
			 exceptionSb =  new StringBuffer();
			StackTraceElement[] trace = e.getStackTrace();
			 for (int i = 0; i < trace.length - 1; ++i){				
				 exceptionSb.append(trace[i].getClassName());
				 exceptionSb.append(trace[i].getMethodName());				
				 exceptionSb.append("line:"+trace[i].getLineNumber());
				 exceptionSb.append("#");
//				 logger.info("exceptionSb.toString() :: "+exceptionSb.toString());
			 }
			
			
		}finally{
			try {
				if(!exception.getMessage().equals("")){
					String threadId = Long.toString(Thread.currentThread().getId());
					logger.info("threadId :: "+threadId);
					logger.info("exceptionSb.toString()" + exceptionSb.toString());
					String str = exceptionSb.toString().replace(".", "-");
					str = str.replace("/", ".");
					str = str.replace("#", "\r");
					exceptionMsn = exceptionMsn.replace(".", "-");
					logger.debug("exceptionMsn :: " +exceptionMsn);
					logger.debug("str:" + str);
					String key = "Exception." + threadId +"(threadId)."  + exceptionMsn + "."+str;
					logger.info("key :: "+key);
					client.timing(key, 1);
					}
			} catch (NullPointerException e2) {
				
			}
			
			exceptionSb.setLength(0);
		}
		
	}


	
	
	
}
