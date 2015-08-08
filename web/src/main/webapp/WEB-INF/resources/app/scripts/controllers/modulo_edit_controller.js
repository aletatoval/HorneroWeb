K.ModuloEditController = Ember.ObjectController.extend({
  editando: true,
  titulo: function(){
    return this.get('model.nombre'); 
    }.property('model.nombre'),  
  actions: {
    save: function(){
      this.get('model').save();
      this.transitionToRoute('modulos.index');
    },
    descartar: function(){
      this.get('model').rollback();
      this.transitionToRoute('modulos.index');
    }
  }
});

