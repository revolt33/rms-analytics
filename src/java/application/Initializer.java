package application;

import javax.servlet.*;

public class Initializer implements ServletContextListener {
	
	public void contextInitialized (ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		Connector connector = new Connector(context.getInitParameter("username"), context.getInitParameter("password"));
		context.setAttribute("connector", connector);
	}

	public void contextDestroyed (ServletContextEvent event) {
		Connector connector = (Connector)((event.getServletContext()).getAttribute("connector"));
		connector.closeAll();
	}
}