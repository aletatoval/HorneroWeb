K.UsuarioEditController = Ember.ObjectController.extend({
  editando: true,
  titulo: function(){
    return this.get('model.nombre'); 
  }.property('model.nombre'),
  lista_empresas: [],
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

