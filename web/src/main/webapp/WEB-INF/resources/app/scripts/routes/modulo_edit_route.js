K.ModuloEditRoute = Ember.Route.extend({
  model: function(params) {
    return this.get('store').find('modulo', this.modelFor('modulo').id);
  },
});