package kr.co.adflow.monitor.jdbctest.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(LoginServlet.class);
	LoginService loginService = new LoginService();

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id, password;
		logger.info("LoginServlet debug....................");
		logger.info("LoginServlet debug....................");
		System.out.println("login servlet ,..!!!!!!!!!!!@");
		id = request.getParameter("userId");
		password = request.getParameter("password");
		
//		id = "ADFMONITOR";
//		password ="0308";
		
		
	
		
		boolean flag;
		String result = null;
		flag = loginService.authenticated(id, password);

		System.out.println(flag);
		if(flag ==true){
			HttpSession session = request.getSession();
			session.setAttribute("sessionId", id);
			logger.info("LoginServlet Info :::::::::::::");
			logger.info("sessionID :: "+id);
			result = "SUCCESS";
//			response.sendRedirect("/EyleeBoard/board/list.jsp");
			response.sendRedirect("/adfmonitor/login/LoginResult.jsp");
		}
		else{
			result ="FAIL";
			response.sendRedirect("/adfmonitor/login/login.jsp");
		}
		
		

		
		
	}


}
