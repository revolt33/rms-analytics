<%@page import="business.customer.AuthToken"%>
<%@page import="java.sql.Connection"%>
<%@page import="application.ConnectionToken"%>
<%@page import="application.Connector"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="business.stock.Processor" %>
<%@page import="business.stock.Category" %>
<%@page import="java.util.ArrayList" %>
<%
	Connector connector = (Connector) application.getAttribute("connector");
	ConnectionToken token = connector.getToken();
	Connection con = connector.getConnection(token);
	if (con != null) {
		ArrayList<Category> list = Processor.getCategories(con);
		pageContext.setAttribute("list", list);
	}
%>

<!DOCTYPE html>
<html>
				<head>
								<title>Home</title>
								<jsp:include page="/resource/include.jsp" />
								<c:url value="/resource/message.js" var="url" ></c:url>
								<script type="text/javascript" src="${url}" defer></script>
								<c:url value="/js/script.js" var="url" ></c:url>
								<script type="text/javascript" src="${url}" defer></script>
								<c:url value="/css/style.css" var="url" ></c:url>
								<link rel="stylesheet" type="text/css" href="${url}">
				</head>
				<body>
								<nav>
												<div class="nav-element left">RMS & Analytics Tool</div>
												<%
				AuthToken authToken = (AuthToken) request.getSession().getAttribute("customer_auth_token");
				if (authToken != null) {
												%>
												<div class="dropdown pull-right">
																<div class="dropdown-toggle nav-element" data-toggle="dropdown">${sessionScope.customer_auth_token.name}<span class="caret"></span></div>
																<ul class="dropdown-menu">
																				<c:url value="/user/account/" var="url"></c:url>
																				<li><a href="${url}">Account Settings</a></li>
																								<c:url value="/user/logout" var="logout_url"></c:url>
																				<li><a href="${logout_url}">Logout</a></li>
																</ul>
												</div>
												<%
			} else {%>
												<div class="nav-element pull-right" id="login-signup">Login / Signup</div>
												<div class="nav-element pull-right"><a href="employee/">Employee Portal</a></div>
												<%}
												%>

								</nav>
								<div id="status-bar" class="alert fade in text-center">
												<a href="#" class="close status-close" data-dismiss="alert" area-label="close">&times;</a><span></span>
								</div>
								<div id="container">	
												<img src="img/r21.jpg" id="img1"/>
												<div id="down" title="Click to view menu"><span class="glyphicon glyphicon-chevron-down"></span></div>
								</div>
								<c:if test="${param.error!=null}">
												<div id="overlay">
												</c:if>
												<c:if test="${param.error==null}">
																<div id="overlay" class="hidden">
																</c:if>
																<div id="form-container" class="container">
																				<div id="close"><span class="glyphicon glyphicon-remove"></span></div>
																				<div class="col-md-5">
																								<div id="signup-form" class="panel panel-primary">
																												<div class="panel-heading text-center">Signup</div>
																												<div class="panel-body">
																																<c:url var="url" value="/user.signup"></c:url>
																																<form class="form-horizontal" action="${url}" method="POST">
																																				<div class="form-group">
																																								<div class="col-xs-12">
																																												<input type="text" class="form-control" name="name" maxlength="50" placeholder="Name" required/>
																																								</div>
																																				</div>
																																				<div class="form-group">
																																								<div class="col-xs-12">
																																												<input type="text" class="form-control" name="username" maxlength="20" placeholder="choose a username" required/>
																																								</div>
																																				</div>
																																				<div class="form-group">
																																								<div class="col-xs-12">
																																												<input type="email" class="form-control" name="email" placeholder="your email-id" required/>
																																								</div>
																																				</div>
																																				<div class="form-group">
																																								<div class="col-xs-12">
																																												<button class="btn btn-primary btn-group-justified" type="submit">Signup</button>
																																								</div>
																																				</div>
																																</form>
																												</div>
																								</div>	
																				</div>
																				<div class="col-md-2">
																								<div id="or" class="text-center">OR</div>
																				</div>
																				<div class="col-md-5">
																								<div id="login-form" class="panel panel-primary">
																												<div class="panel-heading">Already have an account! Log In</div>
																												<div class="panel-body">
																																<c:url value="/user.login" var="url"></c:url>
																																<form class="form-horizontal" method="POST" action="${url}">
																																				<div class="form-group">
																																								<div class="col-xs-12">
																																												<input type="text" class="form-control" name="username" placeholder="username" required/>
																																								</div>
																																				</div>
																																				<div class="form-group">
																																								<div class="col-xs-12">
																																												<input type="password" class="form-control" name="password" placeholder="password" required/>
																																								</div>
																																				</div>
																																				<div class="form-group">
																																								<div class="col-xs-12">
																																												<div class="checkbox">
																																																<label><input type="checkbox" name="remember" />Remember me</label>
																																												</div>
																																								</div>
																																				</div>
																																				<div class="form-group">
																																								<div class="col-xs-12">
																																												<button class="btn btn-primary btn-group-justified" type="submit">Login</button>
																																								</div>
																																				</div>
																																</form>
																												</div>	
																								</div>
																				</div>
																				<c:if test="${param.error!=null}">
																								<h3 class="text-center col-md-12 text-warning">
																												<c:if test="${param.error=='user_login'}">Please login to continue</c:if>
																												<c:if test="${param.error=='user_username'}">Username is incorrect</c:if>
																												<c:if test="${param.error=='user_password'}">Password is incorrect</c:if>
																												<c:if test="${param.error=='user_error'}">Internal server error</c:if>
																								</h3>
																				</c:if>

																</div>
												</div>
												<div id="menu">
																<div id="navigation" class="loose">
																				<p>Categories</p>
																				<ul>
																								<c:set var="first" value="true"></c:set>
																								<c:forEach var="cat" items="${list}">
																												<c:if test="${cat.active}">
																																<c:if test="${!first}">
																																				<li><div class="category" code="${cat.id}">${cat.name}</div></li>
																																				</c:if>
																																				<c:if test="${first}">
																																				<li><div class="category selected" code="${cat.id}">${cat.name}</div></li>
																																								<c:set var="first" value="false"></c:set>
																																				</c:if>
																																</c:if>


																								</c:forEach>
																				</ul>
																</div>
																<div id="showcase">
																				<c:forEach var="cat" items="${list}">
																								<c:if test="${cat.active}">
																												<div class="cat" code="${cat.id}">
																																<p>${cat.name}</p>
																																<%
									int count = 0;
																																%>
																																<c:forEach var="item" items="${cat.itemList}">

																																				<c:if test="${item.active}">
																																								<%
											if (count
												% 2 == 0) {
												out.print("<div class=\"item-wrap\">");
											}
											count++;
																																								%>
																																								<div>
																																												<c:if test="${item.hasUnits}">
																																																<div class="item marginalise" code="${item.firstUnit.id}">
																																																</c:if>
																																																<c:if test="${!item.hasUnits}">
																																																				<div class="item marginalise" code="${item.defaultUnit.id}">
																																																				</c:if>

																																																				<img src="img/item/${item.image}" />
																																																				<div class="item-title">${item.name}</div>
																																																				<c:if test="${item.hasUnits}">
																																																								<c:set value="true" var="first"></c:set>
																																																												<div class="item-units">
																																																												<c:forEach var="unit" items="${item.itemUnits}">
																																																																<c:if test="${!unit.isDefault}">
																																																																				<c:if test="${!first}">
																																																																								<button class="btn btn-sm btn-info item-unit" code="${unit.id}">
																																																																								</c:if>
																																																																								<c:if test="${first}">
																																																																												<button class="btn btn-sm btn-success item-unit" code="${unit.id}">
																																																																																<c:set value="false" var="first"></c:set>
																																																																												</c:if>
																																																																												${unit.name}
																																																																												<span class="badge">${unit.price}</span>
																																																																								</button>
																																																																				</c:if>
																																																																</c:forEach>
																																																								</div>
																																																				</c:if>
																																																				<c:if test="${item.delivery}">
																																																								<c:if test="${item.hasUnits}">
																																																												<div class="add-item has-units">Add to Cart</div>
																																																								</c:if>
																																																								<c:if test="${!item.hasUnits}">
																																																												<div class="add-item">Add to Cart</div>
																																																								</c:if>
																																																				</c:if>
																																																				<c:if test="${!item.delivery}">
																																																								<div class="not-for-delivery">Not for delivery</div>
																																																				</c:if>
																																																				<c:if test="${!item.hasUnits}">
																																																								<div class="item-price" price="${item.defaultUnit.price}">Price: ${item.defaultUnit.price} INR</div>
																																																				</c:if>
																																																				<c:if test="${item.hasUnits}">
																																																								<div class="item-price" price="${item.firstUnit.price}">Price: ${item.firstUnit.price} INR</div>
																																																				</c:if>
																																																</div>
																																												</div>
																																												<%
												if (count
													% 2 == 0) {
													out.print("</div>");
												}
																																												%>
																																								</c:if>
																																				</c:forEach>
																																				<%
										if (count
											% 2 == 1) {
											out.print("</div>");
										}
																																				%>
																																</div>
																												</c:if>
																								</c:forEach>
																				</div>
																				<div id="cart" class="loose">
																								<p>Cart</p>
																								<div id="cart-content">
																								</div>
																								<div id="cart-footer">
																												<div id="total" price="0.0">Total: 0 INR</div>
																												<c:url var="checkout" value="user/checkout/"></c:url>
																												<form method="POST" action="${checkout}" id="checkout">
																																<button class="btn btn-success btn-group-justified">Checkout</button>
																												</form>
																								</div>
																				</div>
																</div>
																</body>
																</html>
