K.PermisoEditRoute = Ember.Route.extend({
  model: function(params) {
    return this.get('store').find('permiso', this.modelFor('permiso').id);
  }
});

