$(document).ready(function () {
				$(document).on('scroll', function () {
								var scroll = $(window).scrollTop();
								var height = Math.max(window.innerHeight || document.documentElement.clientHeight || 1);
								if (scroll > (height / 2)) {
												$('#scroll-top').stop().fadeIn();
								} else {
												$('#scroll-top').stop().fadeOut();
								}
				});
				$(document).on('click', '.close', function () {
								var target = $(this).attr('target');
								if (!$(this).hasClass('status-close')) {
												if (target.length > 0) {
																$('#' + target).fadeOut(200);
																$('#' + target).find('.reloadable').empty();
												}
								}
				});
				$('#scroll-top span').click(function () {
								$('html, body').stop().animate({scrollTop: 0}, '400', 'swing');
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
												if ($(this).hasClass('reduce-popover')) {
																var group = $(this).attr('group');
																var code = $(this).attr('code');
																$('.' + group).each(function () {
																				if ($(this).attr('group-code') == code) {
																								$(this).popover('destroy');
																				}
																});
												}
												submitForm(link, formData, close, method, postTarget, this);
								} else
												errorMessage('All fields are required!');
								event.preventDefault();
				});
				$(document).on('click', '.load-button-with-url', function () {
								var link = $(this).attr('url');
								var param = $(this).attr('param');
								param = param.length > 0 ? 'param=' + param : '';
								var target = $(this).attr('target');
								var makeVisible = $(this).attr('make-visible');
								var method = 'GET';
								load(link, param, target, makeVisible, method, "");
				});
				$(document).on('click', '.load-link-with-param', function () {
								var link = $(this).attr('href');
								var param = $(this).attr('param');
								writeCookie(new Cookie('link_'+page, param, 30));
								param = 'param=' + param;
								var target = $(this).attr('target');
								var makeVisible = "";
								var loadScreen = "overlay";
								var method = 'GET';
								$('.load-link-with-param').not(this).removeClass('active');
								$(this).addClass('active');
								load(link, param, target, makeVisible, method, loadScreen);
								return false;
				});
});
window.onload = function () {
				page = $('body').attr('page');
				var found = false;
				var cookie = readCookie('link_' + page);
				if (cookie !== null) {
								var link = cookie.getValue();
								if (link.length > 0) {
												$('.load-link-with-param').each(function () {
																if ($(this).attr('param') === link) {
																				$(this).trigger('click');
																				found = true;
																				return false;
																}
												});
								}
				}
				if (!found)
								$($('.load-link-with-param')[0]).trigger('click');
				status_bar = $('#status-bar').clone();
				$('#status-bar').detach();		
};
function submitForm(link, data, close, method, postTarget, form) {
				$.ajax({
								type: method,
								url: link,
								data: data,
								success: function (data) {
												console.log(data);
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
												if (destruct) {
																$('#overlay').find('.reloadable').empty();
																if (close.length > 0)
																				$('#' + close).fadeOut(200);
												}
								},
								error: function () {
												errorMessage('Connection Error!');
								}
				});
}
function load(link, param, target, makeVisible, method, loadScreen) {
				$('#dialog, #content-top-dialog').empty();
				$('#content-top-overlay').hide();
				$('#load-screen').fadeIn();
				if (makeVisible != "") {
								$('#' + makeVisible).fadeIn(300);
				}
				if (loadScreen.length > 0) {
								$('#' + loadScreen).fadeIn(300);
				}
				$.ajax({
								type: method,
								url: link,
								data: param,
								success: function (data) {
												$('#load-screen').fadeOut();
												$('#' + target).html(data);
												$('#scroll-top span').trigger('click');
												if (loadScreen.length > 0) {
																$('#' + loadScreen).fadeOut(300);
												}
								},
								error: function () {
												$('#load-screen').fadeOut();
												errorMessage('Connection Error!');
												if (makeVisible != "") {
																$('#' + makeVisible).fadeOut(300);
												}
												if (loadScreen.length > 0) {
																$('#' + loadScreen).fadeOut(300);
												}
								}
				});
}
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