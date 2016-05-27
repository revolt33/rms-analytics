$(document).ready(function () {
				$('body').popover({
								content: reduceStockContent,
								placement: 'left',
								selector: '[data-toggle="popover"]',
								html: true
				});
				
				$(document).on('click', '.navigate-category', function () {
								var code = $(this).attr('code');
								$('.category').each(function () {
												if ($(this).attr('code') == code) {
																$('html, body').animate({scrollTop: $(this).offset().top}, '1000', 'swing');
												}
								});
				});
				
});
function reduceStockContent() {
				var url = $(this).attr('url');
				var code = $(this).attr('code');
				var batch = $(this).attr('batch');
				var param = $(this).attr('param');
				var group = $(this).attr('group');
				var groupCode = $(this).attr('group-code');
				var close = $(this).attr('close');
				var postTarget = $(this).attr('post-target');
				var str = "<div class='col-xs-10 col-xs-offset-1'><form class='form-horizontal reduce-popover' group='"+group+"' code='"+groupCode+"' action='"+url+"' method='POST' post-target='"+postTarget+"' close='"+close+"'>";
				str += "<div class='form-group'><label for='reduce-item-stock' class='control-label'>Amount</label>";
				str += "<input type='number' step='0.001' class='form-control' placeholder='Amount to be reduced' name='amount' required/>";
				str += "</div>";
				str += "<input type='hidden' name='param' value='"+param+"' /><input type='hidden' name='code' value='"+code+"' /><input type='hidden' name='batch' value='"+batch+"' />";
				str += "<div class='form-group'>";
				str += "<button type='submit' class='btn btn-warning btn-group-justified'>Reduce</button>";
				str += "</div></form></div>";
				return str;
}