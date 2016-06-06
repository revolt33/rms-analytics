package filter.user;

import business.customer.CustomerAuthToken;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Login implements Filter {
	
	private FilterConfig filterConfig = null;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {		
		HttpServletRequest httpreq = (HttpServletRequest) request;
		CustomerAuthToken authToken = (CustomerAuthToken) httpreq.getSession().getAttribute("customer_auth_token");
		HttpServletResponse httpres = (HttpServletResponse) response;
		if ( authToken != null ) {
			chain.doFilter(request, response);
		} else {
			httpres.sendRedirect(request.getServletContext().getContextPath()+"/");
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
