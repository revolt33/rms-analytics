package employee;

import java.io.*;
import javax.servlet.http.*;
import business.employee.*;
import business.Status;

public class Redirect extends HttpServlet {
    @Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		AuthToken authToken = (AuthToken) session.getAttribute("employee_auth_token");
		String url ="";
		switch (authToken.getType()) {
			case MANAGER:
				url = "manager/";
				break;
			case STOCK_EMPLOYEE:
				url = "stock/";
				break;
			case SALES_EMPLOYEE:
				url = "sales/";
				break;
		}
		try {
			response.sendRedirect(response.encodeRedirectURL(url));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}