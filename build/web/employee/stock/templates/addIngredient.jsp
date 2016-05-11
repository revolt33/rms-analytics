<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-sm-6 col-sm-offset-3">
				<div class="panel panel-primary">
								<div class="panel-heading text-center">Add Ingridient
												<button type="button" class="close pull-right" target="overlay" aria-label="Close">
																<span aria-hidden="true">&times;</span>
												</button>
								</div>
								<div class="panel-body">
												<c:url var="url" value="Action"></c:url>
												<form class="form-horizontal" action="${url}" method="POST" close="overlay" post-target="view_ingredient_display">
																<div class="form-group">
																				<label for="category" class="col-sm-3 control-label">Name:</label>
																				<div class="col-sm-7">
																								<input type="text" class="form-control" placeholder="Ingridient Name" name="ingridient" required/>
																				</div>
																</div>
																<div class="form-group">
																				<label for="category" class="col-sm-3 control-label">State:</label>
																				<div class="col-sm-7">
																								<select type="text" class="form-control" name="state" >
																												<option value="s">Solid</option>
																												<option value="l">Liquid</option>
																								</select>
																				</div>
																</div>
																<input type="hidden" value="add_ingredient" name="param" />
																<div class="form-group">
																				<div class="col-sm-8 col-sm-offset-2">
																								<button class="btn btn-primary btn-group-justified" type="submit">Add</button>
																				</div>
																</div>
												</form>
								</div>
				</div>
</div>