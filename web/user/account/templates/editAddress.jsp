<%@page import="business.customer.Address"%>
<%@page import="business.customer.AuthToken"%>
<%@page import="java.sql.Connection"%>
<%@page import="application.ConnectionToken"%>
<%@page import="application.Connector"%>
<%@page import="business.customer.Processor" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-md-6 col-md-offset-3">
				<%
					Connector connector = (Connector) application.getAttribute("connector");
					ConnectionToken token = connector.getToken();
					Connection con = connector.getConnection(token);
					AuthToken authToken = (AuthToken) session.getAttribute("customer_auth_token");
					int id = Integer.parseInt(request.getParameter("param"));
					Address address = Processor.getAddress(authToken, id, con);
					connector.closeConnection(token);
					if (address.getId() > 0) {
						pageContext.setAttribute("address", address);
				%>
				<div class="panel panel-primary">
								<div class="panel-heading text-center">Edit address
												<button type="button" class="close pull-right" target="overlay" aria-label="Close">
																<span aria-hidden="true">&times;</span>
												</button>
								</div>
								<div class="panel-body">
												<c:url value="Action" var="url"></c:url>
												<form class="form-horizontal" action="${url}" method="POST" post-target="view_addresses" close="overlay">
																<input type="hidden" name="param" value="edit_address" />
																<div class="form-group">
																				<label class="control-label col-xs-4" for="name">Name:</label>
																				<div class="col-xs-8">
																								<input class="form-control" type="text" name="name" maxlength="100" value="${pageScope.address.name}" placeholder="Name of receiver" required/>
																				</div>
																</div>
																<div class="form-group">
																				<label class="control-label col-xs-4" for="house">House:</label>
																				<div class="col-xs-8">
																								<input class="form-control" type="text" name="house" maxlength="50" placeholder="Your house number" value="${pageScope.address.house}" required/>
																				</div>
																</div>
																<div class="form-group">
																				<label class="control-label col-xs-4" for="street">Street:</label>
																				<div class="col-xs-8">
																								<input class="form-control" type="text" name="street" maxlength="200" placeholder="Your street address" value="${pageScope.address.street}" required/>
																				</div>
																</div>
																<div class="form-group">
																				<label class="control-label col-xs-4" for="landmark">Landmark:</label>
																				<div class="col-xs-8">
																								<input class="form-control" type="text" name="landmark" maxlength="200" placeholder="Your nearby landmark" value="${pageScope.address.landmark}"/>
																				</div>
																</div>
																<div class="form-group">
																				<label class="control-label col-xs-4" for="city">City:</label>
																				<div class="col-xs-8">
																								<input class="form-control" type="text" name="city" maxlength="200" placeholder="Your city" value="${pageScope.address.city}" required/>
																				</div>
																</div>
																<div class="form-group">
																				<label class="control-label col-xs-4" for="pin">Pin:</label>
																				<div class="col-xs-8">
																								<input class="form-control" type="number" name="pin" maxlength="6" placeholder="Your area PINCODE" value="${pageScope.address.pin}" required/>
																				</div>
																</div>
																<div class="form-group">
																				<label class="control-label col-xs-4" for="contact">Contact:</label>
																				<div class="col-xs-8">
																								<input class="form-control" type="number" name="contact" maxlength="10" placeholder="Your contact number" value="${pageScope.address.contact}" required/>
																				</div>
																</div>
																<input type="hidden" name="id" value="${pageScope.address.id}" />
																<div class="form-group">
																				<div class="col-xs-8 col-xs-offset-4" >
																								<button type="submit" class="btn btn-primary btn-group-justified">Save Changes</button>
																				</div>
																</div>
												</form>
								</div>
				</div>
				<%
				} else {
				%>
				<div class="panel panel-warning">
								<div class="panel-heading text-center">Error
												<button type="button" class="close pull-right" target="overlay" aria-label="Close">
																<span aria-hidden="true">&times;</span>
												</button>
								</div>
								<div class="panel-body">
												<p class="text-center text-danger">The address you are trying to edit does not exists!</p>
								</div>
				</div>
				<%
					}
				%>
</div>