
package employee.sales;

import application.ConnectionToken;
import application.Connector;
import business.employee.AuthToken;
import business.stock.Category;
import business.stock.Processor;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Action extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		String param = request.getParameter("param");
		Connector connector = (Connector) getServletContext().getAttribute("connector");
		ConnectionToken token = connector.getToken();
		AuthToken authToken = (AuthToken) request.getSession().getAttribute("auth_token");
		Connection con = connector.getConnection(token);
		if (authToken != null && con != null) {
			switch (param) {
				case "counter_sales":
					ArrayList<Category> list = Processor.getCategories(con);
					request.setAttribute("categories", list);
					RequestDispatcher dispatcher = request.getRequestDispatcher("/employee/sales/resource/counterSales.jsp");
					dispatcher.forward(request, response);
					break;
			}
			connector.closeConnection(token);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
	}
}
