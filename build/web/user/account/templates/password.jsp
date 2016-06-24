<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-md-6 col-md-offset-3">
				<div class="panel panel-primary">
								<div class="panel-heading text-center">Change Your Password
												<button type="button" class="close pull-right" target="overlay" aria-label="Close">
																<span aria-hidden="true">&times;</span>
												</button>
								</div>
								<div class="panel-body">
												<c:url value="Action" var="url"></c:url>
												<form class="form-horizontal has-password" action="${url}" method="POST" post-target="view_user_details" close="overlay">
																<input type="hidden" name="param" value="change_password" />
																<div class="form-group">
																				<label class="col-xs-4 control-label">Old Password:</label>
																				<div class="col-xs-8">
																								<input class="form-control" type="password" name="old" required />
																				</div>
																</div>
																<div class="form-group">
																				<label class="col-xs-4 control-label" >New Password:</label>
																				<div class="col-xs-8">
																								<input class="form-control" type="password" name="new" required/>
																				</div>
																</div>
																<div class="form-group">
																				<label class="col-xs-4 control-label" >Repeat Password:</label>
																				<div class="col-xs-8">
																								<input class="form-control" type="password" name="repeat" required/>
																				</div>
																</div>
																<div class="form-group">
																				<div class="col-xs-8 col-xs-offset-4">
																								<button class="btn btn-primary btn-group-justified">Change Password</button>
																				</div>
																</div>
												</form>
								</div>
				</div>
</div>