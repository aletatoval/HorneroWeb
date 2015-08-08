K.EmpresaRolNuevoController = Ember.ObjectController.extend(Ember.Evented, {
  titulo: function(){
    return this.get('model.nombre');
  }.property('model'),
  listado: false,
  editando: true,
  mostrando: false,

  esValido: false,
  preSeleccionado: new Date(),

  actions: {
    save: function(){
      var that = this;
      this.get('model').save().then(function(){
        that.transitionToRoute('empresas.roles.index');
      });
    },
    descartar: function(){
      this.get('model').rollback();
      this.transitionToRoute('empresas.roles.index');
    }
  }
});
