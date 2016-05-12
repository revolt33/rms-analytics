<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-sm-6 col-sm-offset-3">
				<div class="panel panel-primary">
								<div class="panel-heading">Add Employee
												<button type="button" class="close pull-right" target="overlay" aria-label="Close">
																<span aria-hidden="true">&times;</span>
												</button>
								</div>
								<div class="panel-body">
												<c:url var="url" value="Action"></c:url>
												<form class="form-horizontal simple-form" action="${url}" method="POST">
																<div class="form-group">
																				<label for="name" class="col-sm-4 control-label">Name: </label>
																				<div class="col-sm-8">
																								<input type="text" class="form-control" placeholder="Employee Name" name="name" required/>
																				</div>
																</div>
																<div class="form-group">
																				<label for="name" class="col-sm-4 control-label">Username:</label>
																				<div class="col-sm-7">
																								<input type="text" class="form-control verify-input" url="${url}" param="verify_username" placeholder="Choose a unique username" name="username" required />
																				</div>
																				<div class="col-sm-1"><span id="verify-input-status" data-container="body" data-toggle="tooltip" title=""></span></div>
																</div>
																<div class="form-group">
																				<label for="name" class="col-sm-4 control-label">Type:</label>
																				<div class="col-sm-8">
																								<select class="form-control" name="type">
																												<option value="s">Stock</option>
																												<option value="l" selected>Sales</option>
																												<option value="m" >Manager</option>
																								</select>
																				</div>
																</div>
																<div class="form-group">
																				<label for="name" class="col-sm-4 control-label">Mobile:</label>
																				<div class="col-sm-8">
																								<input type="number" class="form-control" placeholder="Employee's mobile number" name="mobile" required/>
																				</div>
																</div>
																<div class="form-group">
																				<label for="name" class="col-sm-4 control-label">Email:</label>
																				<div class="col-sm-8">
																								<input type="email" class="form-control" placeholder="Employee's email-id" name="email" required/>
																				</div>
																</div>
																<div class="form-group">
																				<label for="name" class="col-sm-4 control-label">Date of Birth:</label>
																				<div class="col-sm-8">
																								<input type="date" class="form-control" placeholder="Employee's DOB" name="dob" required/>
																				</div>
																</div>
																<div class="form-group">
																				<label for="name" class="col-sm-4 control-label">Gender:</label>
																				<div class="col-sm-8">
																								<select class="form-control" name="gender">
																												<option value="m" selected>Male</option>
																												<option value="f">Female</option>
																												<option value="o">Other</option>
																								</select>
																				</div>
																</div>
																<div class="bg-info text-center marginalise">Address</div>
																<div class="form-group">
																				<label for="name" class="col-sm-4 control-label">Street:</label>
																				<div class="col-sm-8">
																								<input type="text" class="form-control" placeholder="House and Street name" name="street" required/>
																				</div>
																</div>
																<div class="form-group">
																				<label for="name" class="col-sm-4 control-label">Colony:</label>
																				<div class="col-sm-8">
																								<input type="text" class="form-control" placeholder="Colony" name="colony" required/>
																				</div>
																</div>
																<div class="form-group">
																				<label for="name" class="col-sm-4 control-label">City:</label>
																				<div class="col-sm-8">
																								<input type="text" class="form-control" placeholder="City" name="city" required/>
																				</div>
																</div>
																<div class="form-group">
																				<label for="name" class="col-sm-4 control-label">Landmark:</label>
																				<div class="col-sm-8">
																								<input type="text" class="form-control" placeholder="Landmark" name="landmark" required/>
																				</div>
																</div>
																<div class="form-group">
																				<label for="name" class="col-sm-4 control-label">State:</label>
																				<div class="col-sm-8">
																								<input type="text" class="form-control" placeholder="State" name="state" required/>
																				</div>
																</div>
																<div class="form-group">
																				<label for="name" class="col-sm-4 control-label">ZIP:</label>
																				<div class="col-sm-8">
																								<input type="number" class="form-control" placeholder="PINCODE" name="zip" maxlength="6" minlength="6" required/>
																				</div>
																</div>
																<input type="hidden" value="add_emp" name="param" />
																<input type="hidden" value="type" name="file-type" />
												</form>
												<c:url var="url" value="/formUpload"></c:url>
												<form enctype="multipart/form-data" class="form-horizontal file-form" action="${url}" method="POST" >
																<div class="form-group">
																				<label for="min_percentage" class="col-sm-4 control-label">Image: </label>
																				<div class="col-sm-8">
																								<input type="file" name="image" class="form-control file" />
																				</div>
																</div>
												</form>
												<div class="form-group">
																<div class="col-sm-8 col-sm-offset-2">
																				<button class="btn btn-primary btn-group-justified file-form-submit" close="overlay" post-target="view_item_stock">Add</button>
																</div>
												</div>
								</div>
				</div>
</div>