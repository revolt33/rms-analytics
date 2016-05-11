<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="business.stock.*" %>
<%@page import="java.util.ArrayList" %>
<%@page import="business.employee.AuthToken" %>
<%@page import="application.*" %>
<%
	AuthToken authToken = (AuthToken) request.getSession().getAttribute("auth_token");
	Connector connector = (Connector) getServletContext().getAttribute("connector");
	ConnectionToken token = connector.getToken();
	ArrayList<Ingredient> ingredients = Processor.getIngridients(authToken, connector.getConnection(token));
	connector.closeConnection(token);
	pageContext.setAttribute("ingredients", ingredients);
%>
<div class="col-md-6 col-md-offset-3">
				<div class="panel panel-primary">
								<div class="panel-heading text-center">Add ${param.name} Relation
												<button type="button" class="close pull-right" aria-label="Close" target="overlay">
																<span aria-hidden="true">&times;</span>
												</button>
								</div>
								<div class="panel-body">
												<c:url var="url" value="Action"></c:url>
												<form class="form-horizontal" action="${url}" method="POST"  close="overlay" post-target="view_relations">
																<input type="hidden" name="item" value="${param.param}" />
																<div class="form-group">
																				<label for="name" class="col-sm-4 control-label">Ingredient:</label>
																				<div class="col-sm-8">
																								<select class="form-control" name="ingridient">
																												<c:forEach var="ing" items="${ingredients}">
																																<option value="${ing.id}">${ing.name}</option>
																												</c:forEach>
																								</select>
																				</div>
																</div>
																<input type="hidden" name="param" value="add_relation" />
																<div class="form-group">
																				<label for="name" class="col-sm-4 control-label">Amount:</label>
																				<div class="col-sm-8">
																								<input type="number" step="0.001" class="form-control" placeholder="Amount" name="amount" required/>
																				</div>
																</div>
																<div class="form-group">
																				<div class="col-sm-8 col-sm-offset-2">
																								<button class="btn btn-primary btn-group-justified" type="submit">Add</button>
																				</div>
																</div>
												</form>
								</div>
				</div>
</div>