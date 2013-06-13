package kr.co.adflow.monitor.jdbctest.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class JoinusServlet
 */
public class JoinusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(JoinusServlet.class);

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("euc-kr");
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String name = request.getParameter("username");
		String age = request.getParameter("age");
		String gender = request.getParameter("gender");
	
		logger.info(id + " :: " + password + " :: " +  " :: " + email + " :: " + name + " :: " + age + " :: "+ gender);
		
		MemberBean memberBean = new MemberBean();
		memberBean.setId(id);
		memberBean.setPwd(password);
		memberBean.setEmail(email);
		memberBean.setName(name);
		memberBean.setAge(Integer.parseInt(age));
		memberBean.setGender(gender);
		JoinusService joinusService = new JoinusService();
		boolean flag;
		flag = joinusService.memberInsert(memberBean);
		

		logger.info(flag);
		request.setAttribute("memberBean", memberBean);
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/adfmonitor/login/JoinusResult.jsp");
//		RequestDispatcher dispatcher = request
//		.getRequestDispatcher("login/retryid.jsp");
		dispatcher.forward(request, response);
		
	}


	

}
