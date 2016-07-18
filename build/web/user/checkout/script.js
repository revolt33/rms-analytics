$(document).ready(function () {
				$('#coupon').keyup(function () {
								$(this).val($(this).val().toUpperCase());
				});
				$('.load-dialog').click(function (){
								var link = $(this).attr('url');
								$('#dialog').find('.modal-title').text($(this).attr('set-title'));
								$('#load-screen').fadeIn();
								$.ajax({
												url: link,
												type: 'GET',
												success: function (data) {
																$('#dialog').find('.modal-body').html(data);
																$('#load-screen').fadeOut();
																$('#dialog').modal('show');
												},
												error: function () {
																$('#load-screen').fadeOut();
																errorMessage('Connection Error!');
												}
								});
				});
				$(document).on('click','.use-coupon',function () {
								var code = $(this).attr('code');
								$('#coupon').val(code);
								$('#dialog').modal('hide');
				});
				$(document).on('click', '.deliver', function () {
								var code = $(this).attr('code');
								$('#address').val(code);
								$('#delivery-address').find('.panel-body').html($(this).parent().parent().find('.panel-body').html());
								$('#dialog').modal('hide');
								$('#delivery-mode').val('d');
								$('#address-code').val(code);
								$('#delivery-address').slideDown();
				});
				$('#pickup').click(function () {
								$('#address').val(0);
								$('#address-code').val(0);
								$('#delivery-mode').val('p');
								$('#delivery-address').slideUp(function () {
												$('#delivery-address').find('.panel-body').empty();	
								});
				});
				$('#place-order').submit(function (e){
								if ( $('#home-delivery').is(':checked') ) {
												if ( parseInt($('#address').val()) === 0 ) {
																e.preventDefault();
																errorMessage('Please select a delivery address first.');
												}
								}
				});
});
window.onload = function () {
				$("body").tooltip({
								selector: '[data-toggle="tooltip"]'
				});
				status_bar = $('#status-bar').clone();
				$('#status-bar').detach();
};