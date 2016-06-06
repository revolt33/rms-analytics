<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome</title>
								<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
								<jsp:include page="/resource/include.jsp" />
								<style type="text/css">
																#mid {
																				margin-top: 25vh;
																				padding: 30px;
																				border-radius: 7px;
																}
								</style>
    </head>
    <body>
        <nav class="nav navbar-inverse">
												<c:url var="project_url" value="/"></c:url>
																<div class="navbar-header">
																				<a href="${project_url}" class="navbar-brand">RMS & Analytics Tool</a>
												</div>
								</nav>
								<div class="container">
												<c:if test="${mode=='set-password'}">
																<c:if test="${status}">
																				<div class="col-md-4 col-md-offset-4 bg-info text-center" id="mid">
																								<p>Welcome ${name}! your account has been setup and your just one step away to experience the delicious treat.</p>
																								<c:url var="link_url" value="/setup/password.jsp?mode=set&username=${username}&token=${code}&type=customer"></c:url>
																								<p>Click <a href="${link_url}">here</a> to set your password.</p>
																				</div>
																</c:if>
																<c:if test="${!status}">
																				<div class="col-md-4 col-md-offset-4 bg-danger text-center" id="mid">
																								<p>${message}</p>
																				</div>
																</c:if>
												</c:if>
								</div>
    </body>
</html>
