
K.PermisosNuevoRoute = Ember.Route.extend({
  controllerName: 'permisosNuevo',
  model: function() {
    return this.get('store').createRecord('permiso');
  }
});

