<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="content-header bg-info">
				<h3 class="text-center">Your Addresses</h3>
				<c:url var="url" value="templates/addAddress.jsp"></c:url>
				<button type="button" class="btn btn-default load-button-with-url" url="${url}" param="" target="dialog" make-visible="overlay">Add new Address</button>
</div>