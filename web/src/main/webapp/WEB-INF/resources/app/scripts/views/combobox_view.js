var filtrosDefault = {_search: false, page: 1, rows: 10, filters: null, sidx:'nombre', sord: 'asc',  todos:true};

K.ComboboxView = Ember.View.extend({
	// Propiedades del elemento HTML
	templateName: 'partes/combobox',

	seleccionado: false, // se seleccionó alguna opción?

	// Propiedades del Combobox
	valorInput: '',
	mostrarOpciones: false,

	inputConTexto: function(){
		var a = this.get('valorInput') != "" ? true : false;
		if(this.get('mostrarOpciones')) a = true;
		return a;
	}.property('{valorInput,mostrarOpciones}'),

	// listaCompleta: {},
	// obtenerLista: function(){ // no hace nada cuando seleccionado es true
	// 	var that = this;
	// 	var filtro = filtrosDefault;
	// 	var filters = {groupOp: "OR"}; 
		
	// 	if(this.get('seleccionado')) return;

	// 	if(this.get('valorInput') != ""){
	// 		filtro.search = true;
	// 		filters.rules = [{field: "nombre", data: this.get('valorInput'), op: "cn"}];
	// 		filtro.filters = JSON.stringify(filters);
	// 	} 
	// 	this.get('controller.store').find(this.get('recurso'), filtro).then(function(data){
	// 		var ops = [];
	// 		Em.$(data.content).each(function(i, item){
	// 			ops.push({id: item._data.id,nombre: item._data.nombre});
	// 		});
	// 		that.set('opciones', ops);
	// 		that.set('mostrarOpciones', true);
	// 	});
	// }.observes('valorInput').on('init'),

	opciones: function(){
		return this.get('controller.lista_empresas'); //Cambiar por una propiedad más genérica
	}.property('controller.lista_empresas'),

	actions: {	
		desplegar: function(){
			this.set('mostrarOpciones', true);
		},
		plegar: function(){
			this.set('mostrarOpciones', false);
			this.set('seleccionado', false);
			this.set('inputComboInstancia.value', '');
			var e = this.get('recurso').toLowerCase();
			var modelo = this.get('controller').get('model');
			modelo.set(e, null);
		},
		buscar: function(valor){
			this.set('valorInput', valor);
			this.send('desplegar');
		},
		seleccionar: function(opcion){
			// console.log('empresa seleccionada', opcion);
			var e = this.get('recurso').toLowerCase();
			var s;
			var modelo = this.get('controller').get('model');
			this.set('seleccionado', true);
			this.set('inputComboInstancia.value', opcion.get('nombre'));
			this.set('mostrarOpciones', false);
			modelo.set(e, opcion); 
			// this.get('controller.store').find(e, opcion.id).then(function(item){
			// 	modelo.set(e, item.content);
			// })
			// this.get('controller').get('model').set(e, this.get('controller.store').find(e, opcion.id));
			// this.get('controller').get('model').set(e, opcion.id);
		}
	},


});

K.InputComboView = Ember.TextField.extend({
	classNames: ['form-control'],
	seleccionado: false,
	observarValor: function(){
		this.set('parentView.valorInput', this.get('value'));
	}.observes('value')
});

K.ListaOpcionesView = Ember.View.extend({
	tagName: 'ul',
	classNames: ['dropdown-menu'],
	attributeBindings: ['style'],
	style: function(){
		return (this.get('parentView.mostrarOpciones') ? 'display: block' : 'display: none');
	}.property('parentView.mostrarOpciones'),
	opciones: function(){
		return this.get('parentView.opciones');
	}.property('parentView.opciones'),
	seleccionado: {},
	actions: {
		seleccionar: function(opcion){
			this.get('parentView').send('seleccionar', opcion);
		}
	}
});