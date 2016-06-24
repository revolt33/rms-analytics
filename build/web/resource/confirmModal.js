function ConfirmModal(mode, param, content) {
				this.param = param;
				this.content = content;
				this.mode = mode;
};
ConfirmModal.prototype.show = function () {
				$('#modal').find('.modal-title').text(this.content['title']);
				$('#modal').find('.modal-body').text(this.content['body']);
				var cancel = document.createElement('button');
				$(cancel).addClass('btn btn-default').attr('data-dismiss', 'modal');
				cancel.textContent = "Cancel";
				$('#modal').find('.modal-footer').empty();
				$('#modal').find('.modal-footer').append(cancel);
				var okay = document.createElement('button');
				$(okay).addClass('btn btn-primary');
				okay.textContent = this.content['okay'];
				var confirmModal = this;
				okay.addEventListener('click', function () {
								confirmModal.execute();
				});
				$('#modal').find('.modal-footer').append(okay);
				$('#modal').modal('show');
};
ConfirmModal.prototype.execute = function () {
				$('#modal').modal('hide');
				switch (this.mode) {
								case "action":
												action(this.param);
												break;
				}
};