<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="content-header bg-info marginalise">
				<h3 class="text-center">Counter Sales</h3>
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
				<div class="form-horizontal col-sm-5 pull-right">
								<div class="form-group">
												<label for="search-item" class="col-xs-4 control-label">Search Item:</label>
												<div class="col-xs-8">
																<input type="text" class="form-control" placeholder="press enter to cycle through" id="search-item" name="search-item" />
												</div>
								</div>
				</div>
</div>
<div class="col-sm-9">
				<c:forEach var="cat" items="${categories}">
								<div class="panel panel-default category" code="${cat.id}">
												<c:if test="${!cat.active}">
																<div class="disable"></div>
												</c:if>
												<div class="panel-heading">${cat.name}</div>
												<div class="panel-body">
																<c:forEach var="item" items="${cat.itemList}">
																				<c:if test="${item.active}">
																								<div class="col-xs-4 item">
																												<div class="panel panel-info">
																																<div class="panel-heading">${item.name}</div>
																																<div class="panel-body">
																																				<c:if test="${item.hasUnits}">
																																								<c:set var="first" value="true"></c:set>
																																								<c:forEach var="unit" items="${item.itemUnits}">
																																												<c:if test="${!unit.isDefault}">
																																																<c:if test="${!first}">
																																																				<button class="btn btn-sm btn-info unit" code="${unit.id}">${unit.name}
																																																								<span class="badge">${unit.price}</span>
																																																				</button>
																																																</c:if>
																																																<c:if test="${first}">
																																																				<button class="btn btn-sm btn-success unit" code="${unit.id}">${unit.name}
																																																								<span class="badge">${unit.price}</span>
																																																				</button>
																																																				<c:set var="first" value="false"></c:set>
																																																</c:if>
																																												</c:if>
																																								</c:forEach>
																																				</c:if>
																																				<c:if test="${!item.hasUnits}">
																																								<button class="btn btn-sm btn-success" code="${item.defaultUnit.id}">${item.defaultUnit.name}
																																												<span class="badge">${item.defaultUnit.price}</span>
																																								</button>
																																				</c:if>
																																</div>
																																<div class="panel-footer">
																																				<button class="btn btn-group-justified btn-primary add-item">Add To Cart</button>
																																</div>
																												</div>
																								</div>
																				</c:if>
																</c:forEach>
												</div>
								</div>
				</c:forEach>
</div>
<div class="col-sm-3">
				<div class="panel panel-default" id="cart">
								<div class="panel-heading text-center">Cart
												<span class="icon">
																<span class="glyphicon glyphicon-trash clear-cart pull-right" data-toggle="tooltip" title="clear cart"></span>
												</span>
								</div>
								<div class="panel-body">
												<ul class="list-group">

												</ul>
								</div>
								<div class="panel-footer">
												<div id="price" class="text-center marginalise">Total: <span>0.0</span></div>
												<button class="btn btn-warning btn-group-justified">Create Bill</button>
								</div>
				</div>
</div>