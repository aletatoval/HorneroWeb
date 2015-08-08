K.FiltrosView = Ember.View.extend({
	templateName: 'partes/contenedor_filtros',
	classNameBindings: ['ocultarFiltros:hide'],


  ocultarFiltros: function(){
  	return this.get('controller.buscarAvanzadoActivo') ? false : true;
  }.property('controller.buscarAvanzadoActivo'),

	campos: function(){
		var def = new Array();
    Ember.get(K[this.get('modelo')], 'attributes').forEach(function(name, meta) {
      if(name.options.filtrable)
        def.push({nombre: meta, options: name.options});
    });
    return def;
	}.property('modelo')

  // actions: {
  //   agregarCampo: function(obj){
  //     var a = this.get('objeto');
  //     a.push(obj);
  //   }
  // }

  // inputFiltroFormView = Ember.View.extend({
  //   didInsertElement: function(){
  //     this.get('parent').set('objeto', this.get('name'));
  //   }
  // })
});