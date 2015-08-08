K.BotonBuscarView = Ember.View.extend({
	templateName: 'partes/boton_buscar',
	classNames: 'buscador',
	classNameBindings: ['buscarSimpleActivo:activo'],
	inputBuscarSimple: function(){
		return this.get('controller.buscarSimpleValor')
	}.property('controller.buscarSimpleValor'),
	inputConValor: function(){
		return this.get('inputBuscarSimple') == "" ? false : true;
	}.property('inputBuscarSimple'),

	buscarSimpleActivo: false,


	didInsertElement: function(){
		// if(this.get('controller.buscarSimpleValor')
	},

	// buscar: function(){
	// 	this.get('controller').send('buscarSimple', this.get('inputBuscarSimple'))
	// }.observes('inputBuscarSimple'),

	mouseEnter: function(evt){
		this.set('buscarSimpleActivo',true);
	},
	mouseLeave: function(evt){
		if(!this.get('inputConValor')){
			this.set('buscarSimpleActivo',false);
		}
	},
	actions: {
		limpiarInput: function(){
			this.set('inputBuscarSimple', "");
			this.set('controller.buscarSimpleValor', '');
			this.get('controller').send('resetLista');
		}
	}
});
