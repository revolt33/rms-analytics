package filter.employee;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import business.employee.*;
import application.*;
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
		AuthToken authToken = (AuthToken) httpreq.getSession().getAttribute("auth_token");
		HttpServletResponse httpres = (HttpServletResponse) response;
		Cookie remember = null, auth_token = null, id = null, type = null;
		if (httpreq.getCookies() != null) {
			for (Cookie cookie : httpreq.getCookies()) {
				switch (cookie.getName()) {
					case "remember":
						remember = cookie;
						break;
					case "auth_token":
						auth_token = cookie;
						break;
					case "id":
						id = cookie;
						break;
					case "type":
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
				authToken.setType(type.getValue().charAt(0));
				Connector connector = (Connector) request.getServletContext().getAttribute("connector");
				ConnectionToken token = connector.getToken();
				authorized = Processor.authorize(authToken, true, connector.getConnection(token));
				connector.closeConnection(token);
				if (authorized) {
					httpreq.getSession().setAttribute("auth_token", authToken);
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
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {

	}
}
