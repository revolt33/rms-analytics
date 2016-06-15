package filter.employee;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import business.employee.*;
import application.*;
import business.Status;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class Login implements Filter {

	private FilterConfig fc;

	@Override
	public void init(FilterConfig fc) {
		this.fc = fc;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
		HttpServletRequest httpreq = (HttpServletRequest) request;
		AuthToken authToken = (AuthToken) httpreq.getSession().getAttribute("employee_auth_token");
		HttpServletResponse httpres = (HttpServletResponse) response;
		Cookie remember = null, auth_token = null, id = null, type = null;
		if (httpreq.getCookies() != null) {
			for (Cookie cookie : httpreq.getCookies()) {
				switch (cookie.getName()) {
					case "remember_employee":
						remember = cookie;
						break;
					case "employee_auth_token":
						auth_token = cookie;
						break;
					case "employee_id":
						id = cookie;
						break;
					case "employee_type":
						type = cookie;
						break;
				}
			}
		}
		boolean authorized = false;
		try {
			if (authToken != null) {
				Connector connector = (Connector) request.getServletContext().getAttribute("connector");
				ConnectionToken token = connector.getToken();
				authorized = Processor.authorize(authToken, false, connector.getConnection(token));
				connector.closeConnection(token);
			} else if (remember != null && remember.getValue().equals("y") && id != null && type != null && auth_token != null) {
				authToken = new AuthToken();
				authToken.setAuthToken(auth_token.getValue());
				authToken.setId(Integer.parseInt(id.getValue()));
				authToken.setType(Status.parseEmployee(type.getValue().charAt(0)));
				Connector connector = (Connector) request.getServletContext().getAttribute("connector");
				ConnectionToken token = connector.getToken();
				authorized = Processor.authorize(authToken, true, connector.getConnection(token));
				connector.closeConnection(token);
				if (authorized) {
					httpreq.getSession().setAttribute("employee_auth_token", authToken);
				} else {
					authToken = null;
				}
			}
			if (authorized) {
				chain.doFilter(request, response);
			} else {
				if (httpreq.getCookies() != null) {
					for (Cookie cookie : httpreq.getCookies()) {
						cookie.setMaxAge(0);
						httpres.addCookie(cookie);
					}
				}
				if ( authToken != null )
					httpres.sendRedirect(httpres.encodeRedirectURL(request.getServletContext().getContextPath())+"/employee/");
				else
					httpres.sendRedirect(httpres.encodeRedirectURL(request.getServletContext().getContextPath()+"/login.jsp?error=1"));
			}
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {

	}
}
