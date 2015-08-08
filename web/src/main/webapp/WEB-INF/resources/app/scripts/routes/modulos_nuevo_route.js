K.ModulosNuevoRoute = Ember.Route.extend({
  controllerName: 'modulosNuevo',
  model: function() {
    return this.get('store').createRecord('modulo');
  }
});