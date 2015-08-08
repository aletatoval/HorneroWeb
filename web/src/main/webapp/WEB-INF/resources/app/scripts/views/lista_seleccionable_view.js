K.ListaSeleccionableView = Ember.View.extend({
	tagName: 'ul',
	classNames: ['collection', 'lista-seleccionable'],
	actualizar: function(){
		this.rerender();
	}.on('actualizarOpciones')
});


K.CheckboxListaView = Ember.View.extend({
	classNames: ['collection-item'],
	classNameBindings: ['seleccionado:teal', 'seleccionado:lighten-2', 'seleccionado:white-text'],
	tagName: 'li',

	seleccionado: false,

	preSeleccionar: function(){
		var seleccionados = this.get('controller.seleccionados');
		if(seleccionados == undefined){
			return;
		}
		if(seleccionados.contains(this.get('item'))){
			this.set('seleccionado', true);
		}
	}.observes('controller.seleccionados').on('init'),

	icono: function(){
		return this.get('seleccionado') ? 'icon icon-check' : '';
	}.property('seleccionado'),

	click: function(){
		var accion = this.get('accion') ? this.get('accion') : 'seleccionar';
		this.get('controller').send(accion, this.get('item'));
		this.toggleProperty('seleccionado')
	}

});