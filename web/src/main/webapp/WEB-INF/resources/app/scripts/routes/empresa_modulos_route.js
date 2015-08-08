// empresas_wizard_route.js
K.EmpresaModulosRoute = Ember.Route.extend({
  model: function(params) {
    return Ember.RSVP.hash({
      empresa: this.get('store').find('empresa', this.modelFor('empresa').id),
      lista_entidades: this.store.find('entidad', {_search: false, page: 1, rows: 10, filters: null, sidx:'nombre', sord: 'asc',  todos:true, idEmpresa:this.modelFor('empresa').id}),
      lista_modulos: this.store.find('modulo', {_search: false, page: 1, rows: 10, filters: null, sidx:'nombre', sord: 'asc',  todos:true})
    });
  },

  setupController: function(controller, model) {
    controller.set('model', model.empresa);
    controller.set('lista_entidades', model.lista_entidades);
    controller.set('lista_modulos', model.lista_modulos);
    controller.set('seleccionados',model.empresa.get('modulos'));
  }

});
