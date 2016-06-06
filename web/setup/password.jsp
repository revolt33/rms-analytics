<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
								<c:if test="${param.mode=='set'}">
												<title>Set Password</title>
								</c:if>
								<c:if test="${param.mode=='reset'}">
												<title>Reset Password</title>
								</c:if>
								<jsp:include page="/resource/include.jsp" />
								<style type="text/css">
												#mid {
																margin-top: 25vh;
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
								<div class="container-fluid">
												<div class="col-md-4 col-md-offset-4" id="mid">
																<div class="panel panel-primary">
																				<div class="panel-heading text-center">Change Password</div>
																				<div class="panel-body">
																								<c:url var="submit_url" value="/setup/password"></c:url>
																								<form class="form-horizontal" action="${submit_url}" method="POST" onsubmit="return verify()">
																												<div class="form-group">
																																<label class="col-xs-5 control-label" for="password" >New Password:</label>
																																<div class="col-xs-7">
																																				<input class="form-control" type="password" maxlength="20" id="password" name="password" required />
																																</div>
																												</div>
																												<div class="form-group">
																																<label class="col-xs-5 control-label" for="re-password" >Repeat Password:</label>
																																<div class="col-xs-7">
																																				<input class="form-control" type="password" maxlength="20" id="re-password" name="re-password" required />
																																</div>
																												</div>
																												<input type="hidden" name="username" value="${param.username}" />
																												<input type="hidden" name="fp_token" value="${param.token}" />
																												<input type="hidden" name="param" value="set" />
																												<div class="form-group">
																																<div class="col-xs-7 col-xs-offset-5">
																																				<button type="submit" class="btn btn-primary btn-group-justified">Set Password</button>
																																</div>
																												</div>
																								</form>
																				</div>
																</div>
																<c:if test="${param.error!=null}">
																				<div class="alert alert-danger fade in">
																								<a href="#" class="close" data-dismiss="alert" area-label="close">&times;</a>
																								${param.error}
																				</div>
																				</c:if>
																</div>
												</div>
    </body>
</html>
<script>
				function verify() {
								var pass = document.getElementById('password').value;
								var repeat = document.getElementById('re-password').value;
								if (pass != repeat) {
												alert('Both passwords should be same.');
												return false;
								} else
												return true;
				}
</script>