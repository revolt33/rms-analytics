package user;

import application.ConnectionToken;
import application.Connector;
import business.customer.AuthToken;
import business.customer.Processor;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Address extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AuthToken authToken = (AuthToken) request.getSession().getAttribute("customer_auth_token");
		Connector connector = (Connector) getServletContext().getAttribute("connector");
		ConnectionToken token = connector.getToken();
		Connection con = connector.getConnection(token);
		if (authToken != null) {
			if (con != null) {
				ArrayList<business.customer.Address> address = Processor.getAddressList(authToken, con);
				request.setAttribute("addresses", address);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/user/checkout/resources/browseAddress.jsp");
				dispatcher.forward(request, response);
				connector.closeConnection(token);
			}
		}
	}
}
