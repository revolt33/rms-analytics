$(document).ready(function (){
				$(document).on('blur', 'input[name=username]', function () {
								if ( $(this).val().length > 0 ) {
												var link = $(this).attr('url');
												var data = 'username=' + $(this).val();
												data += '&param=' + $(this).attr('param');
												$.ajax({
																url: link,
																data: data,
																type: 'POST',
																success: function (data) {
																				var json = $.parseJSON(data);
																				$('#username-status').removeAttr('class');
																				if ( json['status'] == 1 )
																								$('#username-status').addClass('glyphicon glyphicon-ok-sign').css('color','green');
																				 else
																									$('#username-status').addClass('glyphicon glyphicon-exclamation-sign').css('color', 'red');
																				$('#username-status').attr('data-original-title', json['message']);
																}
												});
								}
				});
});

