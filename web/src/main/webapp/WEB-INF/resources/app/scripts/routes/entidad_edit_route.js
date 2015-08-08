K.EntidadEditRoute = Ember.Route.extend({
  model: function(params) {
    return Ember.RSVP.hash({
      entidad: this.get('store').find('entidad', this.modelFor('entidad').id),
      lista_modulos: this.store.find('modulo', {_search: false, page: 1, rows: 10, filters: null, sidx:'nombre', sord: 'asc',  todos:true})
    });
  },
  setupController: function(controller, model) {
    controller.set('model', model.entidad);
    controller.set('lista_modulos', model.lista_modulos);
  }
});

