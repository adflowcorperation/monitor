package kr.co.adflow.monitor.helper;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.adflow.monitor.exception.InterceptException;

import org.apache.log4j.Logger;
import org.jboss.byteman.rule.Rule;
import org.jboss.byteman.rule.exception.ThrowException;
import org.jboss.byteman.rule.helper.Helper;

public class ExceptionTraceHelper extends Helper{

	private static Logger logger = Logger.getLogger(ExceptionTraceHelper.class);
	protected ExceptionTraceHelper(Rule rule) {
		super(rule);
		
	}
	
	public void needIntercepting(HttpServlet httpServlet, HttpServletRequest req, HttpServletResponse res){

//		throw new RuntimeException();
		logger.info("needIntercepting entry point.........................");

		HttpServlet hServlet = httpServlet;
		
		
		
		HttpServletRequest request = req;
		HttpServletResponse response = res;

		String reqLocalAddr = request.getLocalAddr(); // 127.0.0.1
		String reqLocalName = request.getLocalName(); //  8080
		String reqLocalPort =  Integer.toString(request.getLocalPort()); //  8080
		String reqURI = request.getRequestURI(); // /admin/admin.do
		String reqMethod = request.getMethod(); // GET

		logger.info("request.getLocalAddr() :: " + request.getLocalAddr());
		logger.info("request.getLocalName() :: " + request.getLocalName() );
		logger.info("request.getLocalName() :: " + request.getLocalPort());
		logger.info("request.getMethod() :: " + request.getMethod());
		logger.info("request.getPathInfo() :: " + request.getPathInfo());
		logger.info("request.getProtocol() :: " + request.getProtocol());
		logger.info("request.getRemoteAddr() :: " + request.getRemoteAddr());
		logger.info("request.getRemoteHost() :: " + request.getRemoteHost() );
		logger.info("request.getRemotePort() :: " + request.getRemotePort());
		logger.info("request.getRemotePort() :: " + request.getRemoteUser());
		
		logger.info("request.getClass().getClassLoader() :: " + request.getClass().getClassLoader());
		logger.info("response.getClass().getClassLoader() :: " + response.getClass().getClassLoader());

		response.getLocale();
		response.getContentType();

		logger.info("response.getLocale() :: " +response.getLocale());
		logger.info("response.getContentType() :: " +response.getContentType());

		
		
		
		try {
			
			hServlet.service(request, response);
		} catch (Throwable e) {
			e.printStackTrace();
			ThrowException te = new ThrowException(e);
			throw te;
		} 
		
		
		        
       
	}
	
	public void intercept(){
		
	}
	
	
//	 intercept excetion
//	 and catch the trace
//	 return exception hasp tabel(request url , exception string buffer)
//	 when event exception, the catch - ?? how ? Httpservlet �� �餷����� ��..�ұ�?
//	                                            �ƴϸ� RuntimeException ���� �����ұ�?
//	                                             HttpServlet  �� RuntimeException ���� ����
//exception 	
	
	
	// exception �� ���� ��..

	
	
	// was ȯ�� �̴ϱ�..���� exception ���� �ϸ� ���� ������?
	// �Ӱ� �´��� �𸣴���.. �켱 �Ǵ´��???

	
	///////////////////////////
//	
//	��...  �ͼ����� ����..ĳġ�ؼ�. ���� ������ �����ٰ�... �ͼ��� ������ statsd �� ���..
//	
//	�� �ȵǸ� ������ ����͸� �ϰ� �ִٰ�.. �ͼ����� ���� �����ϴ� �� �ٽ� ������  ����!~
	

}
