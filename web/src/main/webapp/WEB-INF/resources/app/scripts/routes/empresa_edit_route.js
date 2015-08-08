K.EmpresaEditRoute = Ember.Route.extend({
  model: function(params) {
    return this.get('store').find('empresa', this.modelFor('empresa').id);
  }
});

