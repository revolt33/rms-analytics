<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="content-header bg-info marginalise">
				<h3 class="text-center">Ingredient Display</h3>
				<div class="btn-group">
								<c:url var="url" value="templates/addIngredient.jsp"></c:url>
								<button class="btn btn-default load-button-with-url" type="button" url="${url}" param="" target="dialog" make-visible="overlay">Add Ingredient</button>
				</div>
</div>
<table class="table table-striped">
				<thead>
								<tr>
												<th>Name</th>
												<th>Stock</th>
												<th>Added By</th>
												<th>Operations</th>
								</tr>
				</thead>
				<tbody>
								<c:forEach var="ingredient" items="${requestScope['ingredients']}">
												<tr>
																<td>${ingredient.name}</td>
																<td>${ingredient.stock}</td>
																<td>${ingredient.addedBy}</td>
																<td>
																				<span class="icon">
																								<c:url var="increase_stock" value="templates/increaseIngredientStock.jsp"></c:url>
																								<span class="glyphicon glyphicon-plus-sign load-button-with-url" data-toggle="tooltip" title="Increase Stock" param="${ingredient.id}&name=${ingredient.name}" target="dialog" make-visible="overlay" url="${increase_stock}"></span>
																								<c:url var="transactions" value="Action"></c:url>
																								<span class="glyphicon glyphicon-list-alt load-button-with-url" data-toggle="tooltip" title="view previous transactions" url="${transactions}" param="view_ingredient_transactions&code=${ingredient.id}&name=${ingredient.name}" target="dialog" make-visible="overlay"></span>
																								<c:if test="${ingredient.stock>0}">
																												<c:url var="url" value="Action"></c:url>
																												<span class="glyphicon glyphicon-minus-sign reduce-ingredient-stock" group="reduce-ingredient-stock" data-toggle="popover" title="Reduce Stock" code="${ingredient.id}" group-code="${ingredient.id}" url="${url}" batch="0" param="reduce_ingredient_stock" close="" post-target="view_ingredient_display"></span>
																								</c:if>

																				</span>
																</td>
												</tr>
								</c:forEach>
				</tbody>
</table>
