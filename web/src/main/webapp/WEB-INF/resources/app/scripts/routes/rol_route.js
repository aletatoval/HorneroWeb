K.RolRoute = Ember.Route.extend({
  model: function(params) {
    return this.get('store').find('rol', params.rol_id);
  }
});

