<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:forEach var="coupon" items="${requestScope.coupons}" varStatus="count">
				${count.index%2==0?'<div class="row">':''}
				<div class="col-sm-6">
								<div class="panel panel-default">
												<div class="panel-heading">Coupon Code: ${coupon.code}</div>
												<div class="panel-body">
																<p>Off: ${coupon.percentage} %</p>
																<p>Max Limit: ${coupon.max} INR</p>
																<c:if test="${coupon.itemList}">
																				<p>Valid with: ${coupon.items}</p>
																</c:if>
																<c:if test="${!coupon.itemList}">
																				<p>Valid with: All Items</p>
																</c:if>
												</div>
												<div class="panel-footer">
																<button class="btn btn-group-justified btn-warning use-coupon" code="${coupon.code}">Use this coupon</button>
												</div>
								</div>
				</div>
				${count.index%2==1?'</div>':''}
</c:forEach>