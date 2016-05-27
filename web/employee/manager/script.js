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
																				$('#verify-input-status').removeAttr('class');
																				if ( json['status'] == 1 )
																								$('#verify-input-status').addClass('glyphicon glyphicon-ok-sign').css('color','green');
																				 else
																									$('#verify-input-status').addClass('glyphicon glyphicon-exclamation-sign').css('color', 'red');
																				$('#verify-input-status').attr('title', json['message']);
																}
												});
								} else
												$('#verify-input-status').removeAttr('class');
				});
});

