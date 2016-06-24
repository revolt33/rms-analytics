package login;

import application.ConnectionToken;
import application.Connector;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import business.customer.*;
import javax.servlet.http.Cookie;
import org.json.simple.JSONObject;

public class Customer extends HttpServlet {


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String remember = request.getParameter("remember");
		remember = remember == null ? "off" : remember;
		if (username != null && username.length() > 0 && password != null && password.length() > 0) {
			Connector connector = (Connector) (getServletContext().getAttribute("connector"));
			ConnectionToken token = connector.getToken();
			if ( token != null ) {
				Connection con = connector.getConnection(token);
				LoginStatus ls = Processor.login(username, password, remember.equals("on"), con);
				connector.closeConnection(token);
				switch ( ls.getStatus() ) {
					case SUCCESS:
						request.getSession().setAttribute("customer_auth_token", ls.getToken());
						if ( remember.equals("on") ) {
							Cookie[] cookies = new Cookie[3];
							cookies[0] = new Cookie("remember_customer", "y");
							cookies[1] = new Cookie("customer_id", "" + ls.getToken().getId());
							cookies[2] = new Cookie("customer_auth_token", ls.getToken().getAuthToken());
							for (Cookie cookie : cookies) {
								cookie.setMaxAge(86400 * 30);
								response.addCookie(cookie);
							}
						}
						response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath()+"/"));
						break;
					case INCORRECT_PASSWORD:
						response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath()+"/?error=user_password"));
						break;
					case INCORRECT_USERNAME:
						response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath()+"/?error=user_username"));
						break;
					case EXCEPTION_OCCURED:
						response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath()+"/?error=user_error"));
						break;
				}
			}
		}
	}
}
