<%@page import="business.Status"%>
<%@page import="business.employee.AuthToken"%>
<!DOCTYPE html>
<html>
				<head>
								<title>Stock Page</title>
								<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
								<jsp:include page="/resource/include.jsp" />
								<%@page import="business.stock.Processor" %>
								<%@page import="business.employee.AuthToken" %>
								<c:url var="plot_js_url" value="/jqplot/jquery.jqplot.min.js"></c:url>
								<script type="text/javascript" src="${plot_js_url}" defer></script>
								<c:url var="jqplot_date_js_url" value="/jqplot/plugins/jqplot.dateAxisRenderer.min.js"></c:url>
								<script type="text/javascript" src="${jqplot_date_js_url}" defer></script>
								<c:url var="jqplot_highlighter_js_url" value="/jqplot/plugins/jqplot.highlighter.min.js"></c:url>
								<script type="text/javascript" src="${jqplot_highlighter_js_url}" defer></script>
								<c:url var="jqplot_cursor_js_url" value="/jqplot/plugins/jqplot.cursor.min.js"></c:url>
								<script type="text/javascript" src="${jqplot_cursor_js_url}" defer></script>
								<c:url var="jqplot_css_url" value="/jqplot/jquery.jqplot.min.css"></c:url>
								<link rel="stylesheet" type="text/css" href="${jqplot_css_url}" />
								<c:url var="style_url" value="style.css"></c:url>
								<link rel="stylesheet" type="text/css" href="${style_url}" />
								<c:url var="style_url" value="../utility.css"></c:url>
								<link rel="stylesheet" type="text/css" href="${style_url}" />
								<c:url var="script_url" value="../utility.js"></c:url>
								<script src="${script_url}" type="text/javascript" defer></script>
								<c:url var="script_url" value="script.js"></c:url>
								<script src="${script_url}" type="text/javascript" defer></script>
				</head>
				<body page="stock">
								<div id="scroll-top"><span class="glyphicon glyphicon-circle-arrow-up" title="Scroll to top"></span></div>
								<div id="status-bar" class="alert fade in text-center">
												<a href="#" class="close status-close" data-dismiss="alert" area-label="close">&times;</a><span></span>
								</div>
								<div id="top-overlay">
												<div class="reloadable col-md-4 col-md-offset-4" id="top-dialog"></div>
								</div>
								<nav class="nav navbar-inverse">
												<c:url var="project_url" value="/"></c:url>
																<div class="container-fluid">
																				<div class="navbar-header">
																								<a href="${project_url}" class="navbar-brand">RMS & Analytics Tool</a>
																</div>
																<ul class="nav navbar-nav navbar-right">
																				<%
						if (((AuthToken) (session.getAttribute("auth_token"))).getType() == Status.MANAGER) {
																				%>
																				<c:url var="manager_url" value="../manager/"></c:url>
																				<li><a href="${manager_url}">Manager</a></li>
																								<c:url var="sales_url" value="../sales/"></c:url>
																				<li><a href="${sales_url}">Sales</a></li>
																								<c:url var="stock_url" value=""></c:url>
																				<li class="active"><a href="${stock_url}">Stock</a></li>
																								<%
							}
																								%>
																				<li class="dropdown">
																								<a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-user"></span>${sessionScope.auth_token.name}<span class="caret"></span></a>
																								<ul class="dropdown-menu">
																												<li><a href="#">Settings</a></li>
																																<c:url var="logout_url" value="/employee/logout"></c:url>
																												<li><a href="${logout_url}">Logout</a></li>
																								</ul>
																				</li>
																</ul>
												</div>
								</nav>
								<div class="container-fluid" id="outer-container">
												<div class="col-md-2" id="taskbar">
																<div class="bg-info text-center">Taskbar</div>
																<div class="list-group">
																				<c:url var="list_url" value="Action"></c:url>
																				<a href="${list_url}" class="list-group-item load-link-with-param" param="view_item_display" target="content">Item Display</a>
																				<a href="${list_url}" class="list-group-item load-link-with-param" param="view_ingredient_display" target="content">Ingredient Display</a>
																				<a href="${list_url}" class="list-group-item load-link-with-param" param="view_relations" target="content">View Relations</a>
																				<a href="${list_url}" class="list-group-item load-link-with-param" param="view_item_config" target="content">Configure Items</a>
																				<a href="#" class="list-group-item">Stock</a>
																				<a href="#" class="list-group-item">Stock</a>
																</div>
												</div>
												<div class="col-md-10">
																<div id="content"></div>
																<div id="overlay" class="disable">
																				<div id="dialog" class="reloadable"></div>
																				<div id="load-screen"></div>
																</div>
												</div>
								</div>
				</body>
</html>