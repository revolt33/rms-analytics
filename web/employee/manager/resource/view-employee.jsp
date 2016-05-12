<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="content-header bg-info">
				<h3 class="text-center">Employee List</h3>
				<div class="btn-group" >
								<c:url var="url" value="templates/addEmp.jsp"></c:url>
								<button type="button" class="btn btn-default load-button-with-url" url="${url}" param="" target="dialog" make-visible="overlay">Add Employee</button>
				</div>
</div>
<div>
				<table class="table table-striped table-hover">
								<thead>
												<tr>
																<th>Name</th>
																<th>Gender</th>
																<th>Mobile</th>
																<th>Email</th>
																<th>Type</th>
																<th>Active</th>
												</tr>
								</thead>
								<tbody>
												<c:forEach items="${emp_list}" var="emp" >
																<tr>
																				<c:url var="url" value="Action"></c:url>
																				<td>${emp.name}<span class="icon"><span class="glyphicon glyphicon-info-sign load-button-with-url" code="${emp.id}" title="View Details" data-toggle="tooltip" url="${url}" param="view_emp_detail&emp_id=${emp.id}" target="dialog" make-visible="overlay"></span></span></td>
																				<td>${emp.empGender}</td>
																				<td>${emp.mobile}</td>
																				<td>${emp.email}</td>
																				<td>${emp.empType}</td>
																				<td>
																								<span class="icon">
																												<c:url value="Action" var="emp_active_url" ></c:url>
																												<c:if test="${emp.isActive}">
																																<span>Yes</span>
																																<c:if test="${emp.id!=sessionScope['auth_token']['id']}">
																																				<span class="glyphicon glyphicon-ban-circle action-button-with-url" title="Deactivate" url="${emp_active_url}" target="view_emp" param="disable_emp" code="${emp.id}" data-toggle="tooltip"></span>
																																</c:if>
																												</c:if>
																												<c:if test="${!emp.isActive}">
																																<span>No</span>
																																<c:if test="${emp.id!=sessionScope['auth_token']['id']}">
																																				<span class="glyphicon glyphicon-ok-circle action-button-with-url" title="Activate" url="${emp_active_url}" target="view_emp" param="enable_emp" code="${emp.id}" data-toggle="tooltip"></span>
																																</c:if>
																												</c:if>
																								</span>
																				</td>
																</tr>
												</c:forEach>
								</tbody>
				</table>
</div>