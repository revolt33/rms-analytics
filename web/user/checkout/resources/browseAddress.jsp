<%@page import="business.customer.Address"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	if (((ArrayList<Address>) request.getAttribute("addresses")).size() == 0) {
%>
<p class="text-danger">There are no addresses in your address book, <a href="#" onclick="addAddress()">Click here</a> to add new address.</p>
<hr>
<div class="row" id="add-address">
				<div class="col-xs-10 col-xs-offset-1">
								<c:url value="/user/account/Action" var="url"></c:url>
								<form class="form-horizontal" action="${url}" method="POST" id="add-address-form">
												<input type="hidden" name="param" value="add_address" />
												<div class="form-group">
																<label class="control-label col-xs-4" for="name">Name:</label>
																<div class="col-xs-8">
																				<input class="form-control" type="text" name="name" maxlength="100" value="${sessionScope.customer_auth_token.name}" placeholder="Name of receiver" required/>
																</div>
												</div>
												<div class="form-group">
																<label class="control-label col-xs-4" for="house">House:</label>
																<div class="col-xs-8">
																				<input class="form-control" type="text" name="house" maxlength="50" placeholder="Your house number" required/>
																</div>
												</div>
												<div class="form-group">
																<label class="control-label col-xs-4" for="street">Street:</label>
																<div class="col-xs-8">
																				<input class="form-control" type="text" name="street" maxlength="200" placeholder="Your street address" required/>
																</div>
												</div>
												<div class="form-group">
																<label class="control-label col-xs-4" for="landmark">Landmark:</label>
																<div class="col-xs-8">
																				<input class="form-control" type="text" name="landmark" maxlength="200" placeholder="Your nearby landmark"/>
																</div>
												</div>
												<div class="form-group">
																<label class="control-label col-xs-4" for="city">City:</label>
																<div class="col-xs-8">
																				<input class="form-control" type="text" name="city" maxlength="200" placeholder="Your city" required/>
																</div>
												</div>
												<div class="form-group">
																<label class="control-label col-xs-4" for="pin">Pin:</label>
																<div class="col-xs-8">
																				<input class="form-control" type="number" name="pin" maxlength="6" placeholder="Your area PINCODE" required/>
																</div>
												</div>
												<div class="form-group">
																<label class="control-label col-xs-4" for="contact">Contact:</label>
																<div class="col-xs-8">
																				<input class="form-control" type="number" name="contact" maxlength="10" placeholder="Your contact number" required/>
																</div>
												</div>
												<div class="form-group">
																<div class="col-xs-8 col-xs-offset-4" >
																				<button type="submit" class="btn btn-primary btn-group-justified">Add Address</button>
																</div>
												</div>
								</form>
				</div>
</div>
<script type="text/javascript">
				function addAddress() {
								$('#add-address').slideDown();
				}
				$('#add-address-form').on('submit', function (submit) {
								submit.preventDefault();
								var link = $(this).attr('action');
								var method = $(this).attr('method');
								var data = $(this).serialize();
								$('#load-screen').fadeIn();
								$.ajax({
												url: link,
												type: method,
												data: data,
												success: function (data) {
																var json = $.parseJSON(data);
																var message = json['message'];
																var status = json['status'];
																if ( status === 1 ) {
																				successMessage(message);
																				$('#dialog').modal('hide');
																				setTimeout(function () {
																								$('#home-delivery').trigger('click');
																				}, 300);
																} else {
																				errorMessage(message);
																}
																$('#load-screen').fadeOut();
												},
												error: function () {
																errorMessage('Connection Error!');
																$('#load-screen').fadeOut();
												}
								});
				});
</script>
<%
	}
%>
<c:forEach var="address" items="${requestScope.addresses}" varStatus="count">
				${count.index%2==0?'<div class="row">':''}
				<div class="col-sm-6">
								<div class="panel panel-default">
												<div class="panel-body">
																${address.name}
																<br />
																${address.address}
																<br />
																<c:if test="${address.havingLandmark}">
																				Landmark: ${address.landmark}
																				<br />
																</c:if>
																PINCODE: ${address.pin}
																<br />
																Contact: ${address.contact}
												</div>
												<div class="panel-footer">
																<button class="btn btn-warning btn-group-justified deliver" code="${address.id}">Deliver at this address</button>
												</div>
								</div>
				</div>
				${count.index%2==1?'</div>':''}
</c:forEach>