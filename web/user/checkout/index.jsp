<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Checkout</title>
								<jsp:include page="/resource/include.jsp" />
								<c:url var="url" value="/user/checkout/script.js"></c:url>
								<script type="text/javascript" src="${url}" defer></script>
								<c:url var="url" value="/user/checkout/style.css"></c:url>
								<link rel="stylesheet" href="${url}" type="text/css" />
    </head>
    <body>
        <jsp:include page="/user/templates/header.jsp" />
								<div id="load-screen"></div>
								<div id="status-bar" class="alert fade in text-center">
												<a href="#" class="close status-close" data-dismiss="alert" area-label="close">&times;</a><span></span>
								</div>
								<div class="modal fade" tabindex="-1" role="dialog" id="dialog">
												<div class="modal-dialog">
																<div class="modal-content">
																				<div class="modal-header">
																								<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
																								<h4 class="modal-title text-center">Discount Coupons</h4>
																				</div>
																				<div class="modal-body">

																				</div>
																</div>
												</div>
								</div>
								<div class="container">
												<div class="push-down">
																<div class="col-md-8">
																				<div class="panel panel-default">
																								<div class="panel-heading">Items in your cart</div>
																								<div class="panel-body">
																												<table class="table table-striped table-hover">
																																<thead>
																																				<tr>
																																								<th>#</th>
																																								<th>Item Name</th>
																																								<th>Quantity</th>
																																								<th>Price</th>
																																								<th>Amount</th>
																																				</tr>
																																</thead>
																																<tbody>
																																				<c:forEach var="item" items="${requestScope.cart.list}" varStatus="count">
																																								<c:set var="cross" value="false"></c:set>
																																								<c:if test="${!item.available}">
																																												<c:set var="cross" value="true"></c:set>
																																												<c:set var="msg" value="Item out of stock"></c:set>
																																								</c:if>
																																								<c:if test="${!item.valid}">
																																												<c:set var="cross" value="true"></c:set>
																																												<c:set var="msg" value="Item is unavailable"></c:set>
																																								</c:if>
																																								<c:if test="${cross}">
																																												<tr class="text-muted cross" data-toggle="tooltip" title="${msg}">
																																												</c:if>
																																												<c:if test="${!cross}">
																																												<tr>
																																												</c:if>
																																												<td>${count.index+1}</td>
																																												<td>${item.name}</td>
																																												<td>${item.quantity}</td>
																																												<td>${item.price}</td>
																																												<td>${item.price*item.quantity} INR</td>
																																								</tr>
																																				</c:forEach>
																																</tbody>
																												</table>
																												<c:if test="${!requestScope.cart.available}">
																																<div class="bg-danger text-center text-danger col-sm-6 col-sm-offset-3"><i>One or more items is not available!</i></div>
																												</c:if>
																								</div>
																								<div class="panel-footer">
																												<c:url value="/user/checkout/" var="url"></c:url>
																												<form class="form-inline" method="POST" action="${url}">
																																<c:forEach var="item" items="${requestScope.cart.list}">
																																				<input type="hidden" name="unit[]" value="${item.unit},${item.quantity}" />
																																</c:forEach>
																																<c:set var="form_class" value="" scope="page"></c:set>
																																<c:if test="${requestScope.cart.couponApplied}">
																																				<c:if test="${requestScope.cart.couponValid}">
																																								<c:set var="form_class" value=" has-feedback has-success" scope="page"></c:set>
																																				</c:if>
																																				<c:if test="${!requestScope.cart.couponValid}">
																																								<c:set var="form_class" value=" has-feedback has-error" scope="page"></c:set>
																																				</c:if>
																																</c:if>
																																<fieldset>
																																				<legend>Have a coupon?</legend>
																																				<div class="form-group ${form_class}">
																																								<div class="col-sm-8">
																																												<div class="input-group">
																																																<input class="form-control text-uppercase" id="coupon" type="text" maxlength="7" placeholder="Coupon Code" name="coupon" ${requestScope.cart.couponApplied?'aria-describedby="coupon_status"':''} value="${requestScope.cart.coupon}" required/>
																																																<c:url var="url" value="coupon"></c:url>
																																																<span class="input-group-addon icon" data-toggle="tooltip" title="Browse Coupons" data-container="body" id="browse-coupons" url="${url}">
																																																				<span class="glyphicon glyphicon-globe" ></span>
																																																</span>
																																												</div>
																																												<c:if test="${requestScope.cart.couponApplied}">
																																																<c:if test="${requestScope.cart.couponValid}">
																																																				<span class="glyphicon glyphicon-ok-sign form-control-feedback input-sign" aria-hidden="true"></span>
																																																				<span class="sr-only" id="coupon_status">(coupon is valid)</span>
																																																</c:if>
																																																<c:if test="${!requestScope.cart.couponValid}">
																																																				<span class="glyphicon glyphicon-remove-sign form-control-feedback input-sign" aria-hidden="true"></span>
																																																				<span class="sr-only" id="coupon_status">(coupon is invalid)</span>
																																																				<span class="help-block">The coupon you applied is invalid.</span>
																																																</c:if>
																																												</c:if>
																																								</div>
																																								<div class="col-sm-4">
																																												<button class="btn btn-warning btn-group-justified" type="submit">Apply Coupon</button>
																																								</div>
																																				</div>
																																</fieldset>
																												</form>
																								</div>
																				</div>
																</div>
																<div class="col-md-4">
																				<div class="panel panel-info">
																								<div class="panel-heading text-center">Order Summary</div>
																								<div class="panel-body">
																												<form class="form-horizontal">
																																<div class="form-group">
																																				<label class="col-xs-8 control-label">Number of items in your cart:</label>
																																				<div class="col-xs-4">
																																								<p class="form-control-static">${requestScope.cart.size}</p>
																																				</div>
																																</div>
																																<div class="form-group">
																																				<label class="col-xs-8 control-label">Total Amount${requestScope.cart.couponValid?'':' Payable'}:</label>
																																				<div class="col-xs-4">
																																								<p class="form-control-static text-success bold">${requestScope.cart.total} INR</p>
																																				</div>
																																</div>
																																<c:if test="${requestScope.cart.couponValid}">
																																				<div class="form-group">
																																								<label class="col-xs-8 control-label">Discount:</label>
																																								<div class="col-xs-4">
																																												<p class="form-control-static text-warning bold">${requestScope.cart.total-requestScope.cart.effectiveValue} INR</p>
																																								</div>
																																				</div>
																																				<div class="form-group">
																																								<label class="col-xs-8 control-label">Total Amount Payable:</label>
																																								<div class="col-xs-4">
																																												<p class="form-control-static text-warning bold">${requestScope.cart.effectiveValue} INR</p>
																																								</div>
																																				</div>
																																</c:if>
																												</form>
																												<form class="form-horizontal" method="POST" action="">
																																<c:forEach var="item" items="${requestScope.cart.list}">
																																				<input type="hidden" name="unit[]" value="${item.unit},${item.quantity}" />
																																</c:forEach>
																																<input type="hidden" name="coupon" value="${requestScope.cart.coupon}" />
																																<div class="form-group">
																																				<div class="col-xs-12 bold text-center">Delivery Type</div>
																																				<div class="col-xs-12 text-center">
																																								<div class="radio">
																																												<label class="radio-inline"><input type="radio" name="delivery" value="p" />Pickup</label>
																																												<label class="radio-inline"><input type="radio" name="delivery" value="h" />Home Delivery</label>
																																								</div>
																																				</div>
																																</div>
																																<div class="form-group">
																																				<div class="col-xs-12">
																																								<button class="btn btn-primary btn-group-justified">Place Order</button>
																																				</div>
																																</div>
																												</form>
																								</div>
																				</div>
																</div>
												</div>
								</div>
				</body>
</html>