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
//	 when event exception, the catch - ?? how ? Httpservlet 이 들ㅇ어왔을 때..할까?
//	                                            아니면 RuntimeException 으로 감시할까?
//	                                             HttpServlet  과 RuntimeException 차이 보기
//exception 	
	
	
	// exception 이 났을 때..

	
	
	// was 환경 이니까..서즐릿 exception 으로 하면 되지 않을까?
	// 머가 맞는지 모르느까.. 우선 되는대로???

	
	///////////////////////////
//	
//	음...  익셉션이 나면..캐치해서. 서블릿 정보를 가져다고... 익셉션 내용을 statsd 로 쏘고..
//	
//	정 안되면 서블릿을 모니터링 하고 있다가.. 익셉션을 어케 추적하는 지 다시 생각해  보기!~
	

}
