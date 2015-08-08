K.EmpresasNuevoRolRoute = Ember.Route.extend({
  model: function(params) {
    return Ember.RSVP.hash({
      empresa: this.get('store').find('empresa', this.modelFor('empresa').id),
      nuevoRol: this.get('store').createRecord('rol')
    });
  },
  setupController: function(controller, model) {
    controller.set('model', model.empresa);
    controller.set('nuevoRol',model.nuevoRol);
  }
});
