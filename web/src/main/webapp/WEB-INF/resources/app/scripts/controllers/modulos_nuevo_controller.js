K.ModulosNuevoController = Ember.ObjectController.extend({
  needs: ['modulo'],
  editando: true,
  titulo: function(){
    return this.get('model.nombre') ? this.get('model.nombre') : 'Nuevo modulo'; 
  }.property('model.nombre'),
  
  actions: {
    save: function(){
      var that = this;
      this.get('model').save().then(function(){
        that.transitionToRoute('modulos.index');
      });
    },
    descartar: function(){
      this.get('model').rollback();
      this.transitionToRoute('modulos.index');
    }
  },

});