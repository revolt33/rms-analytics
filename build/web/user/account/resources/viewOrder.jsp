<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-md-8 col-md-offset-2">
				<div class="panel panel-primary">
								<div class="panel-heading text-center">View Order
												<button type="button" class="close pull-right" target="overlay" aria-label="Close">
																<span aria-hidden="true">&times;</span>
												</button>
								</div>
								<div class="panel-body">
												<div class="col-sm-6">
																<p><strong>${requestScope.order.customerName}</strong></p>
																<p>Contact: ${requestScope.order.contact}</p>
																<p>Time: ${requestScope.order.formattedTimestamp}</p>
												</div>
												<div class="col-sm-6">
																<p class="text-center"><strong>Delivery Address</strong></p>
																<c:if test="${requestScope.order.homeDelivery}">
																				<p>${requestScope.order.address}</p>
																</c:if>
																<c:if test="${!requestScope.order.homeDelivery}">
																				<p>Pickup at shop.</p>
																</c:if>
												</div>
												<hr />
												<div class="col-sm-12">
																<table class="table table-hover table-striped">
																				<thead>
																								<tr>
																												<th>#</th>
																												<th>Item Name</th>
																												<th>Quantity</th>
																												<th>Rate</th>
																												<th>Amount</th>
																								</tr>
																				</thead>
																				<tbody>
																								<c:forEach items="${requestScope.order.info}" var="info" varStatus="count">
																												<tr>
																																<td>${count.index+1}</td>
																																<td>${info.name}</td>
																																<td>${info.quantity}</td>
																																<td>${info.price}</td>
																																<td>${info.amount}</td>
																												</tr>
																								</c:forEach>
																				</tbody>
																				<tfoot>
																								<tr>
																												<td colspan="3"></td>
																												<th>Total:</th>
																												<td><img src="../../img/inr.png" width="10" height="10" />${requestScope.order.value}</td>
																								</tr>
																								<c:if test="${requestScope.order.discountCode!=null}">
																												<tr>
																																<td colspan="3"></td>
																																<th>Discount (${requestScope.order.discountCode}):</th>
																																<td><img src="../../img/inr.png" width="10" height="10" />${requestScope.order.discountedValue}</td>
																												</tr>
																												<tr>
																																<td colspan="3"></td>
																																<th>Amount Payable:</th>
																																<td><img src="../../img/inr.png" width="10" height="10" />${requestScope.order.effectiveValue}</td>
																												</tr>
																								</c:if>
																				</tfoot>
																</table>
												</div>
								</div>
								<div class="panel-footer">
												<p>Status: <span class="label ${requestScope.order.orderStatusClass}">${requestScope.order.orderStatusValue}</span></p>
																<c:if test="${requestScope.order.attended}">
																				<c:if test="${requestScope.order.homeDelivery}">
																				<p>Estimated Delivery Time: ${requestScope.order.formattedEstimatedDelivery}</p>
																</c:if>
																<c:if test="${requestScope.order.pickup}">
																				<p>Ready to pickup at: ${requestScope.order.formattedEstimatedDelivery}</p>
																</c:if>
												</c:if>
								</div>
				</div>
</div>