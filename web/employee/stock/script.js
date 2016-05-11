$(document).ready(function () {
				$('body').popover({
								content: reduceStockContent,
								placement: 'left',
								selector: '[data-toggle="popover"]',
								html: true
				});
				$(document).on('submit', 'form', function (event) {
								var proceed = true;
								$(this).find('input[type=text], textarea').each(function () {
												if (($(this).val()).trim().length < 1) {
																proceed = false;
																return false;
												}
								});
								if (proceed) {
												var link = $(this).attr('action');
												var close = $(this).attr('close');
												var method = $(this).attr('method');
												var formData = $(this).serialize();
												var postTarget = $(this).attr('post-target');
												if ( $(this).hasClass('reduce-popover') ) {
																var group = $(this).attr('group');
																var code = $(this).attr('code');
																$('.'+group).each(function () {
																				if ( $(this).attr('group-code') == code ) {
																								$(this).popover('destroy');
																				}
																});
												}
												submitForm(link, formData, close, method, postTarget, this);
								} else
												errorMessage('All fields are required!');
								event.preventDefault();
				});
				$(document).on('click', '.navigate-category', function () {
								var code = $(this).attr('code');
								$('.category').each(function () {
												if ($(this).attr('code') == code) {
																$('html, body').animate({scrollTop: $(this).offset().top}, '1000', 'swing');
												}
								});
				});
				function submitForm(link, data, close, method, postTarget, form) {
								$.ajax({
												type: method,
												url: link,
												data: data,
												success: function (data) {
																var json = $.parseJSON(data);
																var status = json['status'];
																var destruct = true;
																switch (parseInt(status)) {
																				case 1:
																								successMessage(json['message']);
																								$('.list-group-item').each(function () {
																												if ($(this).attr('param') == postTarget) {
																																$(this).trigger('click');
																																return false;
																												}
																								});
																								break;
																				case 9:
																								destruct = false;
																								if (confirm(json['message'])) {
																												$(form).find('input[name=override]').val('true');
																												$(form).submit();
																								}
																								break;
																				case 10:
																								successMessage(json['message']);
																								$('.list-group-item').each(function () {
																												if ($(this).attr('param') == postTarget) {
																																$(this).trigger('click');
																																return false;
																												}
																								});
																								load(json['url'], 'batch=' + json['batch'], 'top-dialog', 'top-overlay', 'GET', '');
																								break;
																				default:
																								errorMessage(json['message']);
																								break;
																}
																if ( destruct ) {
																				$('#overlay').find('.reloadable').empty();
																				if ( close.length > 0 )
																								$('#' + close).fadeOut(200);
																}
												},
												error: function () {
																errorMessage('Connection Error!');
												}
								});
				}
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