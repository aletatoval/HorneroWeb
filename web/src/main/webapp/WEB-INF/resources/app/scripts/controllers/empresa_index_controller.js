K.EmpresaIndexController = Ember.ObjectController.extend({
  listado: false,
  mostrando: true,

  // titulo: function(){
  //   return this.get('model.nombre') ? this.get('model.nombre') : 'Empresa'; 
  // }.property('model.nombre'),

  actions: {
  	editar: function(){
  		this.transitionToRoute('empresa.edit');
  	},
    eliminar: function(){
      this.get('model').deleteRecord(); //marcar el modelo como "isDeleting"
      // desea eliminar? blablabla
      this.get('model').save(); // persistir el borrado
    }
  }
});