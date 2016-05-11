<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-sm-6 col-sm-offset-3">
				<div class="panel panel-primary">
								<div class="panel-heading text-center">Employee Detail
												<button type="button" class="close pull-right" target="overlay" aria-label="Close">
																<span aria-hidden="true">&times;</span>
												</button>
								</div>
								<div class="panel-body">
												<c:if test="${emp==null}">
																<div class="col-sm-12 bg-danger text-center">No such employee found!</div>
												</c:if>
												<c:if test="${emp!=null}" >
																<div class="col-sm-8">
																				<div class="col-sm-12 marginalise">
																								<div class="col-xs-4"><span class="pull-right">Name:</span></div>
																								<div class="col-xs-8">${emp.name}</div>
																				</div>
																				<div class="col-sm-12 marginalise">
																								<div class="col-xs-4"><span class="pull-right">Type:</span></div>
																								<div class="col-xs-8">${emp.empType}</div>
																				</div>
																				<div class="col-sm-12 marginalise">
																								<div class="col-xs-4"><span class="pull-right">Email:</span></div>
																								<div class="col-xs-8">${emp.email}</div>
																				</div>
																				<div class="col-sm-12 marginalise">
																								<div class="col-xs-4"><span class="pull-right">Mobile:</span></div>
																								<div class="col-xs-8">${emp.mobile}</div>
																				</div>
																				<div class="col-sm-12 marginalise">
																								<div class="col-xs-4"><span class="pull-right">Gender:</span></div>
																								<div class="col-xs-8">${emp.empGender}</div>
																				</div>
																				<div class="col-sm-12 marginalise">
																								<div class="col-xs-4"><span class="pull-right">Active:</span></div>
																								<div class="col-xs-8"><c:if test="${emp.isActive}">Yes</c:if><c:if test="${!emp.isActive}">No</c:if></div>
																								</div>
																								<div class="col-sm-12 marginalise">
																												<div class="col-xs-4"><span class="pull-right">DOB:</span></div>
																																<div class="col-xs-8">${emp.dob}</div>
																				</div>
																				<div class="col-sm-12 marginalise">
																								<div class="col-xs-4"><span class="pull-right">Address:</span></div>
																								<div class="col-xs-8">${emp.street}, ${emp.colony}, ${emp.city}, ${emp.state} - ${emp.zip}</div>
																				</div>
																</div>
																<c:url var="url" value="/img/emp/${emp.photo}"></c:url>
																<div class="col-sm-4"><img src="${url}" class="img-rounded" width="100%" /></div>
																</c:if>
								</div>
				</div>
</div>