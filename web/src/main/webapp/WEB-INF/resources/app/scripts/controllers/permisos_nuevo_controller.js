K.PermisosNuevoController = Ember.ObjectController.extend({
  needs: ['permiso'],
  editando: true,
  titulo: function(){
    return this.get('model.nombre') ? this.get('model.nombre') : 'Nuevo permiso'; 
  }.property('model.nombre'),
  
  actions: {
    save: function(){
      var that = this;
      this.get('model').save().then(function(){
        that.transitionToRoute('permisos.index');
      });
    },
    descartar: function(){
      this.get('model').rollback();
      this.transitionToRoute('permisos.index');
    }
  },

  modulos: function(){
    return this.get('store').find('modulo', {_search: false, page: 0, rows: 0, filters: null, sidx:'nombre', sord: 'asc',  todos:true})
  }.property()

});