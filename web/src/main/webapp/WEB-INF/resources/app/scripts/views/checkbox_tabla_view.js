K.CheckboxTablaView = Ember.View.extend({
	classNameBindings: ['seleccionado:deep-orange', 'seleccionado:white-text'],
	tagName: 'tr',

	seleccionado: false,

	preSeleccionar: function(){
		var seleccionados = this.get('controller.seleccionados');
		var item = this.get('item')
		// console.log('seleccionados, iniciar en checkboxView', seleccionados);
		// console.log('objeto actual', this.get('item'));
		if(seleccionados.contains(item)){
			this.set('seleccionado', true);
			// console.log('pre-seleccionar!! módulo en empresa :D');
		}
	}.observes('controller.preSeleccionado').on('init'),

	icono: function(){
		return this.get('seleccionado') ? 'icon-check-circle' : 'icon-cancel-circle';
	}.property('seleccionado'),

	click: function(){
		// // console.log('click en ', this.get('item.nombre'));
		this.get('controller').send('seleccionar', this.get('item'));
		this.toggleProperty('seleccionado')
	}

});
