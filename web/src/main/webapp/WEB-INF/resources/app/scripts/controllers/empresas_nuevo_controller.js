K.EmpresasNuevoController = Ember.ObjectController.extend({
  needs: ['empresa'],
  editando: true,
  titulo: function(){
    return this.get('model.nombre') ? this.get('model.nombre') : 'Nueva empresa'; 
  }.property('model.nombre'),
  
  actions: {
    save: function(){
      var that = this;
      this.get('model').save().then(function(){
        that.transitionToRoute('empresas.index');
      });
    },
    descartar: function(){
      this.get('model').rollback();
      this.transitionToRoute('empresas.index');
    }
  },

});