<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="nav navbar-inverse">
				<c:url var="project_url" value="/"></c:url>
								<div class="container-fluid">
												<div class="navbar-header">
																<a href="${project_url}" class="navbar-brand">RMS & Analytics Tool</a>
								</div>

								<ul class="nav navbar-nav navbar-right">
												<li class="dropdown">
																<a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-user"></span>${sessionScope.customer_auth_token.name}<span class="caret"></span></a>
																<ul class="dropdown-menu">
																				<c:url var="url" value="/user/account/"></c:url>
																				<li><a href="${url}">Account Settings</a></li>
																				<c:url var="url" value="/user/logout"></c:url>
																				<li><a href="${url}">Logout</a></li>
																</ul>
												</li>
								</ul>
				</div>
</nav>