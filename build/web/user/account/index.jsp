<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
				if ( request.getParameter("view_tab") != null ) {
								response.addCookie(new Cookie("link_user", request.getParameter("view_tab")));
				}
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
								<jsp:include page="/resource/include.jsp" />
								<c:url var="url" value="/resource/message.js"></c:url>
								<script type="text/javascript" src="${url}" defer></script>
								<c:url var="url" value="style.css"></c:url>
								<link rel="stylesheet" type="text/css" href="${url}" />
								<c:url var="url" value="/resource/confirmModal.js"></c:url>
								<script type="text/javascript" src="${url}" defer></script>
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
																				<a href="${list_url}" class="list-group-item load-link-with-param" param="view_addresses" target="content">Address</a>
																				<a href="${list_url}" class="list-group-item load-link-with-param" param="view_orders" target="content">Orders</a>
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
								<div class="modal fade" tabindex="-1" role="dialog" id="modal" data-backdrop="static">
												<div class="modal-dialog">
																<div class="modal-content">
																				<div class="modal-header">
																								<h4 class="modal-title text-center"></h4>
																				</div>
																				<div class="modal-body">
																				</div>
																				<div class="modal-footer">
																				</div>
																</div>
												</div>
								</div>
    </body>
</html>
