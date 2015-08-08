K.RolPermisosRoute = Ember.Route.extend({
  model: function(params) {
    return Ember.RSVP.hash({
      rol: this.get('store').find('rol', this.modelFor('rol').id),
      lista_permisos: this.store.find('permiso', {_search: false, page: 1, rows: 10, filters: null, sidx:'nombre', sord: 'asc',  todos:true}),
      lista_entidades: this.store.find('entidad', {_search: false, page: 1, rows: 10, filters: null, sidx:'nombre', sord: 'asc',  todos:true})
    });
  },
  setupController: function(controller, model) {
    controller.set('model', model.rol);
    controller.set('seleccionados',model.rol.get('permisos'));
    controller.set('lista_permisos', model.lista_permisos);
    controller.set('lista_entidades', model.lista_entidades);
  }
});

