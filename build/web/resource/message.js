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
				feedback.find('span').remove();
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