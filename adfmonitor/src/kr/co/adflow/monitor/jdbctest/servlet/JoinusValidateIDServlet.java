package kr.co.adflow.monitor.jdbctest.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class JoinusValidateID
 */
public class JoinusValidateIDServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
		String userID = req.getParameter("id");
//		int idValidateCount = Integer.parseInt(req.getParameter("idValidateCount"));
//		int idValidation = Integer.parseInt(req.getParameter("idValidateCount"));
		
		String idValidation = req.getParameter("idValidateCount");
		System.out.println("validate ID info log ###############");
		System.out.println(userID);
	
		JoinusService joinusService = new JoinusService();
		
		
		
		req.setAttribute("idValidation", idValidation);
		req.setAttribute("userID", userID);
//		RequestDispatcher dispatcher = req
//		.getRequestDispatcher("login/joinus.jsp");
		RequestDispatcher dispatcher = req
		.getRequestDispatcher("/hellobyteman/login/retryid.jsp");
		dispatcher.forward(req, resp);
//		resp.sendRedirect("/EyleeBoard/login/joinus.jsp?idValidation="+idValidation+"&userID="+userID);
		
		
//		req.setAttribute("result", idValidation);
//		
//req.setAttribute("result", idValidation);
//		
//		RequestDispatcher dispatcher = req
//				.getRequestDispatcher("login/joinus.jsp");
//		dispatcher.forward(req, resp);
		
//		resp.sendRedirect("/EyleeBoard/login/joinus.jsp?idValidation="+idValidation);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String userID = (String) request.getAttribute("id");
//		System.out.println("validate ID info....");
//		System.out.println(userID);
		doGet(request, response);
	}

}
