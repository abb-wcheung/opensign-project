package org.owasp.oss.httpserver;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.owasp.oss.ca.UserManager;
import org.owasp.oss.ca.model.User;

public class LoginServlet extends OSSBaseServlet {

	private static Logger log = Logger.getLogger(OpenSignResourceServlet.class);
	
	private boolean login(HttpServletRequest req) throws IOException {
		HttpSession session = req.getSession();
		String userName = req.getParameter("user_name");
		String password = req.getParameter("password");
		if (userName != null || password != null) {
			// New login attempt
			User user = UserManager.getInstance().getUser(userName);
			if (user != null) {
				if (user.getPassword() != null && user.getPassword().equals(password)) {
					session.setAttribute("user", user);
					
					_user = user;
					_userName = userName;
					_content = "Successfully logged in!";
					_title = "Login";
					
					log.info("User " + _userName + " has logged in");
					
					return true;
				}
			}			
		} 
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		this.load(req, resp);
		
		HttpSession session = req.getSession();
		String userName = req.getParameter("user_name");
		String password = req.getParameter("password");					

		if (userName != null || password != null) {
			// New login attempt
			User user = UserManager.getInstance().getUser(userName);
			if (user != null) {
				if (user.getPassword().equals(password)) {
					session.setAttribute("user", user);
					
					_user = user;
					_userName = userName;
					_content = "Successfully logged in!";
					_title = "Login";
					
					log.info("User " + _userName + " has logged in");
					
					send();
					return;
				}
			}			
		} 
		
		resp.sendRedirect("error.html");
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		load(req, resp);
		
		// TODO: remove
		if (login(req))
			return;
		
		_title = "login";
		
		_content = 
		"<form name=\"login\" action=\"/login\" method=\"POST\">" +
			"<table><tr><td></td><td></td></tr><tr>" +
					"<td>user name:</td><td><input name=\"user_name\" value=\"user1\" type=text ></td>" +
				"</tr><tr>" +
					"<td>password:</td><td><input name=\"password\" value=\"123\" type=text ></td>" +
				"</tr><tr>" +
					"<td colspan=\"2\" align=\"center\"><input id=\"button\" type=\"submit\" value=\"submit\"></td>" +
				"<tr></tr></tr>" +
			"</table>" +
		"</form>";			
		
		send();
	}

	
}