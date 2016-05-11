<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="col-sm-6 col-sm-offset-3">
				<div class="panel panel-primary">
								<div class="panel-heading text-center">
												Add ${param.name} Unit
												<button type="button" class="close pull-right" target="overlay" aria-label="Close">
																<span aria-hidden="true">&times;</span>
												</button>
								</div>
								<div class="panel-body">
												<c:url var="url" value="Action"></c:url>
												<form class="form-horizontal" action="${url}" method="POST" close="overlay" post-target="view_item_config">
																<div class="form-group">
																				<label for="name" class="col-sm-3 control-label">Name:</label>
																				<div class="col-sm-7">
																								<input type="text" class="form-control" placeholder="Unit Name" name="name" required/>
																				</div>
																</div>
																<div class="form-group">
																				<label for="fraction" class="col-sm-3 control-label">Fraction:</label>
																				<div class="col-sm-7">
																								<input type="number" step="0.05" class="form-control" placeholder="fraction of item" name="fraction" required/>
																				</div>
																</div>
																<div class="form-group">
																				<label for="fraction" class="col-sm-3 control-label">Price:</label>
																				<div class="col-sm-7">
																								<input type="number" step="0.05" class="form-control" placeholder="price of item" name="price" required/>
																				</div>
																</div>
																<input type="hidden" value="add_item_unit" name="param" />
																<input type="hidden" value="${param.param}" name="item" />
																<div class="form-group">
																				<div class="col-sm-8 col-sm-offset-2">
																								<button class="btn btn-primary btn-group-justified" type="submit">Add</button>
																				</div>
																</div>
												</form>
								</div>
				</div>
</div>
