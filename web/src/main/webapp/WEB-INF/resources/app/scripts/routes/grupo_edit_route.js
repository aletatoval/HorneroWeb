K.GrupoEditRoute = Ember.Route.extend({
  model: function(params) {
    return this.get('store').find('grupo', this.modelFor('grupo').id);
  }
});
