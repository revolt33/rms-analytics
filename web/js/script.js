$(document).ready(function () {
				$("#login-signup").click(function () {
								$("#overlay").removeClass("hidden").fadeIn(400);
				});
				$("#close").click(function () {
								$("#overlay").fadeOut(400);
				});
				$(window).scroll(function () {
								var position = $('#menu').offset().top;
								if ($(window).scrollTop() > (position - 50)) {
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
																var quantity = parseInt($(this).attr('quantity'));
																var price = parseFloat($(this).attr('price'));
																total = price;
																$(this).attr('quantity', ++quantity);
																$(this).children('.cart-item-price').text('Cost: ' + (quantity) + ' x ' + price + ' = ' + (price * quantity) + ' INR');
																return false;
												}
								});
								if (proceed) {
												var title = $(this).parent().children('.item-title').text();
												if ($(this).hasClass('has-units')) {
																var unit = $(this).parent().find('.btn-success');
																title += ' (' + unit[0].firstChild.nodeValue + ')';
												}
												var price = parseFloat($(this).parent().children('.item-price').attr('price'));
												var element = createCartItem(code, title, price, 1)
												$('#cart-content').append(element);
												$(element).slideDown(200);
												total = price;
								}
								refreshCart(parseInt(code), 1);
								var amount = parseFloat($('#total').attr('price'));
								refreshForm(amount + total);
				});
				$(document).on('click', '.remove-cart-item', function () {
								var quantity = parseInt($(this).parent().attr('quantity'));
								var price = parseFloat($(this).parent().attr('price'));
								$(this).parent().slideUp(300, function () {
												var amount = quantity * price;
												var total = parseFloat($('#total').attr('price'));
												refreshForm(total - amount);
												$(this).remove();
								});
								refreshCart(parseInt($(this).parent().attr('code')), 0);
				});
				$(document).on('click', '.item-unit', function () {
								$(this).parent().children().not(this).addClass('btn-info').removeClass('btn-success');
								$(this).removeClass('btn-info').addClass('btn-success');
								$(this).parent().parent().children('.item-price').text('Price: ' + $(this).children('.badge').text() + ' INR').attr('price', $(this).children('.badge').text());
								$(this).parent().parent().attr('code', $(this).attr('code'));
				});
				$(document).on('click', '.reduce-cart-item', function () {
								var quantity = parseInt($(this).parent().attr('quantity'));
								var price = parseFloat($(this).parent().attr('price'));
								if (quantity > 1) {
												$(this).parent().attr('quantity', --quantity);
												$(this).parent().children('.cart-item-price').text('Cost: ' + quantity + ' x ' + price + ' = ' + (quantity * price) + ' INR');
												var total = parseFloat($('#total').attr('price'));
												refreshForm(total - price);
								} else {
												$(this).parent().slideUp(300, function () {
																var amount = quantity * price;
																var total = parseFloat($('#total').attr('price'));
																refreshForm(total - amount);
																$(this).remove();
												});
								}
								refreshCart(parseInt($(this).parent().attr('code')), -1);
				});
				$('#checkout').submit(function (e) {
								if (parseFloat($('#total').attr('price')) <= 0) {
												e.preventDefault();
								}
				});
});
window.onload = function () {
				if (navigator.cookieEnabled) {
								var cart = readCookie('cart');
								var total = 0;
								if (cart != null && cart.getValue().length > 0) {
												var str = cart.getValue();
												str = str.substring(1, str.length - 1);
												var units = str.split('],[');
												for (var index in units) {
																var id = parseInt(units[index].split(',')[0]);
																var found = false;
																var title, price;
																$('.item').each(function () {
																				title = $(this).find('.item-title').text();
																				if ($(this).find('.add-item').hasClass('has-units')) {
																								$(this).find('.item-unit').each(function () {
																												if (parseInt($(this).attr('code')) === id) {
																																title += ' (' + $(this)[0].firstChild.nodeValue + ')';
																																price = parseFloat($(this).find('.badge').text());
																																found = true;
																																return false;
																												}
																								});
																				} else {
																								if (parseInt($(this).attr('code')) === id) {
																												price = parseFloat($(this).find('.item-price').attr('price'));
																												found = true;
																								}
																				}
																				if (found)
																								return false;
																});
																if (found) {
																				var quantity = parseInt(units[index].split(',')[1]);
																				var element = createCartItem(id, title, price, quantity);
																				$('#cart-content').append(element);
																				$(element).slideDown();
																				total += quantity * price;
																}
												}
												refreshForm(total);
								}
				}
				status_bar = $('#status-bar').clone();
				$('#status-bar').detach();
};
function createCartItem(code, title, price, quantity) {
				var cart_item = document.createElement('div');
				$(cart_item).attr('code', code).attr('price', price).attr('quantity', quantity).addClass('cart-item');
				var remove_cart_item = document.createElement('div');
				$(remove_cart_item).attr('title', 'Remove').addClass('remove-cart-item');
				var remove_cart_item_icon = document.createElement('span');
				$(remove_cart_item_icon).addClass('glyphicon glyphicon-trash');
				$(remove_cart_item).append(remove_cart_item_icon);
				var reduce_cart_item = document.createElement('div');
				$(reduce_cart_item).attr('title', 'Reduce').addClass('reduce-cart-item');
				var reduce_cart_item_icon = document.createElement('span');
				$(reduce_cart_item_icon).addClass('glyphicon glyphicon-scissors');
				$(reduce_cart_item).append(reduce_cart_item_icon);
				var cart_item_title = document.createElement('div');
				$(cart_item_title).addClass('cart-item-title').text(title);
				var cart_item_price = document.createElement('div');
				$(cart_item_price).addClass('cart-item-price').text('Cost: ' + quantity + ' x ' + price + ' = ' + (quantity * price) + ' INR');
				$(cart_item).append(remove_cart_item).append(reduce_cart_item).append(cart_item_title).append(cart_item_price);
				return cart_item;
}
function refreshCart(id, amount) {
				if (navigator.cookieEnabled) {
								var cart = readCookie('cart');
								if (cart === null)
												cart = new Cookie('cart', '', 30);
								var str = cart.getValue();
								var list = new String();
								if (str.length === 0 && amount > 0) {
												list = '[' + id + ',' + amount + ']';
								} else if (str.length > 0) {
												str = str.substring(1, str.length - 1);
												var units = str.split('],[');
												var found = false;
												for (var index in units) {
																if (parseInt(units[index].split(',')[0]) === id) {
																				if (amount === 0) {
																								units[index] = '' + id + ',0';
																				} else {
																								units[index] = '' + id + ',' + (parseInt(units[index].split(',')[1]) + amount);
																				}
																				found = true;
																				break;
																}
												}
												if (!found && amount > 0) {
																units.push('' + id + ',' + amount);
												}
												for (var index in units) {
																if (parseInt(units[index].split(',')[1]) > 0) {
																				list += (index > 0 ? ',' : '') + '[' + units[index] + ']';
																}
												}
								}
								writeCookie(new Cookie('cart', list, 30));
				} else
								return false;
}
function refreshForm(total) {
				$('#total').attr('price', (total));
				$('#total').text('Total: ' + (total) + ' INR');
				if ($('.cart-item').length > 0) {
								$('#checkout button').css({'cursor': 'pointer'});
				} else {
								$('#checkout button').css({'cursor': 'not-allowed'});
				}
				var form = $('#checkout');
				$(form).find('input').remove();
				$('.cart-item').each(function () {
								var element = document.createElement('input');
								$(element).attr('type', 'hidden').attr('name', 'unit[]').val(('' + $(this).attr('code') + ',' + $(this).attr('quantity')));
								$(form).append(element);
				});
}