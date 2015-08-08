K.DestinatariosNuevoController = Ember.ObjectController.extend({
  needs: ['entidad'],
  empresa_elegida: {},
  lista_empresas: [],
  
  editando: true,
  titulo: function(){
    return this.get('model.nombre') ? this.get('model.nombre') : 'Nuevo destinatario'; 
  }.property('model.nombre'),
  
  actions: {
    save: function(){
      var that = this;
      var model = this.get('model');
      model.set('empresa', this.get('empresa_elegida'));
      model.save().then(function(){
        that.transitionToRoute('destinatarios.index');
      });
    },
    descartar: function(){
      this.get('model').rollback();
      this.transitionToRoute('destinatarios.index');
    }
  },

});