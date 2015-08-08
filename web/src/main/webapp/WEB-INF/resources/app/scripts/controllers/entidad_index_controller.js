K.EntidadesIndexController = Ember.ObjectController.extend({
  listado: false,
  mostrando: true,

    actions: {
  	editar: function(){
  		this.transitionToRoute('entidad.edit');
  	},
    eliminar: function(){
      this.get('model').deleteRecord(); //marcar el modelo como "isDeleting"
      // desea eliminar? blablabla
      this.get('model').save(); // persistir el borrado
    }
  }
});
