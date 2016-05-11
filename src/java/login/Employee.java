package login;

import java.io.*;
import javax.servlet.http.*;
import business.employee.*;
import application.*;

public class Employee extends HttpServlet {
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String remember = request.getParameter("remember");
		remember = remember==null?"off":remember;
		if (username != null && username.length() > 0 && password != null && password.length() > 0) {
			Connector connector = (Connector) (getServletContext().getAttribute("connector"));
			ConnectionToken token = connector.getToken();
			String errorCode = "1";
			boolean success = false;
			LoginStatus ls = null;
			if (token != null) {
				ls = Processor.login(username, password, (remember.equals("on")), connector.getConnection(token));
				switch (ls.getStatus()) {
					case Processor.LOGIN_SUCCESS:
						success = true;
						request.getSession().setAttribute("auth_token", ls.getToken());
						break;
					case Processor.INCORRECT_USERNAME:
						errorCode = "2";
						break;
					case Processor.INCORRECT_PASSWORD:
						errorCode = "3";
						break;
					case Processor.EXCEPTION_OCCURED:
						errorCode = "4";
						break;
					case Processor.INACTIVE_USER:
						errorCode = "5";
						break;
				}
			} else {
				errorCode = "4";
			}
			connector.closeConnection(token);
			if (success) {
				if ( remember.equals("on") ){
					Cookie[] cookies = new Cookie[4];
					cookies[0] = new Cookie("remember", "y");
					cookies[1] = new Cookie("auth_token", ls.getToken().getAuthToken());
					cookies[2] = new Cookie("id", ""+ls.getToken().getId());
					cookies[3] = new Cookie("type", ""+ls.getToken().getType());
					for ( Cookie cookie: cookies ) {
						cookie.setMaxAge(86400*30);
						response.addCookie(cookie);
					}
				}
				try {
					response.sendRedirect(response.encodeRedirectURL("employee/"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					response.sendRedirect(response.encodeRedirectURL("login.jsp?error=" + errorCode));
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}
