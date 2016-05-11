<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-sm-6 col-sm-offset-3">
				<div class="panel panel-primary" >
								<div class="panel-heading text-center">Add Item
												<button type="button" class="close pull-right" aria-label="Close" target="overlay">
																<span aria-hidden="true">&times;</span>
												</button>
								</div>
								<div class="panel-body">
												<c:url var="url" value="Action"></c:url>
												<form class="form-horizontal simple-form" action="${url}" method="POST">
																<div class="form-group">
																				<label for="name" class="col-sm-4 control-label">Name:</label>
																				<div class="col-sm-8">
																								<input type="text" class="form-control" placeholder="Item Name" name="name" required/>
																				</div>
																</div>
																<div class="form-group">
																				<label for="description" class="col-sm-4 control-label">Description:</label>
																				<div class="col-sm-8">
																								<textarea class="form-control" placeholder="Description" maxlength="500" name="description" required></textarea>
																				</div>
																</div>
																<div class="form-group">
																				<label for="min_percentage" class="col-sm-4 control-label">Min Percentage:</label>
																				<div class="col-sm-8">
																								<input type="text" class="form-control" placeholder="Minimum Price" name="min_percentage" required/>
																				</div>
																</div>
																<div class="form-group">
																				<label for="min_percentage" class="col-sm-4 control-label">Price:</label>
																				<div class="col-sm-8">
																								<input type="text" class="form-control" placeholder="Item Price" name="price" required/>
																				</div>
																</div>
																<div class="form-group">
																				<label for="on_order" class="col-sm-4 control-label">Produced On Order: </label>
																				<div class="col-sm-8">
																								<input type="radio" name="on_order" value="y" />Yes
																								<input type="radio" name="on_order" value="n" />No
																				</div>
																</div>
																<div class="form-group">
																				<label for="delivery" class="col-sm-4 control-label">Eligible for Delivery: </label>
																				<div class="col-sm-8">
																								<input type="radio" name="delivery" value="y" />Yes
																								<input type="radio" name="delivery" value="n" />No
																				</div>
																</div>
																<input type="hidden" value="add_item" name="param" />
																<input type="hidden" value="${param['param']}" name="category" />
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
																				<button class="btn btn-primary btn-group-justified file-form-submit" close="overlay" post-target="view_item_display">Add</button>
																</div>
												</div>
								</div>
				</div>
</div>