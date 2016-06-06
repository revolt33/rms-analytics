package filter.employee.stock;

import business.Status;
import business.employee.AuthToken;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ayush
 */
public class Observer implements Filter {

	private FilterConfig filterConfig = null;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession();
		AuthToken authToken = (AuthToken) session.getAttribute("auth_token");
		if (authToken.getType() == Status.STOCK_EMPLOYEE || authToken.getType() == Status.MANAGER) {
			chain.doFilter(request, response);
		} else {
			HttpServletResponse httpRes = (HttpServletResponse) response;
			httpRes.sendRedirect(httpRes.encodeRedirectURL("../"));
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
