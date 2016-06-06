package signup;

import application.ConnectionToken;
import application.Connector;
import business.Status;
import business.stock.Result;
import java.io.IOException;
import java.sql.Connection;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

public class User extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connector connector = (Connector) getServletContext().getAttribute("connector");
		ConnectionToken token = connector.getToken();
		Connection con = connector.getConnection(token);
		String username = request.getParameter("username");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		boolean proceed = true;
		Enumeration<String> parameters = (Enumeration<String>) request.getParameterNames();
		while (parameters.hasMoreElements()) {
			if (request.getParameter(parameters.nextElement()).trim().length() == 0) {
				proceed = false;
				break;
			}
		}
		if (proceed) {
			if (con != null) {
				Result result = business.customer.Processor.signupUser(username, name, email, con);
				switch (result.getStatus()) {
					case SUCCESS:
						request.setAttribute("status", true);
						request.setAttribute("code", result.getToken());
						request.setAttribute("username", username);
						request.setAttribute("name", name);
						break;
					case EXCEPTION_OCCURED:
						request.setAttribute("status", false);
						request.setAttribute("message", "Internal Server Error!");
						break;
					case FAILED:
						request.setAttribute("status", false);
						request.setAttribute("message", "This username is already taken.");
						break;
				}
			}
		} else {
			request.setAttribute("status", false);
			request.setAttribute("message", "All fields are required!");
		}
		connector.closeConnection(token);
		request.setAttribute("mode", "set-password");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/setup/linkgenerator.jsp");
		dispatcher.forward(request, response);
	}

}
