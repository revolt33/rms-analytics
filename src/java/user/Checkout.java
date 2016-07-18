package user;

import application.ConnectionToken;
import application.Connector;
import business.Status;
import business.customer.Cart;
import business.customer.CartItem;
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

public class Checkout extends HttpServlet {


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AuthToken authToken = (AuthToken) request.getSession().getAttribute("customer_auth_token");
		Connector connector = (Connector) getServletContext().getAttribute("connector");
		ConnectionToken token = connector.getToken();
		Connection con = connector.getConnection(token);
		if ( authToken != null ) {
			if ( con != null ) {
				String[] units = request.getParameterValues("unit[]");
				ArrayList<CartItem> list = new ArrayList<>();
				for ( String unit: units ) {
					CartItem item = new CartItem();
					String[] parts = unit.split(",");
					item.setUnit(Integer.parseInt(parts[0]));
					item.setQuantity(Integer.parseInt(parts[1]));
					list.add(item);
				}
				Cart cart = Processor.checkout(authToken, list, null, Status.UNKNOWN, 0, con);
				request.setAttribute("cart", cart);
				RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
				dispatcher.forward(request, response);
				connector.closeConnection(token);
			}
		}
	}

}
