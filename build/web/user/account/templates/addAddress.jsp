<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-md-6 col-md-offset-3">
				<div class="panel panel-primary">
								<div class="panel-heading text-center">Add new address
												<button type="button" class="close pull-right" target="overlay" aria-label="Close">
																<span aria-hidden="true">&times;</span>
												</button>
								</div>
								<div class="panel-body">
												<c:url value="Action" var="url"></c:url>
												<form class="form-horizontal" action="${url}" method="POST" post-target="view_addresses" close="overlay">
																<input type="hidden" name="param" value="add_address" />
																<div class="form-group">
																				<label class="control-label col-xs-4" for="name">Name:</label>
																				<div class="col-xs-8">
																								<input class="form-control" type="text" name="name" maxlength="100" value="${sessionScope.customer_auth_token.name}" placeholder="Name of receiver" required/>
																				</div>
																</div>
																<div class="form-group">
																				<label class="control-label col-xs-4" for="house">House:</label>
																				<div class="col-xs-8">
																								<input class="form-control" type="text" name="house" maxlength="50" placeholder="Your house number" required/>
																				</div>
																</div>
																<div class="form-group">
																				<label class="control-label col-xs-4" for="street">Street:</label>
																				<div class="col-xs-8">
																								<input class="form-control" type="text" name="street" maxlength="200" placeholder="Your street address" required/>
																				</div>
																</div>
																<div class="form-group">
																				<label class="control-label col-xs-4" for="landmark">Landmark:</label>
																				<div class="col-xs-8">
																								<input class="form-control" type="text" name="landmark" maxlength="200" placeholder="Your nearby landmark" required/>
																				</div>
																</div>
																<div class="form-group">
																				<label class="control-label col-xs-4" for="city">City:</label>
																				<div class="col-xs-8">
																								<input class="form-control" type="text" name="city" maxlength="200" placeholder="Your city" required/>
																				</div>
																</div>
																<div class="form-group">
																				<label class="control-label col-xs-4" for="pin">Pin:</label>
																				<div class="col-xs-8">
																								<input class="form-control" type="number" name="pin" maxlength="6" placeholder="Your area PINCODE" required/>
																				</div>
																</div>
																<div class="form-group">
																				<label class="control-label col-xs-4" for="contact">Contact:</label>
																				<div class="col-xs-8">
																								<input class="form-control" type="number" name="contact" maxlength="10" placeholder="Your contact number" required/>
																				</div>
																</div>
																<div class="form-group">
																				<div class="col-xs-8 col-xs-offset-4" >
																								<button type="submit" class="btn btn-primary btn-group-justified">Add Address</button>
																				</div>
																</div>
												</form>
								</div>
				</div>
</div>