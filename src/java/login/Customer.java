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
							Cookie[] cookies = new Cookie[2];
							cookies[0] = new Cookie("remember", "y");
							cookies[1] = new Cookie("id", "" + ls.getToken().getId());
							for (Cookie cookie : cookies) {
								cookie.setMaxAge(86400 * 30);
								response.addCookie(cookie);
							}
						}
						writeResponse(response, "Login Successful!");
						break;
					case INCORRECT_PASSWORD:
						writeErrorResponse(response, "Password is incorrect!");
						break;
					case INCORRECT_USERNAME:
						writeErrorResponse(response, "Username is incorrect!");
						break;
					case EXCEPTION_OCCURED:
						writeErrorResponse(response, "Internal Server Error!");
						break;
				}
			}
		}
	}
	public void writeResponse(HttpServletResponse response, String successMessage) {
		JSONObject json = new JSONObject();
		json.put("status", 1);
		json.put("message", successMessage);
		PrintWriter writer;
		try {
			writer = response.getWriter();
			writer.print(json);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	public void writeErrorResponse(HttpServletResponse response, String successMessage) {
		JSONObject json = new JSONObject();
		json.put("status", 0);
		json.put("message", successMessage);
		PrintWriter writer;
		try {
			writer = response.getWriter();
			writer.print(json);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
