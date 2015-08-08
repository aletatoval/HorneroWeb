K.InputValidableView = Ember.TextField.extend({
	classNames: ['form-control'],

	validarInput: function() {
		var v = this.get('value');

		if(this.get('required') && v == ""){
			this.set('conError', true);
			this.set('parentView.conError', true);
			this.set('parentView.mensajeError','Este campo es requerido');
		} else {
			this.set('conError', false);
			this.set('parentView.conError', false);
			this.set('parentView.mensajeError','');			
		}
	}.observes('value')
});