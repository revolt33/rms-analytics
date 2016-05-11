<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-sm-12">
				<div class="panel panel-primary">
								<div class="panel-heading text-center">${param['name']} Stock History
												<button type="button" class="close pull-right" target="overlay" aria-label="Close">
																<span aria-hidden="true">&times;</span>
												</button>
								</div>
								<div class="panel-body">
												<table class="table table-hover">
																<thead>
																				<tr>
																								<th>Batch Code</th>
																								<th>Record Date</th>
																								<th>Expiration</th>
																								<th>Stock</th>
																								<th>Amount</th>
																								<th>Consumed</th>
																								<th>Added By</th>
																								<th>Operations</th>
																				</tr>
																</thead>
																<tbody>
																				<c:forEach var="entry" items="${requestScope['ingridient_entry']}">
																								<c:if test="${entry.isActive}">
																												<tr>
																												</c:if>
																												<c:if test="${!entry.isActive}">
																												<tr class="bg-danger">
																												</c:if>
																												<td><b>${entry.id}</b></td>
																												<td>${entry.addedTimestamp}</td>
																												<td>${entry.expiryDateTime}</td>
																												<td>${entry.stock}</td>
																												<td>${entry.amount}</td>
																												<td>${entry.consumed}</td>
																												<td>${entry.addedBy}</td>
																												<td>
																																<span class="icon">
																																				<c:if test="${entry.isActive}">
																																								<c:url var="url" value="Action" ></c:url>
																																								<span class="glyphicon glyphicon-trash action-button-with-url" data-toggle="tooltip" title="remove this batch" url="${url}" param="remove_ingredient_batch" code="${entry.id}" target="view_ingredient_display" close="overlay"></span>
																																				</c:if>
																																				<c:if test="${!entry.isActive}">
																																								<span class="glyphicon glyphicon-info-sign" data-toggle="tooltip" title="Batch removed"></span>
																																				</c:if>
																																				<c:if test="${(entry.amount-entry.consumed)>0  && entry.isActive}">
																																								<c:url var="url" value="Action"></c:url>
																																								<span class="glyphicon glyphicon-minus-sign reduce-batch-ingredient-stock" group="reduce-batch-ingredient-stock" data-toggle="popover" title="Reduce Batch Stock" code="${param.code}" group-code="${entry.id}" url="${url}" batch="${entry.id}" param="reduce_batch_ingredient_stock" close="overlay" post-target="view_ingredient_display"></span>
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