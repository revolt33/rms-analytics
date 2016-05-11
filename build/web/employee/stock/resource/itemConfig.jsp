<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="content-header bg-info">
				<h3 class="text-center">Item Configuration</h3>
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
</div>
<c:forEach var="cat" items="${categories}">
				<div class="panel panel-primary category" code="${cat.id}">
								<div class="panel-heading">${cat.name}
								</div>
								<div class="panel-body">
												<c:forEach var="item" items="${cat.itemList}" >
																<div class="col-sm-4">
																				<div class="panel panel-info">
																								<div class="panel-heading">${item.name}
																												<div class="icon pull-right">
																																<c:url var="add_item_unit" value="templates/addItemUnit.jsp"></c:url>
																																<span class="glyphicon glyphicon-plus-sign load-button-with-url" data-toggle="tooltip" title="add a unit" url="${add_item_unit}" target="dialog" make-visible="overlay" param="${item.id}&name=${item.name}" ></span>
																												</div>
																								</div>
																								<div class="panel-body">
																												<table class="table table-striped">
																																<thead>
																																				<tr>
																																								<th>Unit name</th>
																																								<th>Amount</th>
																																								<th>Operation</th>
																																				</tr>
																																</thead>
																																<tbody>
																																				<c:forEach var="unit" items="${item.itemUnits}">
																																								<tr>
																																												<td data-toggle="tooltip" title="Added By: ${unit.added_by}" data-container="body">${unit.name}</td>
																																												<td>${unit.fraction}</td>
																																												<c:url var="url" value="Action"></c:url>
																																												<td>
																																																<span class="icon">
																																																				<span class="glyphicon glyphicon-equalizer load-button-with-url" data-toggle="tooltip" title="View price history" url="${url}" target="dialog" make-visible="overlay" param="view_price_history&code=${unit.id}&name=${unit.name} ${item.name}"></span>
																																																<c:if test="${unit.status}">
																																																				<span class="glyphicon glyphicon-ban-circle action-button-with-url" data-toggle="tooltip" title="disable unit" url="${url}" param="disable_item_unit" code="${unit.id}" close="" target="view_item_config"></span>
																																																</c:if>
																																																<c:if test="${!unit.status}">
																																																				<span class="glyphicon glyphicon-ok-circle action-button-with-url" data-toggle="tooltip" title="enable unit" url="${url}" param="enable_item_unit" code="${unit.id}" close="" target="view_item_config"></span>
																																																</c:if>
																																																</span>
																																																
																																												</td>
																																								</tr>
																																				</c:forEach>
																																</tbody>
																												</table>
																								</div>
																				</div>
																</div>
												</c:forEach>
								</div>
				</div>
</c:forEach>