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
								<jsp:include page="/user/templates/header.jsp" />
												<div class="container-fluid" id="outer-container">
																<div class="col-md-2" id="taskbar">
																				<div class="bg-info text-center">Taskbar</div>
																				<div class="list-group">
																				<c:url var="list_url" value="Action"></c:url>
																				<a href="${list_url}" class="list-group-item load-link-with-param" param="view_user_details" target="content">Your Info</a>
																				<c:url var="list_url" value="Action"></c:url>
																				<a href="${list_url}" class="list-group-item load-link-with-param" param="view_addresses" target="content">Address</a>
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
