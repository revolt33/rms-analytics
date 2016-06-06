<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
								<jsp:include page="/resource/include.jsp" />
								<c:url var="url" value="style.css"></c:url>
								<link rel="stylesheet" type="text/css" href="${url}" />
								<c:url var="url" value="script.js"></c:url>
								<script type="text/javascript" src="${url}" defer></script>
        <title>Account Settings</title>
    </head>
    <body page="user">
								<div id="scroll-top"><span class="glyphicon glyphicon-circle-arrow-up" title="Scroll to top"></span></div>
								<div id="status-bar" class="alert fade in text-center">
												<a href="#" class="close status-close" data-dismiss="alert" area-label="close">&times;</a><span></span>
								</div>
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
																												<c:url var="logout_url" value="/user/logout"></c:url>
																																<li><a href="${logout_url}">Logout</a></li>
																												</ul>
																								</li>
																				</ul>
																</div>
												</nav>
												<div class="container" id="outer-container">
																<div class="col-md-3" id="taskbar">
																				<div class="bg-info text-center">Taskbar</div>
																				<div class="list-group">
																				<c:url var="list_url" value="Action"></c:url>
																				<a href="${list_url}" class="list-group-item load-link-with-param" param="view_user_details" target="content">Your Info</a>
																</div>
												</div>
												<div class="col-md-9">
																<div id="content"></div>
																<div id="overlay" class="disable">
																				<div id="dialog" class="reloadable"></div>
																				<div id="load-screen"></div>
																</div>
												</div>
								</div>
    </body>
</html>
