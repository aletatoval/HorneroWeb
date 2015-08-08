K.RolesNuevoController = Ember.ObjectController.extend({
  editando: true,
  titulo: function(){
    return this.get('model.nombre') ? this.get('model.nombre') : 'Nuevo rol'; 
  }.property('model.nombre'),
  
  actions: {
    save: function(){
      var that = this;
      this.get('model').save().then(function(){
        that.transitionToRoute('roles.index');
      });
    },
    descartar: function(){
      this.get('model').rollback();
      this.transitionToRoute('roles.index');
    }
  },

});