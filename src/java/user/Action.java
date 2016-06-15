package user;

import application.ConnectionToken;
import application.Connector;
import business.customer.Customer;
import business.customer.AuthToken;
import business.customer.Processor;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

public class Action extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String param = request.getParameter("param");
		Connector connector = (Connector) getServletContext().getAttribute("connector");
		ConnectionToken token = connector.getToken();
		AuthToken authToken = (AuthToken) request.getSession().getAttribute("customer_auth_token");
		Connection con = connector.getConnection(token);
		if ( authToken != null ) {
			if ( con != null ) {
				switch (param) {
					case "view_user_details":
						Customer customer = Processor.getUserDetail(authToken, con);
						request.setAttribute("user_details", customer);
						RequestDispatcher dispatcher = request.getRequestDispatcher("/user/account/resources/customerDetails.jsp");
						dispatcher.forward(request, response);
						break;
					case "view_addresses":
						dispatcher = request.getRequestDispatcher("/user/account/resources/address.jsp");
						dispatcher.forward(request, response);
						break;
				}
			}
		}
		connector.closeConnection(token);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String param = request.getParameter("param");
		Connector connector = (Connector) getServletContext().getAttribute("connector");
		ConnectionToken token = connector.getToken();
		AuthToken authToken = (AuthToken) request.getSession().getAttribute("customer_auth_token");
		Connection con = connector.getConnection(token);
		if ( authToken != null ) {
			if ( con != null ) {
				switch (param) {
					case "update_details":
						String email = request.getParameter("email");
						long mobile = Long.parseLong(request.getParameter("mobile"));
						String dob = request.getParameter("dob");
						if ( Processor.updateUserDetails(dob, email, mobile, authToken, con) ) {
							writeResponse(response, "Contacts updated Successfully!");
						} else {
							writeErrorResponse(response, "Contacts could not be updated!");
						}
						break;
				}
			}
		}
		connector.closeConnection(token);
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
