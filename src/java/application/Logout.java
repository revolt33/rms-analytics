package application;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ayush
 */
public class Logout extends HttpServlet {

	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		request.getSession().invalidate();
		for ( Cookie cookie: request.getCookies() ) {
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
		response.sendRedirect(response.encodeRedirectURL("login.jsp"));
	}

}
