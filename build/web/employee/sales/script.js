$(document).ready(function () {
				item_results = new Array();
				search_index = 0;
				$(document).on('click', '.navigate-category', function () {
								var code = $(this).attr('code');
								$('.category').each(function () {
												if ($(this).attr('code') == code) {
																$('html, body').animate({scrollTop: $(this).offset().top}, '1000', 'swing');
												}
								});
				});
				$(document).on('click', '.unit', function () {
								$(this).parent().children('.btn-success').removeClass('btn-success').addClass('btn-info');
								$(this).removeClass('btn-info').addClass('btn-success');
				});
				$(document).on('click', '.add-item', function () {
								var exists = false;
								var unit = $(this).parent().parent().find('.btn-success');
								$('#cart').find('ul').children('li').each(function () {
												var code = $(this).attr('code');
												if( code == $(unit).attr('code') ) {
																var count = parseInt($(this).find('.badge').text());
																count++;
																$(this).find('.badge').text(count);
																exists = true;
																return false;
												}
								});
								if ( !exists ) {
												var element = document.createElement('li');
												var title = document.createElement('span');
												title.textContent	=	$(this).parent().parent().find('.panel-heading').text() + " ("+$(unit).text()+")";
												$(element).html(title);
												var menu = document.createElement('div');
												var icon = document.createElement('span');
												$(menu).addClass('cart-menu');
												var reduce = document.createElement('span');
												reduce.title = 'reduce';
												$(reduce).addClass('glyphicon');
												$(reduce).attr('data-toggle', 'tooltip');
												var remove = reduce.cloneNode(true);
												remove.title = 'remove';
												$(reduce).addClass('glyphicon-minus-sign reduce-cart-item');
												$(remove).addClass('glyphicon-trash remove-cart-item');
												$(icon).addClass('icon').append(reduce).append(remove);
												$(menu).append(icon);
												$(element).append(menu);
												var badge = document.createElement('span');
												badge.textContent = "1";
												$(badge).addClass('badge');
												$(element).append(badge).attr('code', $(unit).attr('code')).attr('price', $(unit).find('.badge').text());
												$(element).addClass('list-group-item');
												$('#cart').find('ul').append(element);
												$(element).slideDown();
								}
								total();
				});
				$(document).on('click', '.reduce-cart-item', function () {
								var target = $(this).parent().parent().parent();
								var count = parseInt(target.find('.badge').text());
								count--;
								if ( count === 0 ) {
												target.slideUp(function () {
																target.remove();
																total();
												});
								} else {
												target.find('.badge').text(count);
												total();
								}
								
				});
				$(document).on('click', '.clear-cart', function () {
								$('#cart').find('ul').children('li').slideUp(function (){
												$('#cart').find('ul').empty();
												total();
								});
				});
				$(document).on('click', '.remove-cart-item', function () {
								var target = $(this).parent().parent().parent();
								target.slideUp(function (){
												target.remove();
												total();
								});
				});
				function total() {
								var total = 0.0;
								$('#cart').find('li').each(function () {
												total += parseFloat($(this).attr('price')) * parseInt($(this).find('.badge').text());
								});
								$('#price').find('span').text(total);
				}
				$(document).on('keyup', '#search-item', function (e) {
								var text = $(this).val();
								text = text.toLowerCase();
								text = text.trim();
								if ( text.length > 0 && e.which === 13) {
												item_results.length = 0;
												$('.item').each(function () {
																var current = $(this).find('.panel-heading').text();
																current = current.toLowerCase();
																if (current.indexOf(text) >= 0) {
																				item_results.push(this);
																}
												});
												if ( item_results.length > 0) {
																search_index = search_index < item_results.length ? search_index : 0;
																$('.item').children('.panel').removeClass('panel-warning').addClass('panel-info');
																$(item_results[search_index]).children('.panel').removeClass('panel-info').addClass('panel-warning');
																$('html, body').stop().animate({scrollTop: $(item_results[search_index]).offset().top}, '200', 'swing');
																search_index++;
												} else
																search_index = 0;
								} else {
												$('.item').children('.panel').removeClass('panel-warning').addClass('panel-info');
												search_index = 0;
								}
				});
});