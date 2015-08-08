K.GruposNuevoRoute = Ember.Route.extend({
  controllerName: 'gruposNuevo',
  model: function() {
    return this.get('store').createRecord('grupo');
  }
});