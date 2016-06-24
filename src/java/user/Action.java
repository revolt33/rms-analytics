package user;

import application.ConnectionToken;
import application.Connector;
import business.Status;
import business.customer.Address;
import business.customer.Customer;
import business.customer.AuthToken;
import business.customer.Processor;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
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
		if (authToken != null) {
			if (con != null) {
				switch (param) {
					case "view_user_details":
						Customer customer = Processor.getUserDetail(authToken, con);
						request.setAttribute("user_details", customer);
						RequestDispatcher dispatcher = request.getRequestDispatcher("/user/account/resources/customerDetails.jsp");
						dispatcher.forward(request, response);
						break;
					case "view_addresses":
						ArrayList<Address> list = Processor.getAddressList(authToken, con);
						request.setAttribute("addresses", list);
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
		if (authToken != null) {
			if (con != null) {
				switch (param) {
					case "update_details":
						String email = request.getParameter("email");
						long mobile = Long.parseLong(request.getParameter("mobile"));
						String dob = request.getParameter("dob");
						if (Processor.updateUserDetails(dob, email, mobile, authToken, con)) {
							writeResponse(response, "Contacts updated Successfully!");
						} else {
							writeErrorResponse(response, "Contacts could not be updated!");
						}
						break;
					case "add_address":
						Address address = new Address();
						address.setName(request.getParameter("name"));
						address.setHouse(request.getParameter("house"));
						address.setStreet(request.getParameter("street"));
						address.setCity(request.getParameter("city"));
						address.setPin(Integer.parseInt(request.getParameter("pin")));
						address.setContact(Long.parseLong(request.getParameter("contact")));
						address.setLandmark(request.getParameter("landmark"));
						if (Processor.addAddress(address, authToken, con)) {
							writeResponse(response, "Address added successfully!");
						} else {
							writeErrorResponse(response, "Address could not be added!");
						}
						break;
					case "edit_address":
						address = new Address();
						address.setId(Integer.parseInt(request.getParameter("id")));
						address.setName(request.getParameter("name"));
						address.setHouse(request.getParameter("house"));
						address.setStreet(request.getParameter("street"));
						address.setCity(request.getParameter("city"));
						address.setPin(Integer.parseInt(request.getParameter("pin")));
						address.setContact(Long.parseLong(request.getParameter("contact")));
						address.setLandmark(request.getParameter("landmark"));
						if (Processor.editAddress(authToken, address, con)) {
							writeResponse(response, "Address changed successfully!");
						} else {
							writeErrorResponse(response, "Address could not be changed!");
						}
						break;
					case "delete_address":
						if (Processor.deleteAddress(authToken, Integer.parseInt(request.getParameter("code")), con)) {
							writeResponse(response, "Address deleted successfully!");
						} else {
							writeErrorResponse(response, "Adress could not be deleted!");
						}
						break;
					case "change_password":
						String old = request.getParameter("old").trim();
						String newPass = request.getParameter("new").trim();
						String repeat = request.getParameter("repeat").trim();
						if (newPass.equals(repeat)) {
							if (newPass.length() > 7) {
								Status status = Processor.changePassword(authToken, old, newPass, con);
								switch (status) {
									case SUCCESS:
										writeResponse(response, "Password changed successfully!");
										break;
									case USER_UNAVAILABLE:
										writeErrorResponse(response, "Invalid user!");
										break;
									case INCORRECT_PASSWORD:
										writeErrorResponse(response, "Incorrect password!");
										break;
									default:
										writeErrorResponse(response, "Internal server error!");
								}
							} else {
								writeErrorResponse(response, "Minimum 8 characters password required!");
							}
						} else {
							writeErrorResponse(response, "Passwords do not match!");
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
