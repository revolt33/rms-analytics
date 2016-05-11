$(document).ready(function () {
				$("#login-signup").click(function () {
								$("#overlay").fadeIn(400);
				});
				$("#close").click(function () {
								$("#overlay").fadeOut(200);
				});
				$(window).scroll(function () {
								var position = $('#menu').offset().top;
								if ($(window).scrollTop() > (position - 50) ){
												$('#navigation').removeClass('loose').addClass('hinge');
												$('#cart').removeClass('loose').addClass('hinge');
								} else {
												$('#navigation').removeClass('hinge').addClass('loose');
												$('#cart').removeClass('hinge').addClass('loose');
								}
								if ($(window).scrollTop() > position - 60) {
												$('nav').addClass('intensify');
								} else {
												$('nav').removeClass('intensify');
								}
				});
				$('.category').click(function () {
								$('.category').removeClass('selected');
								$(this).addClass('selected');
								var code = $(this).attr('code');
								$('.cat').each(function () {
												if ($(this).attr('code') == code) {
																$('html, body').stop().animate({scrollTop: ($(this).offset().top - 50)}, '1000', 'swing');
												}
								});
				});
				$('#down').click(function () {
								$('html, body').stop().animate({scrollTop: $('#showcase').offset().top - 50}, '400', 'swing');
				});
				$('.add-item').click(function () {
								var code = $(this).parent().attr('code');
								var proceed = true;
								var total = 0.0;
								$('.cart-item').each(function () {
												if ($(this).attr('code') == code) {
																proceed = false;
																var amount = parseInt($(this).attr('amount'));
																var price = parseFloat($(this).attr('price'));
																total = price;
																$(this).attr('amount', ++amount);
																$(this).children('.cart-item-price').text('Cost: ' + (amount) + ' x ' + price + ' = ' + (price * amount) + ' INR');
																return false;
												}
								});
								if (proceed) {
												var title = $(this).parent().children('.item-title').text();
												var price = parseFloat($(this).parent().children('.item-price').attr('price'));
												var amount = price;
												var str = '<div class="cart-item" code="' + code + '" price="' + price + '" amount="' + 1 + '">';
												str += '<div class="remove-cart-item" title="Remove"><span class="glyphicon glyphicon-trash"></span></div>';
												str += '<div class="reduce-cart-item" title="Reduce"><span class="glyphicon glyphicon-scissors"></span></div>'
												str += '<div class="cart-item-title">' + title + '</div>';
												str += '<div class="cart-item-price">Cost: 1 x ' + price + ' = ' + amount + ' INR</div>';
												str += '</div>';
												var element = $(str);
												$('#cart-content').append(element);
												$(element).slideDown(200);
												total = price;
								}
								var amount = parseFloat($('#total').attr('price'));
								$('#total').text('Total: ' + (amount + total) + ' INR');
								$('#total').attr('price', (amount + total));
								$('#checkout').css({'cursor': 'pointer'});
				});
				$(document).on('click', '.remove-cart-item', function () {
								var amount = parseInt($(this).parent().attr('amount'));
								var price = parseFloat($(this).parent().attr('price'));
								$(this).parent().slideUp(300, function () {
												var amount1 = amount * price;
												var total = parseFloat($('#total').attr('price'));
												$('#total').attr('price', (total - amount1));
												$('#total').text('Total: ' + (total - amount1) + ' INR');
												$(this).remove();
												if ($('.cart-item').length > 0) {
																$('#checkout').css({'cursor': 'pointer'});
												} else {
																$('#checkout').css({'cursor': 'not-allowed'});
												}
								});
				});
				$(document).on('click', '.reduce-cart-item', function () {
								var amount = parseInt($(this).parent().attr('amount'));
								var price = parseFloat($(this).parent().attr('price'));
								if (amount > 1) {
												$(this).parent().attr('amount', --amount);
												$(this).parent().children('.cart-item-price').text('Cost: ' + amount + ' x ' + price + ' = ' + (amount * price) + ' INR');
												var total = parseFloat($('#total').attr('price'));
												$('#total').attr('price', (total - price));
												$('#total').text('Total: ' + (total - price) + ' INR');
								} else {
												$(this).parent().slideUp(300, function () {
																var amount1 = amount * price;
																var total = parseFloat($('#total').attr('price'));
																$('#total').attr('price', (total - amount1));
																$('#total').text('Total: ' + (total - amount1) + ' INR');
																$(this).remove();
																if ($('.cart-item').length > 0) {
																				$('#checkout').css({'cursor': 'pointer'});
																} else {
																				$('#checkout').css({'cursor': 'not-allowed'});
																}
												});
								}
				});
				$('#checkout').click(function () {
								if (parseFloat($('#total').attr('price')) > 0) {
												console.log('checkout');
								} else {
												return false;
								}
				});
})