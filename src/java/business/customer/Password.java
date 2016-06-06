/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.customer;

import application.ConnectionToken;
import application.Connector;
import business.Status;
import java.io.IOException;
import java.sql.Connection;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ayush
 */
public class Password extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connector connector = (Connector) getServletContext().getAttribute("connector");
		ConnectionToken token = connector.getToken();
		Connection con = connector.getConnection(token);

		String param = request.getParameter("param");
		boolean proceed = true;
		Enumeration<String> parameters = (Enumeration<String>) request.getParameterNames();
		while (parameters.hasMoreElements()) {
			if (request.getParameter(parameters.nextElement()).trim().length() == 0) {
				proceed = false;
				break;
			}
		}
		Status result = Status.UNKNOWN;
		if (proceed) {
			if (con != null) {
				switch (param) {
					case "set":
						String username = request.getParameter("username");
						String fpToken = request.getParameter("fp_token");
						String password = request.getParameter("password");
						String repeat = request.getParameter("re-password");
						result = Processor.changePassword(password, username, fpToken, con);
						String error = "";
						if (password == repeat) {
							if (result == Status.SUCCESS) {
								response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath() + "/"));
							} else {
								switch (result) {
									case UNAUTHORIZED_OPERATION:
										error = "This link is expired!";
										break;
									case USER_UNAVAILABLE:
										error = "User does not exist!";
										break;
									case EXCEPTION_OCCURED:
										error = "Internal server error!";
										break;
								}
								response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath() + "/setup/password.jsp?mode=set&username=" + username + "&token=" + fpToken + "&type=customer&error=" + error));
							}
						} else {
							error = "Passwords do not match!";
							response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath() + "/setup/password.jsp?mode=set&username=" + username + "&token=" + fpToken + "&type=customer&error=" + error));
						}
						break;
				}
			}
		}
	}

}
