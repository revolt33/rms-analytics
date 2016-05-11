<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="content-header bg-info">
				<h3 class="text-center">Item Display</h3>
				<div class="btn-group" >
								<button class="btn btn-default dropdown-toggle" type="button" id="dropdownCategory" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
												Category
												<span class="caret"></span>
								</button>
								<ul class="dropdown-menu" aria-labelledby="dropdownCategory">
												<c:forEach items="${categories}" var="cat">
																<li><a href="#" code="${cat.id}" class="navigate-category">${cat.name}</a></li>
																</c:forEach>
								</ul>
				</div>
				<div class="btn-group pull-right" >
								<c:url var="url" value="templates/addCategory.jsp"></c:url>
								<button type="button" class="btn btn-default load-button-with-url" url="${url}" param="" target="dialog" make-visible="overlay">Add Category</button>
				</div>
</div>
<c:forEach items="${categories}" var="cat">
				<div class="panel panel-default category" code="${cat.id}">
								<c:if test="${!cat.active}" >
												<div class="disable text-center">
																<c:url value="Action" var="activate_cat_url"></c:url>
																<button class="btn btn-success btn-lg activate action-button-with-url" type="button" url="${activate_cat_url}" target="view_item_display" param="activate_category" code="${cat.id}" close="" >Activate</button>
												</div>
								</c:if>
								<div class="panel-heading">
												${cat.name}
												<div class="icon pull-right">
																<c:url value="Action" var="operation_cat_url"></c:url>
																<c:url var="add_item_url" value="templates/addItem.jsp"></c:url>
																<c:url var="edit_category_url" value="templates/editCategory.jsp"></c:url>
																<span class="glyphicon glyphicon-plus-sign load-button-with-url" title="add item" data-toggle="tooltip" url="${add_item_url}" target="dialog" make-visible="overlay" param="${cat.id}"></span>
																<span class="glyphicon glyphicon-pencil load-button-with-url" title="edit category" data-toggle="tooltip" url="${edit_category_url}" target="dialog" make-visible="overlay" param="${cat.id}"></span>
																<span class="glyphicon glyphicon-trash action-button-with-url" title="remove category" data-toggle="tooltip" url="${operation_cat_url}" target="view_item_display" param="disable_category" code="${cat.id}" close=""></span>
												</div>
								</div>
								<div class="panel-body">
												<table class="table table-striped table-hover">
																<thead>
																				<tr>
																								<th>Name</th>
																								<th>Stock</th>
																								<th>Min-Percentage</th>
																								<th>Active</th>
																								<th>On Order</th>
																								<th>Delivery</th>
																								<th>Added by</th>
																								<th>Operations</th>
																</thead>
																</tr>
																<tbody>
																				<c:forEach items="${cat.itemList}" var="item" >
																								<tr>
																												<td>${item.name}</td>
																												<td>${item.stock}</td>
																												<td>${item.minPercentage}</td>
																												<td>
																																<c:url value="Action" var="item_active_status" ></c:url>
																																				<span class="icon">
																																								<span>${item.isActive}</span>
																																				<c:if test="${item.isActive=='Yes'}" >
																																								<span class="glyphicon glyphicon-ban-circle action-button-with-url" title="Deactivate" data-toggle="tooltip" url="${item_active_status}" target="view_item_display" param="disable_item" code="${item.id}" close="" ></span>
																																				</c:if>
																																				<c:if test="${item.isActive=='No'}" >
																																								<span class="glyphicon glyphicon-ok-circle action-button-with-url" title="Activate" data-toggle="tooltip" url="${item_active_status}" target="view_item_display" param="enable_item" code="${item.id}" close="" ></span>
																																				</c:if>
																																</span>
																												</td>
																												<td>${item.producedOnOrder}</td>
																												<td>${item.isEligibleForDelivery}</td>
																												<td>${item.addedBy}</td>
																												<td>
																																<span class="icon">
																																				<span class="glyphicon glyphicon-pencil" data-toggle="tooltip" title="Edit"></span>
																																				<c:url var="transactions" value="Action"></c:url>
																																				<span class="glyphicon glyphicon-list-alt load-button-with-url" data-toggle="tooltip" title="view previous transactions" url="${transactions}" target="dialog" make-visible="overlay" param="view_item_transactions&code=${item.id}&name=${item.name}"></span>
																																				<c:if test="${item.stock>0}">
																																								<c:url var="url" value="Action"></c:url>
																																								<span class="glyphicon glyphicon-minus-sign reduce-item-stock" group="reduce-item-stock" data-toggle="popover" title="Reduce Stock" code="${item.id}" group-code="${item.id}" url="${url}" batch="0" param="reduce_item_stock" close="" post-target="view_item_display"></span>
																																				</c:if>
																																				<c:if test="${!item.onOrder}">
																																								<c:url var="increase_stock" value="templates/increaseItemStock.jsp"></c:url>
																																								<span class="glyphicon glyphicon-plus-sign load-button-with-url" data-toggle="tooltip" title="Increase Stock" url="${increase_stock}" target="dialog" make-visible="overlay" param="${item.id}&name=${item.name}"></span>
																																				</c:if>
																																</span>
																												</td>
																								</tr>
																				</c:forEach>
																</tbody>
												</table>
								</div>
				</div>
</c:forEach>