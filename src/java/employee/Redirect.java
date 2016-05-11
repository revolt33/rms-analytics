package employee;

import java.io.*;
import javax.servlet.http.*;
import business.employee.*;

public class Redirect extends HttpServlet {
    @Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		AuthToken authToken = (AuthToken) session.getAttribute("auth_token");
		String url ="";
		switch (authToken.getType()) {
			case business.stock.Processor.MANAGER:
				url = "manager/";
				break;
			case business.stock.Processor.STOCK_EMPLOYEE:
				url = "stock/";
				break;
			case business.stock.Processor.SALES_EMPLOYEE:
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