<%@page import="business.customer.CustomerAuthToken"%>
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
								<script type="text/javascript" src="js/script.js" defer></script>
								<link rel="stylesheet" type="text/css" href="css/style.css">
				</head>
				<body>
								<nav>
												<div class="nav-element left">RMS & Analytics Tool</div>
												<%
				CustomerAuthToken authToken = (CustomerAuthToken) request.getSession().getAttribute("customer_auth_token");
				if (authToken != null) {
												%>
												<div class="dropdown pull-right">
																<div class="dropdown-toggle nav-element" data-toggle="dropdown">${sessionScope.customer_auth_token.name}<span class="caret"></span></div>
																<ul class="dropdown-menu">
																				<c:url value="/user/" var="url"></c:url>
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
								<div id="overlay">
												<div id="form-container">
																<div id="close"><span class="glyphicon glyphicon-remove"></span></div>
																<div id="signup-form" class="panel panel-primary">
																				<div class="panel-heading text-center">Signup</div>
																				<div class="panel-body">
																								<c:url var="url" value="/user.signup"></c:url>
																								<form class="form-horizontal" action="${url}" method="POST">
																												<div class="form-group col-xs-12">
																																<input type="text" class="form-control" name="name" maxlength="50" placeholder="Name" required/>
																												</div>
																												<div class="form-group col-xs-12">
																																<input type="text" class="form-control" name="username" maxlength="20" placeholder="choose a username" required/>
																												</div>
																												<div class="form-group col-xs-12">
																																<input type="email" class="form-control" name="email" placeholder="your email-id" required/>
																												</div>
																												<div class="form-group col-xs-12">
																																<button class="btn btn-primary btn-group-justified" type="submit">Signup</button>
																												</div>
																								</form>
																				</div>
																</div>
																<div id="or">OR</div>
																<div id="login-form" class="panel panel-primary">
																				<div class="panel-heading">Already have an account! Log In</div>
																				<div class="panel-body">
																								<c:url value="/user.login" var="url"></c:url>
																								<form class="form-horizontal" method="POST" action="${url}">
																												<div class="form-group col-sm-12">
																																<input type="text" class="form-control" name="username" placeholder="username" required/>
																												</div>
																												<div class="form-group col-sm-12">
																																<input type="password" class="form-control" name="password" placeholder="password" required/>
																												</div>
																												<div class="form-group col-sm-12">
																																<div class="checkbox">
																																				<label><input type="checkbox" name="remember" />Remember me</label>
																																</div>
																												</div>
																												<div class="form-group col-sm-12">
																																<button class="btn btn-primary btn-group-justified" type="submit">Login</button>
																												</div>
																								</form>
																				</div>	
																</div>
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
																																								<div class="item marginalise" code="${item.id}">
																																												<img src="img/item/${item.image}" />
																																												<div class="item-title">${item.name}</div>
																																												<c:if test="${item.hasUnits}">
																																																<div class="item-units">
																																																				<c:forEach var="unit" items="${item.itemUnits}">
																																																								<c:if test="${!unit.isDefault}">
																																																												<button class="btn btn-sm btn-info">${unit.name}
																																																																<span class="badge">${unit.price}</span>
																																																												</button>
																																																								</c:if>
																																																				</c:forEach>
																																																</div>
																																												</c:if>
																																												<c:if test="${item.delivery}">
																																																<div class="add-item">Add to Cart</div>
																																												</c:if>
																																												<c:if test="${!item.delivery}">
																																																<div class="not-for-delivery">Not for delivery</div>
																																												</c:if>
																																												<c:if test="${!item.hasUnits}">
																																																<div class="item-price" price="${item.defaultUnit.price}">Price: ${item.defaultUnit.price} INR</div>
																																												</c:if>
																																												<c:if test="${item.hasUnits}">
																																																<div class="item-price" price="">Price:  INR</div>
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
																				<a id="checkout" class="success" href="#">Checkout</a>
																</div>
												</div>
								</div>
				</body>
</html>
