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
	
	// exception 을 받아서 stacktrace를 찍어서 거르기
	public void needIntercepting(Exception exception) {
		StringBuffer exceptionSb = null;
		try {
			throw new Exception(exception);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("needIntercepting(Exception exception) entry point..........");
			 exceptionSb =  new StringBuffer();
			StackTraceElement[] trace = e.getStackTrace();
			 for (int i = 0; i < trace.length - 1; ++i){
				 exceptionSb.append(trace[i].getClassName());
				 exceptionSb.append(trace[i].getMethodName());
				 exceptionSb.append(".");
				 exceptionSb.append(trace[i].getLineNumber());
//				 logger.info("exceptionSb.toString() :: "+exceptionSb.toString());
			 }
			
			
		}finally{
			String threadId = Long.toString(Thread.currentThread().getId());
			String key = "Exception." + threadId +"(threadId)." + exceptionSb.toString();
			client.timing(key, 1);
		
		}
		
	}


	
	
	// intercept excetion
	// and catch the trace
	// return exception hasp tabel(request url , exception string buffer)
	// when event exception, the catch - ?? how ? Httpservlet 이 들ㅇ어왔을 때..할까?
	// 아니면 RuntimeException 으로 감시할까?
	// HttpServlet 과 RuntimeException 차이 보기
	// exception

	// exception 이 났을 때..

	// was 환경 이니까..서즐릿 exception 으로 하면 되지 않을까?
	// 머가 맞는지 모르느까.. 우선 되는대로???

	// /////////////////////////
	//
	// 음... 익셉션이 나면..캐치해서. 서블릿 정보를 가져다고... 익셉션 내용을 statsd 로 쏘고..
	//
	// 정 안되면 서블릿을 모니터링 하고 있다가.. 익셉션을 어케 추적하는 지 다시 생각해 보기!~

}
