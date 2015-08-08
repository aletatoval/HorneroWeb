K.CheckboxSimpleView = Ember.View.extend({
	templateName: 'partes/checkbox_simple',
	seleccionado: false,
	idInput: '',
	asignarId: function(){
		return 'input_' + parseInt(Math.random() * 1000000);
	}.on('init'),
	actions: {
		seleccionar: function(){
			this.toggleProperty('seleccionado');
			var modelo = this.get('modelo');
			var accion = this.get('accion');
			var n = this.get('params');
			if(n == 1){
				this.get('controller').send(accion, this.get('param1'));
			} else if(n == 2) {
				this.get('controller').send(accion, this.get('param1'), this.get('param2'));
			}
		}
	}
});