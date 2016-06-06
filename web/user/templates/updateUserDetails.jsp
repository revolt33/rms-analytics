<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-md-6 col-md-offset-3">
				<div class="panel panel-primary">
								<div class="panel-heading text-center">Update Contact Details
												<button type="button" class="close pull-right" target="overlay" aria-label="Close">
																<span aria-hidden="true">&times;</span>
												</button>
								</div>
								<div class="panel-body">
												<c:url value="Action" var="url"></c:url>
												<form class="form-horizontal" action="${url}" method="POST" post-target="view_discount">
																<div class="form-group">
																				<label class="control-label col-xs-4" for="mobile">Mobile:</label>
																				<div class="col-xs-8">
																								<input class="form-control" type="number" maxlength="10" name="mobile" required/>
																				</div>
																</div>
																<div class="form-group">
																				<label class="control-label col-xs-4" for="email">Email:</label>
																				<div class="col-xs-8">
																								<input class="form-control" type="email" name="email" maxlength="200" required/>
																				</div>
																</div>
																<div class="form-group">
																				<label class="control-label col-xs-4" for="dob">Birthday:</label>
																				<div class="col-xs-8">
																								<input class="form-control" type="date" name="dob" required/>
																				</div>
																</div>
																<input type="hidden" name="param" value="update_details" />
																<div class="form-group">
																				<div class="col-xs-8 col-xs-offset-4">
																								<button type="submit" class="btn btn-group-justified btn-primary">Update</button>
																				</div>
																</div>
												</form>
								</div>
				</div>
</div>