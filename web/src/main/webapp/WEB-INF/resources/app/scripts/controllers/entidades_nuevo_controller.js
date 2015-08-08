K.EntidadesNuevoController = Ember.ObjectController.extend({
  needs: ['entidad'],
  editando: true,
  titulo: function(){
    return this.get('model.nombre') ? this.get('model.nombre') : 'Nueva entidad'; 
  }.property('model.nombre'),
  
  actions: {
    save: function(){
      var that = this;
      this.get('model').save().then(function(){
        that.transitionToRoute('entidades.index');
      });
    },
    descartar: function(){
      this.get('model').rollback();
      this.transitionToRoute('entidades.index');
    }
  },

});