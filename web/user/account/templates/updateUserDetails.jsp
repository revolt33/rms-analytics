<%@page import="business.customer.Customer"%>
<%@page import="business.customer.AuthToken"%>
<%@page import="business.customer.Processor"%>
<%@page import="java.sql.Connection"%>
<%@page import="application.ConnectionToken"%>
<%@page import="application.Connector"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
				Connector connector = (Connector) application.getAttribute("connector");
				ConnectionToken token = connector.getToken();
				Connection con = connector.getConnection(token);
				Customer customer = Processor.getUserDetail((AuthToken)session.getAttribute("customer_auth_token"), con);
				connector.closeConnection(token);
				pageContext.setAttribute("customer", customer);
%>
<div class="col-md-6 col-md-offset-3">
				<div class="panel panel-primary">
								<div class="panel-heading text-center">Update Contact Details
												<button type="button" class="close pull-right" target="overlay" aria-label="Close">
																<span aria-hidden="true">&times;</span>
												</button>
								</div>
								<div class="panel-body">
												<c:url value="Action" var="url"></c:url>
												<form class="form-horizontal" action="${url}" method="POST" post-target="view_user_details" close="overlay">
																<div class="form-group">
																				<label class="control-label col-xs-4" for="mobile">Mobile:</label>
																				<div class="col-xs-8">
																								<input class="form-control" value="${pageScope.customer.contact}" type="number" maxlength="10" name="mobile" required/>
																				</div>
																</div>
																<div class="form-group">
																				<label class="control-label col-xs-4" for="email">Email:</label>
																				<div class="col-xs-8">
																								<input class="form-control" type="email" value="${pageScope.customer.email}" name="email" maxlength="200" required/>
																				</div>
																</div>
																<div class="form-group">
																				<label class="control-label col-xs-4" for="dob">Birthday:</label>
																				<div class="col-xs-8">
																								<input class="form-control" type="date" name="dob" value="${pageScope.customer.dob}" required/>
																				</div>
																</div>
																<input type="hidden" name="param" value="update_details" />
																<div class="form-group">
																				<div class="col-xs-8 col-xs-offset-4">
																								<button type="submit" class="btn btn-group-justified btn-primary">Update</button>
																				</div>
																</div>
												</form>
								</div>
				</div>
</div>