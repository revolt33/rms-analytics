package employee.manager;

import application.ConnectionToken;
import application.Connector;
import application.Utility;
import business.employee.AuthToken;
import business.employee.Employee;
import business.employee.Processor;
import business.stock.Result;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

/**
 *
 * @author Ayush
 */
public class Action extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String param = request.getParameter("param");
		Connector connector = (Connector) getServletContext().getAttribute("connector");
		ConnectionToken token = connector.getToken();
		switch (param) {
			case "view_emp":
				ArrayList<Employee> empList = Processor.getEmployeeList(((AuthToken) request.getSession().getAttribute("auth_token")), connector.getConnection(token));
				connector.closeConnection(token);
				request.setAttribute("emp_list", empList);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/employee/manager/resource/view-employee.jsp");
				dispatcher.forward(request, response);
				break;
			case "view_emp_detail":
				Employee emp = Processor.getEmpDetail(Integer.parseInt(request.getParameter("emp_id")), ((AuthToken) request.getSession().getAttribute("auth_token")), connector.getConnection(token));
				connector.closeConnection(token);
				request.setAttribute("emp", emp);
				dispatcher = request.getRequestDispatcher("/employee/manager/resource/view-employee-detail.jsp");
				dispatcher.forward(request, response);
				break;
		}

	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getContentType().contains("application/x-www-form-urlencoded")) {
			String param = request.getParameter("param");
			Connector connector = (Connector) getServletContext().getAttribute("connector");
			ConnectionToken token = connector.getToken();
			JSONObject json = new JSONObject();
			Enumeration<String> parameters = (Enumeration<String>) request.getParameterNames();
			boolean proceed = true;
			while (parameters.hasMoreElements()) {
				if (request.getParameter(parameters.nextElement()).trim().length() < 1) {
					proceed = false;
					break;
				}
			}
			char result = '\0';
			if (proceed) {
				switch (param) {
					case "enable_emp":
						result = Processor.alterStatusOfEmployee(Integer.parseInt(request.getParameter("code")), true, ((AuthToken) request.getSession().getAttribute("auth_token")), connector.getConnection(token));
						writeResponse(result, json, response, "Employee enabled successfully!");
						break;
					case "disable_emp":
						result = Processor.alterStatusOfEmployee(Integer.parseInt(request.getParameter("code")), false, ((AuthToken) request.getSession().getAttribute("auth_token")), connector.getConnection(token));
						writeResponse(result, json, response, "Employee disabled successfully!");
						break;
					case "add_emp":
						Connection con = connector.getConnection(token);
						if (Processor.checkUsername(request.getParameter("username"), ((AuthToken) request.getSession().getAttribute("auth_token")), con)) {
							Employee emp = new Employee();
							emp.setName(request.getParameter("name"));
							emp.setUsername(request.getParameter("username"));
							emp.setType(request.getParameter("type").charAt(0));
							emp.setMobile(request.getParameter("mobile"));
							emp.setEmail(request.getParameter("email"));
							emp.setDob(request.getParameter("dob"));
							emp.setGender(request.getParameter("gender").charAt(0));
							emp.setStreet(request.getParameter("street"));
							emp.setColony(request.getParameter("colony"));
							emp.setCity(request.getParameter("city"));
							emp.setLandmark(request.getParameter("landmark"));
							emp.setState(request.getParameter("state"));
							emp.setZip(Integer.parseInt(request.getParameter("zip")));
							emp.setImageExt(Utility.getSuitableExtension(request.getParameter("file-type")));
							Result res = Processor.createEmp(emp, ((AuthToken) request.getSession().getAttribute("auth_token")), con);
							boolean success = false;
							if (res.getStatus() == Processor.SUCCESS) {
								ServletContext context = getServletContext();
								File root = new File(context.getRealPath("/"));
								l:
								for (File file : root.listFiles()) {
									if (file.isDirectory() && file.getName().equals("img")) {
										for (File dir : file.listFiles()) {
											if (dir.isDirectory() && dir.getName().equals("emp")) {
												request.getSession().setAttribute("dir", dir);
												request.getSession().setAttribute("code", new Integer(res.getCode()));
												success = true;
											}
										}
									}
								}
							}
							writeUploadResponse(res.getStatus(), success, json, response, "Employee added successfully", "Item could not be added");
						} else {
							json.put("status", 0);
							json.put("message", "Username is not availbale!");
							PrintWriter writer = response.getWriter();
							writer.print(json);
						}
						connector.closeConnection(token);
						break;
					case "check_username":
						if (Processor.checkUsername(request.getParameter("username"), ((AuthToken) request.getSession().getAttribute("auth_token")), connector.getConnection(token))) {
							json.put("status", 1);
							json.put("message", "Username is availbale!");
						} else {
							json.put("status", 0);
							json.put("message", "Username is not availbale!");
						}
						connector.closeConnection(token);
						PrintWriter writer = response.getWriter();
						writer.print(json);
						break;
				}
			} else {
				json.put("status", 0);
				json.put("message", "Empty values are not allowed!");
				PrintWriter writer = response.getWriter();
				writer.print(json);
			}
		}
	}

	public void writeResponse(char result, JSONObject json, HttpServletResponse response, String successMessage) throws IOException {
		if (result == Processor.SUCCESS) {
			json.put("status", 1);
			json.put("message", successMessage);
		} else {
			json.put("status", 0);
			switch (result) {
				case Processor.EXCEPTION_OCCURED:
					json.put("message", "Internal server error!");
					break;
				case Processor.NOT_MANAGER:
					json.put("message", "Manager level authorization required!");
					break;
				case Processor.UNAUTHORIZED_OPERATION:
					json.put("message", "UNAUTHORIZED OPERATION");
					break;
				default:
					json.put("message", "Request could not be honoured!");
					break;
			}
		}
		PrintWriter writer = response.getWriter();
		writer.print(json);
	}

	public void writeUploadResponse(char result, boolean success, JSONObject json, HttpServletResponse response, String successMessage, String errorMessage) throws IOException {
		if (result == Processor.SUCCESS && success) {
			json.put("status", 1);
			json.put("message", successMessage + " uploading file!");
		} else if (result != Processor.SUCCESS) {
			json.put("status", 2);
			json.put("message", errorMessage + "!");
		} else {
			json.put("status", 0);
			json.put("message", successMessage + " but file could not be uploaded!");
		}
		PrintWriter writer = response.getWriter();
		writer.print(json);
	}
}
