package filter.employee.manager;

import business.employee.AuthToken;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

public class Observer implements Filter {
	private FilterConfig filterConfig = null;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest)request).getSession();
		AuthToken authToken = (AuthToken) session.getAttribute("auth_token");
		if ( authToken.getType() == business.employee.Processor.MANAGER ) {
			chain.doFilter(request, response);
		} else {
			HttpServletResponse httpRes = (HttpServletResponse)response;
			httpRes.sendRedirect(httpRes.encodeRedirectURL("../"));
		}
	}

	@Override
	public void destroy() {		
	}

	@Override
	public void init(FilterConfig fc) throws ServletException {
		filterConfig = fc;
	}
	
}
