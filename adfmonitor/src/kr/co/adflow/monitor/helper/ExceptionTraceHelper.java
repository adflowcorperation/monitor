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
	
	// exception �� �޾Ƽ� stacktrace�� �� �Ÿ���
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
	// when event exception, the catch - ?? how ? Httpservlet �� �餷����� ��..�ұ�?
	// �ƴϸ� RuntimeException ���� �����ұ�?
	// HttpServlet �� RuntimeException ���� ����
	// exception

	// exception �� ���� ��..

	// was ȯ�� �̴ϱ�..���� exception ���� �ϸ� ���� ������?
	// �Ӱ� �´��� �𸣴���.. �켱 �Ǵ´��???

	// /////////////////////////
	//
	// ��... �ͼ����� ����..ĳġ�ؼ�. ���� ������ �����ٰ�... �ͼ��� ������ statsd �� ���..
	//
	// �� �ȵǸ� ������ ����͸� �ϰ� �ִٰ�.. �ͼ����� ���� �����ϴ� �� �ٽ� ������ ����!~

}
