package filter.user;

import application.ConnectionToken;
import application.Connector;
import business.customer.AuthToken;
import business.customer.Processor;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login implements Filter {

	private FilterConfig filterConfig = null;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
		HttpServletRequest httpreq = (HttpServletRequest) request;
		AuthToken authToken = (AuthToken) httpreq.getSession().getAttribute("customer_auth_token");
		HttpServletResponse httpres = (HttpServletResponse) response;
		Cookie remember = null, auth_token = null, id = null;
		if (httpreq.getCookies() != null) {
			for (Cookie cookie : httpreq.getCookies()) {
				switch (cookie.getName()) {
					case "remember_customer":
						remember = cookie;
						break;
					case "customer_auth_token":
						auth_token = cookie;
						break;
					case "customer_id":
						id = cookie;
						break;
				}
			}
		}
		boolean authorized = false;
		if (authToken != null) {
			Connector connector = (Connector) request.getServletContext().getAttribute("connector");
			ConnectionToken token = connector.getToken();
			authorized = Processor.authorize(authToken, false, connector.getConnection(token));
			connector.closeConnection(token);
		} else if (remember != null && remember.getValue().equals("y") && id != null && auth_token != null) {
			authToken = new AuthToken();
			authToken.setAuthToken(auth_token.getValue());
			authToken.setId(Integer.parseInt(id.getValue()));
			Connector connector = (Connector) request.getServletContext().getAttribute("connector");
			ConnectionToken token = connector.getToken();
			authorized = Processor.authorize(authToken, true, connector.getConnection(token));
			connector.closeConnection(token);
			if (authorized) {
				httpreq.getSession().setAttribute("customer_auth_token", authToken);
			} else {
				authToken = null;
			}
		}
		if (authorized) {
			forward(httpreq, httpres, chain);
		} else {
			if (httpreq.getCookies() != null) {
				for (Cookie cookie : httpreq.getCookies()) {
					cookie.setMaxAge(0);
					httpres.addCookie(cookie);
				}
			}
			httpreq.getSession().invalidate();
			if (httpreq.getRequestURL().indexOf("user") > 0) {
				try {
					httpres.sendRedirect(httpres.encodeRedirectURL(request.getServletContext().getContextPath() + "/?error=user_login"));
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			} else {
				forward(httpreq, httpres, chain);
				
			}
		}
	}
	
	void forward( HttpServletRequest request, HttpServletResponse response, FilterChain chain ) {
		try {
			chain.doFilter(request, response);
		} catch (IOException | ServletException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

}
