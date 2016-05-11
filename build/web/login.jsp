<!DOCTYPE html>
<html>
				<head>
								<title>Corporate Login</title>
								<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
								<jsp:include page="/resource/include.jsp"></jsp:include>
												<style type="text/css">
																#mid {
																				margin-top: 25vh;
																}
												</style>
								</head>
								<body>
												<nav class="nav navbar-inverse">
																<div class="navbar-header">
																<c:url var="url" value="/index.jsp"></c:url>
																<a href="${url}" class="navbar-brand">RMS Analytics Tool</a>
												</div>
								</nav>
								<div class="container">
												<div class="col-md-4 col-md-offset-4">
																<div class="panel panel-primary" id="mid">
																				<div class="panel-heading">Employee Login Portal</div>
																				<div class="panel-body">
																								<c:url var="url" value="/employee.login"></c:url>
																								<form class="form-horizontal" action="${url}" method="POST">
																												<div class="form-group">
																																<label for="username" class="col-sm-3 control-label">Username</label>
																																<div class="col-sm-9">
																																				<input type="text" class="form-control" placeholder="username" name="username" required="" />
																																</div>
																												</div>
																												<div class="form-group">
																																<label for="password" class="col-sm-3 control-label">Password</label>
																																<div class="col-sm-9">
																																				<input type="password" class="form-control" placeholder="password" name="password" required=""/>
																																</div>
																												</div>
																												<div class="form-group">
																																<div class="col-sm-9 col-sm-offset-3">
																																				<div class="checkbox">
																																								<label><input type="checkbox" name="remember" />Remember me</label>
																																				</div>
																																</div>
																												</div>
																												<div class="form-group">
																																<div class="col-sm-offset-3 col-sm-9">
																																				<button class="btn btn-primary btn-group-justified">Login</button>
																																</div>
																												</div>
																								</form>
																				</div>
																</div>
																<c:if test="${param.error!=null}">
																				<div class="alert alert-danger fade in">
																								<a href="#" class="close" data-dismiss="alert" area-label="close">&times;</a>
																								<c:if test="${param.error==1}" >Please Login to Continue!</c:if>
																								<c:if test="${param.error==2}" >Incorrect Username!</c:if>
																								<c:if test="${param.error==3}" >Incorrect Password!</c:if>
																								<c:if test="${param.error==4}" >Internal Server Error!</c:if>
																								<c:if test="${param.error==5}" >Username is not active!</c:if>
																								</div>
																</c:if>

												</div>
								</div>
				</body>
</html>