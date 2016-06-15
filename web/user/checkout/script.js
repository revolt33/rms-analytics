$(document).ready(function () {
				$('#coupon').keyup(function () {
								$(this).val($(this).val().toUpperCase());
				});
				$('#browse-coupons').click(function (){
								var link = $(this).attr('url');
								$('#load-screen').fadeIn();
								$.ajax({
												url: link,
												type: 'POST',
												success: function (data) {
																$('#dialog').find('.modal-body').html(data);
																$('#load-screen').fadeOut();
																$('#dialog').modal('show');
												},
												error: function () {
																$('#load-screen').fadeOut();
																errorMessage('Connection Error!')
												}
								});
				});
				$(document).on('click','.use-coupon',function () {
								var code = $(this).attr('code');
								$('#coupon').val(code);
								$('#dialog').modal('hide');
				});
});
window.onload = function () {
				$("body").tooltip({
								selector: '[data-toggle="tooltip"]'
				});
				status_bar = $('#status-bar').clone();
				$('#status-bar').detach();
};
function errorMessage(message) {
				var sign = document.createElement('span');
				$(sign).addClass('glyphicon glyphicon-remove-sign pull-left');
				displayMessage('alert-danger', sign, message);
}
function successMessage(message) {
				var sign = document.createElement('span');
				$(sign).addClass('glyphicon glyphicon-ok-circle pull-left');
				displayMessage('alert-success', sign, message);
}
function displayMessage(alert_class, sign, message) {
				$('#status-bar').remove();
				var feedback = status_bar.clone();
				$('body').append(feedback);
				feedback.addClass(alert_class);
				$(feedback).append(sign);
				var element = document.createElement('span');
				element.textContent = message;
				$(feedback).append(element);
				feedback.fadeIn(500).delay(3000).fadeOut(300, function () {
								feedback.remove();
				});
}