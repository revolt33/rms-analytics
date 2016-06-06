package application;

import javax.servlet.*;

public class Initializer implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			Class.forName("com.mysql.jdbc.Driver");

		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		ServletContext context = event.getServletContext();
		Connector connector = new Connector(context.getInitParameter("username"), context.getInitParameter("password"));
		context.setAttribute("connector", connector);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		Connector connector = (Connector) ((event.getServletContext()).getAttribute("connector"));
		connector.closeAll();
	}
}
