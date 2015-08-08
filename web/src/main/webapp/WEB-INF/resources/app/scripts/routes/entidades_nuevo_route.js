K.EntidadesNuevoRoute = Ember.Route.extend({
  controllerName: 'entidadesNuevo',
  model: function() {
    return this.get('store').createRecord('entidad');
  }
});

