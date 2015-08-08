K.EmpresasNuevoRoute = Ember.Route.extend({
  controllerName: 'empresasNuevo',
  model: function() {
    return this.get('store').createRecord('empresa');
  }
});

