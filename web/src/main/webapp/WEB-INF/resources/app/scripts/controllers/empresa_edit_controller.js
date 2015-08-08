K.EmpresaEditController = Ember.ObjectController.extend({
  needs: 'empresa',
  editando: true,
  titulo: function(){
    return this.get('model.nombre'); 
    }.property('model.nombre'),
  actions: {
    save: function(){
      this.get('model').save();
      this.transitionToRoute('empresas.index');
    },
    descartar: function(){
      this.get('model').rollback();
      this.transitionToRoute('empresas.index');
    }
  }
});

