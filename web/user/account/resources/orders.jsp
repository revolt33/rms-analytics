<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="content-header bg-info">
				<h3 class="text-center">Your Orders</h3>
</div>
<div>
				<c:forEach var="order" items="${requestScope.orders}" >
								<div class="col-md-6">
												<div class="panel panel-default">
																<div class="panel-heading">
																				${order.customerName}
																				<c:url value="Action" var="url"></c:url>
																				<span class="pull-right"><span class="label ${order.orderStatusClass}">${order.orderStatusValue}</span><img src="../../img/inr.png" width="14" height="14" /> ${order.effectiveValue}<span class="icon"><span class="glyphicon glyphicon-list-alt load-button-with-url" data-toggle="tooltip" title="View Order" data-container="body" make-visible="overlay" target="dialog" param="view_order&order=${order.id}" url="${url}"></span></span>
																</div>
																<div class="panel-body">
																				<div class="col-sm-6">
																								<form class="form-horizontal">
																												<div class="form-group-sm">
																																<label class="control-label col-xs-6">Contact:</label>
																																<div class="col-xs-6">
																																				<p class="form-control-static">${order.contact}</p>
																																</div>
																												</div>
																												<c:if test="${order.discountCode!=null}">
																																<div class="form-group-sm">
																																				<label class="control-label col-xs-6">Coupon:</label>
																																				<div class="col-xs-6">
																																								<p class="form-control-static text-success">${order.discountCode}</p>
																																				</div>
																																</div>
																																<div class="form-group-sm">
																																				<label class="control-label col-xs-6">Total:</label>
																																				<div class="col-xs-6">
																																								<p class="form-control-static"><img src="../../img/inr.png" width="10" height="10" />${order.value}</p>
																																				</div>
																																</div>
																																<div class="form-group-sm">
																																				<label class="control-label col-xs-6">Discount:</label>
																																				<div class="col-xs-6">
																																								<p class="form-control-static"><img src="../../img/inr.png" width="10" height="10" />${order.discountedValue}</p>
																																				</div>
																																</div>
																												</c:if>
																								</form>
																				</div>
																				<div class="col-sm-6">
																								<c:if test="${order.homeDelivery}">
																												<p class="text-center"><strong>Address</strong></p>
																												<p>${order.address}</p>
																								</c:if>
																								<p>Time: ${order.formattedTimestamp}</p>
																				</div>
																</div>
												</div>
								</div>
				</c:forEach>
</div>