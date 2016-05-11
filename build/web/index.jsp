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
												<div class="nav-element right" id="login-signup">Login / Signup</div>
												<div class="nav-element right"><a href="employee/">Employee Portal</a></div>

								</nav>
								<div id="container">	
												<img src="img/r21.jpg" id="img1"/>
												<div id="down" title="Click to view menu"><span class="glyphicon glyphicon-chevron-down"></span></div>
								</div>
								<div id="overlay">
												<div id="form-container">
																<div id="close"><span class="glyphicon glyphicon-remove"></span></div>
																<div id="signup-form" class="panel panel-primary">
																				<div class="panel-heading">Signup for Delicious treat!</div>
																				<div class="panel-body">
																								<input type="text" class="form-control marginalise" name="name" placeholder="Name">
																								<input type="text" class="form-control marginalise" name="userid" placeholder="choose a username">
																								<input type="text" class="form-control marginalise" name="email" placeholder="your email-id">
																								<div class="btn-group btn-group-justified marginalise" role="group">
																												<div class="btn-group" role="group">
																																<button type="button" class="btn btn-primary" id="signup">Signup</button>
																												</div>
																								</div>
																				</div>
																</div>
																<div id="or">OR</div>
																<div id="login-form" class="panel panel-primary">
																				<div class="panel-heading">Already have an account! Log In</div>
																				<div class="panel-body">
																								<input type="text" class="form-control marginalise" name="userid" placeholder="username">
																								<input type="password" class="form-control marginalise" name="password" placeholder="password">
																								<div class="marginalise"><input type="checkbox" id="remember" />Remember me</div>
																								<div class="btn-group btn-group-justified marginalise" role="group">
																												<div class="btn-group" role="group">
																																<button type="button" class="btn btn-primary" id="login">LogIn</button>
																												</div>
																								</div>
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
										if (count % 2 == 0) {
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
										if (count % 2 == 0) {
											out.print("</div>");
										}
																																				%>
																																</c:if>
																												</c:forEach>
																												<%
								if (count % 2 == 1) {
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
