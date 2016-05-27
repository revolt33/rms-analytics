<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-sm-6 col-sm-offset-3">
				<div class="panel panel-primary">
								<div class="panel-heading text-center">Add Discount
												<button type="button" class="close pull-right" target="overlay" aria-label="Close">
																<span aria-hidden="true">&times;</span>
												</button>
								</div>
								<div class="panel-body">
												<c:url var="url" value="Action"></c:url>
												<form class="form-horizontal" id="discount-form" action="${url}" method="POST" close="overlay" post-target="view_discount">
																<div class="form-group">
																				<label class="control-label col-xs-4" for="code">Code:</label>
																				<div class="col-xs-7">
																								<input type="text" class="form-control verify-input uppercase" url="${url}" param="verify_discount_code" minlength="3" maxlength="7" name="code" placeholder="unique discount code" required/>
																				</div>
																				<div class="col-xs-1"><span id="verify-input-status" data-toggle="tooltip" data-container="body" title=""></span></div>
																</div>
																<div class="form-group">
																				<label class="control-label col-xs-4" for="type">Type:</label>
																				<div class="col-xs-8">
																								<select name="type" class="form-control">
																												<option value="e">Everyone</option>
																												<option value="r">Restricted</option>
																												<option value="s">Sales</option>
																								</select>
																				</div>
																</div>
																<div class="form-group">
																				<label class="control-label col-xs-4" for="percentage">Percentage:</label>
																				<div class="col-xs-8">
																								<input type="number" step="0.01" class="form-control" name="percentage" placeholder="discount in percentage" required/>
																				</div>
																</div>
																<div class="form-group">
																				<label class="control-label col-xs-4" for="max">Max Limit:</label>
																				<div class="col-sm-8">
																								<input class="form-control" step="0.01" type="number" name="max" placeholder="maximum allowed off limit" required/>
																				</div>
																</div>
																<div class="form-group">
																				<label class="control-label col-xs-4" for="valid_from">Valid from:</label>
																				<div class="col-xs-8">
																								<input type="datetime-local" class="form-control" name="valid_from" required/>
																				</div>
																</div>
																<div class="form-group">
																				<label class="control-label col-xs-4" for="valid_upto">Valid upto:</label>
																				<div class="col-xs-8">
																								<input type="datetime-local" class="form-control" name="valid_upto" required/>
																				</div>
																</div>
																<div class="form-group">
																				<label class="control-label col-xs-4" for="applicable">Applicable to:</label>
																				<div class="col-xs-8">
																								<select name="applicable" class="form-control">
																												<option value="a">All Items</option>
																												<option value="i">Item List</option>
																								</select>
																				</div>
																</div>
																<div class="form-group">
																				<label class="control-label col-xs-4">Items:</label>
																				<div class="col-xs-8" id="items">

																				</div>
																</div>
																<input type="hidden" name="param" value="add_discount" />
																<div class="form-group">
																				<div class="col-sm-8 col-sm-offset-2">
																								<button class="btn btn-primary btn-group-justified" type="submit">Add</button>
																				</div>
																</div>
												</form>
								</div>
				</div>
</div>
<script>
				$('select[name=applicable]').on('change', function () {
								if ($(this).val() == 'i') {
												$('#load-screen').fadeIn();
				<c:url value="templates/discount-item-list.jsp" var="url"></c:url>
												$.ajax({
																type: 'GET',
																url: '${url}',
																success: function (data) {
																				$('#load-screen').fadeOut();
																				$('#content-top-dialog').html(data);
																				$('#content-top-overlay').fadeIn(300);
																},
																error: function () {
																				$('#load-screen').fadeOut();
																				errorMessage('Connection Error!');
																}
												});
								}
				});
				$('input[name=code]').keyup(function () {
								var code = $(this).val();
								code = code.toUpperCase();
								$(this).val(code);
				});
</script>