$(document).ready(function (){
				$(document).on('blur', '.verify-input', function () {
								if ( $(this).val().length > 0 ) {
												var link = $(this).attr('url');
												var data = 'input=' + $(this).val();
												data += '&param=' + $(this).attr('param');
												$.ajax({
																url: link,
																data: data,
																type: 'POST',
																success: function (data) {
																				var json = $.parseJSON(data);
																				console.log(json['message']);
																				$('#verify-input-status').removeAttr('class');
																				if ( json['status'] == 1 )
																								$('#verify-input-status').addClass('glyphicon glyphicon-ok-sign').css('color','green');
																				 else
																									$('#verify-input-status').addClass('glyphicon glyphicon-exclamation-sign').css('color', 'red');
																				$('#verify-input-status').attr('data-original-title', json['message']).tooltip('fixTitle');
																}
												});
								} else
												$('#verify-input-status').removeAttr('class');
				});
				$('body').popover({
								content: viewDiscountItemList,
								placement: 'left',
								selector: '[data-toggle="popover"]',
								html: false
				});
});
function viewDiscountItemList() {
				var text = $(this).parent().find('.hidden').text();
				console.log(text);
				text = text.substring(0, text.lastIndexOf(','));
				return text;
}

