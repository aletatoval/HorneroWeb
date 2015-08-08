// empresas_wizard_route.js
K.EmpresaRolesRoute = Ember.Route.extend({
  model: function(params) {
    return Ember.RSVP.hash({
      empresa: this.get('store').find('empresa', this.modelFor('empresa').id),
      lista_roles: this.store.find('rol', {_search: false, page: 1, rows: 10, filters: null, sidx:'nombre', sord: 'asc',  todos:true, idEmpresa:this.modelFor('empresa').id}),
    });
  },

  setupController: function(controller, model) {
    controller.set('model', model.empresa);
    controller.set('roles', model.lista_roles);
  }

});
