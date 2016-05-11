<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="content-header bg-info marginalise">
				<h3 class="text-center">Relations</h3>
				<div class="btn-group" >
								<button class="btn btn-default dropdown-toggle" type="button" id="dropdownCategory" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
												Category
												<span class="caret"></span>
								</button>
								<ul class="dropdown-menu" aria-labelledby="dropdownCategory">
												<c:forEach items="${categories}" var="cat">
																<li><a href="#" code="${cat.id}" class="navigate-category">${cat.name}</a></li>
																</c:forEach>
								</ul>
				</div>
</div>
<c:forEach var="cat" items="${categories}">
				<div class="panel panel-primary category" code="${cat.id}">
								<div class="panel-heading">${cat.name}</div>
								<div class="panel-body">
												<c:forEach var="item" items="${cat.relationList}" >
																<div class="col-sm-4">
																				<div class="panel panel-info">
																								<div class="panel-heading">${item.name}
																												<div class="icon pull-right">
																																<c:url var="url" value="templates/addRelation.jsp"></c:url>
																																<span class="glyphicon glyphicon-plus-sign load-button-with-url" url="${url}" param="${item.id}&name=${item.name}" target="dialog" make-visible="overlay" data-toggle="tooltip" title="add relation" data-container="body"></span>
																												</div>
																								</div>
																								<div class="panel-body">
																												<table class="table table-striped">
																																<thead>
																																				<tr>
																																								<th>Ingredient name</th>
																																								<th>Amount</th>
																																								<th>Operation</th>
																																				</tr>
																																</thead>
																																<tbody>
																																				<c:forEach var="relation" items="${item.relation}">
																																								<tr>
																																												<td>${relation.name}</td>
																																												<td>${relation.amount} ${relation.state}</td>
																																												<c:url var="url" value="Action"></c:url>
																																												<td><span class="icon"><span class="glyphicon glyphicon-trash action-button-with-url" url="${url}" data-toggle="tooltip" title="remove relation" param="remove_relation" target="view_relations" code="${relation.id}" close=""></span></span></td>
																																								</tr>
																																				</c:forEach>
																																</tbody>
																												</table>
																								</div>
																				</div>
																</div>
												</c:forEach>
								</div>
				</div>
</c:forEach>