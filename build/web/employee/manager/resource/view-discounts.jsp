<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="bg-info content-header">
				<h3 class="text-center">Discounts</h3>
				<div class="btn-group">
								<c:url var="url" value="templates/addDiscount.jsp"></c:url>
								<button class="btn btn-default load-button-with-url" url="${url}" param="" target="dialog" make-visible="overlay">Add Discount</button>
				</div>
</div>
<div>
				<table class="table table-striped">
								<thead>
												<tr>
																<th>Code</th>
																<th>From</th>
																<th>To</th>
																<th>Percentage</th>
																<th>Limit</th>
																<th>Type</th>
																<th>Added By</th>
																<th>Operations</th>
												</tr>
								</thead>
								<tbody>
												<c:forEach var="discount" items="${discounts}">
																<tr>
																				<td>${discount.code}</td>
																				<td>${discount.formattedFrom}</td>
																				<td>${discount.formattedTo}</td>
																				<td>${discount.percentage}</td>
																				<td>${discount.max}</td>
																				<td>${discount.formattedType}</td>
																				<td>${discount.addedBy}</td>
																				<td>
																								<span class="icon">
																												<c:if test="${discount.status}">
																																<span class="glyphicon glyphicon-ban-circle" data-toggle="tooltip" title="Deactivate"></span>
																												</c:if>
																												<c:if test="${!discount.status}">
																																<span class="glyphicon glyphicon-ok-circle" data-toggle="tooltip" title="Activate"></span>
																												</c:if>
																												<c:if test="${discount.hasItemList}">
																																<span class="glyphicon glyphicon-th-list discount-list" data-toggle="popover" title="Item List"></span><span class="hidden">
																																				<c:forEach items="${discount.itemList}" var="item">${item.name}, </c:forEach>
																																				</span>
																												</c:if>

																								</span>
																				</td>
																</tr>
												</c:forEach>
								</tbody>
				</table>
</div>