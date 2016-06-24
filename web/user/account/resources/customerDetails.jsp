<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="content-header bg-info">
				<h3 class="text-center">User Details</h3>
				<c:url var="url" value="templates/updateUserDetails.jsp"></c:url>
				<button class="btn btn-default load-button-with-url" url="${url}" param="" target="dialog" make-visible="overlay">Update Contact</button>
				<c:url var="url" value="templates/password.jsp"></c:url>
				<button class="btn btn-default load-button-with-url pull-right" url="${url}" param="" target="dialog" make-visible="overlay" >Change Password</button>
</div>
<div>
				<table class="table table-striped">
								<tbody>
												<tr>
																<td>Name</td>
																<td>${requestScope.user_details.name}</td>
												</tr>
												<tr>
																<td>Mobile</td>
																<td>${requestScope.user_details.contact}</td>
												</tr>
												<tr>
																<td>Email</td>
																<td>${requestScope.user_details.email}</td>
												</tr>
												<tr>
																<td>Birthday</td>
																<td>${requestScope.user_details.dob}</td>
												</tr>
												<tr>
																<td>Username</td>
																<td>${requestScope.user_details.username}</td>
												</tr>
								</tbody>
				</table>
</div>