K.UsuariosNuevoController = Ember.ObjectController.extend({
  titulo: function(){
    return this.get('model.nombre') ? this.get('model.nombre') : 'Nuevo usuario'; 
  }.property('model.nombre'),

  needs: ['empresas'],
  lista_empresas: [],
  editando: true,
  actions: {
    save: function(){
      this.get('model').save();
      this.transitionToRoute('usuarios');
    },
    descartar: function(){
      this.get('model').rollback();
      this.transitionToRoute('usuarios.index');
    }
  }
});

