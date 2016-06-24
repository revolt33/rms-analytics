<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="content-header bg-info">
				<h3 class="text-center">Your Addresses</h3>
				<c:url var="url" value="templates/addAddress.jsp"></c:url>
				<button type="button" class="btn btn-default load-button-with-url" url="${url}" param="" target="dialog" make-visible="overlay">Add new Address</button>
</div>
<div>
				<c:forEach var="address" items="${requestScope.addresses}">
								<div class="col-sm-4">
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
																				<div class="row">
																								<div class="col-xs-6">
																												<c:url var="url" value="templates/editAddress.jsp"></c:url>
																												<button class="btn btn-warning btn-group-justified load-button-with-url" url="${url}" param="${address.id}" target="dialog" make-visible="overlay">Edit</button>
																								</div>
																								<div class="col-xs-6">
																												<c:url var="url" value="Action"></c:url>
																												<button class="btn btn-danger btn-group-justified action-button-with-url" target="view_addresses" url="${url}" param="delete_address" code="${address.id}" confirm-title="Delete Address" confirm-content="Are you sure to delete this address?" confirm-okay="Yes">Delete</button>
																								</div>
																				</div>
																</div>
												</div>
								</div>
				</c:forEach>
</div>