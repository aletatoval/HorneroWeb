K.ModuloRoute = Ember.Route.extend({
	listado: false,
  	mostrando: true,
	titulo: function(){
    	return this.get('model.nombre') ? this.get('model.nombre') : 'Modulo'; 
  	}.property('model.nombre'),

  	actions: {
  		editar: function(){
  			this.transitionToRoute('modulo.edit');
  		},
	    eliminar: function(){
	      this.get('model').remove(); //marcar el modelo como "isDeleting"
	      // desea eliminar? blablabla
	      this.get('model').save(); // persistir el borrado
	    }
  	},
	model: function(params) {
    return this.get('store').find('modulo', params.modulo_id);
  	}
});
