<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="bg-info content-header">
				<h3 class="text-center">Discounts</h3>
				<div class="btn-group">
								<c:url var="url" value="templates/addDiscount.jsp"></c:url>
								<button class="btn btn-default load-button-with-url" url="${url}" param="" target="dialog" make-visible="overlay">Add Discount</button>
				</div>
</div>