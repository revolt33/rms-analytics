<%@page import="java.util.ArrayList"%>
<%@page import="application.ConnectionToken"%>
<%@page import="application.Connector"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="business.stock.*" %>
<%
	Connector connector = (Connector) getServletContext().getAttribute("connector");
	ConnectionToken token = connector.getToken();
	ArrayList<Category> category = Processor.getCategories(connector.getConnection(token));
	connector.closeConnection(token);
	pageContext.setAttribute("categories", category);
%>
<div class="col-sm-8 col-sm-offset-2">
				<div class="panel panel-success">
								<div class="panel-heading text-center">Select items for discount
												<button type="button" class="close pull-right" target="content-top-overlay" aria-label="Close">
																<span aria-hidden="true">&times;</span>
												</button>
								</div>
								<div class="panel-body">
												<c:forEach items="${categories}" var="cat">
																<div class="col-xs-6">
																				<div class="panel panel-info">
																								<div class="panel-heading">${cat.name}</div>
																								<div class="panel-body">
																												<table class="table table-striped">
																																<tbody>
																																				<c:forEach items="${cat.itemList}" var="item">
																																								<tr>
																																												<td><input type="checkbox" value="${item.id}" name="items" />&nbsp; <span>${item.name}</span></td>
																																								</tr>
																																				</c:forEach>
																																</tbody>
																												</table>
																								</div>
																				</div>
																</div>
												</c:forEach>
								</div>
								<div class="panel-footer text-center">
												<button class="btn btn-success" onclick="save()">Save Changes</button>
								</div>
				</div>
</div>
<script>
				function save() {
								$('#discount-form').find('.item_list').each(function () {
												$(this).remove();
								});
								$('#items').empty();
								$('input[name=items]').each(function () {

												if ($(this).is(':checked')) {
																var element = document.createElement('input');
																element.type = 'hidden';
																element.value = $(this).val();
																element.name = 'item_list[]';
																$(element).addClass('item_list');
																$('#discount-form').append(element);
																element = document.createElement('a');
																$(element).addClass('btn btn-success btn-sm');
																$(element).text($(this).parent().children('span').text());
																$('#items').append(element);
												}
								});
								$('#content-top-dialog').empty();
								$('#content-top-overlay').fadeOut();
				}
</script>