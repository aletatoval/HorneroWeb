K.RolEditRoute = Ember.Route.extend({
  model: function(params) {
    return this.get('store').find('rol', this.modelFor('rol').id);
  }
});

