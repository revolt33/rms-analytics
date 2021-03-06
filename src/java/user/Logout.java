package user;

import application.ConnectionToken;
import application.Connector;
import business.customer.AuthToken;
import business.customer.Processor;
import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Logout extends HttpServlet {


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connector connector = (Connector) getServletContext().getAttribute("connector");
		ConnectionToken token = connector.getToken();
		AuthToken authToken = (AuthToken) request.getSession().getAttribute("customer_auth_token");
		Connection con = connector.getConnection(token);
		Processor.logout(authToken, con);
		connector.closeConnection(token);
		for (Cookie cookie : request.getCookies()) {
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
		request.getSession().invalidate();
		response.sendRedirect(getServletContext().getContextPath() + "/");
	}

	
}
