package org.owasp.oss.httpserver;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.oss.ca.User;
import org.owasp.oss.ca.UserManager;

public class LogoutServlet extends HttpServlet {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		session.invalidate();

		OSSHtmlTemplate template = new OSSHtmlTemplate();
		template.setContent("Successfully logged out!");
		template.setTitle("Logout");
		
		PrintWriter respBody = resp.getWriter();
		respBody.write(template.build());
		respBody.flush();
	}
}
