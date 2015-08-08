K.UsuariosNuevoRoute = Ember.Route.extend({
  model: function(params) {
    return Ember.RSVP.hash({
      usuario: this.get('store').createRecord('usuario'),
      lista_empresas: this.store.find('empresa', {_search: false, page: 1, rows: 10, filters: null, sidx:'nombre', sord: 'asc',  todos:true})
    });
  },

  setupController: function(controller, model) {
    controller.set('model', model.usuario);
    controller.set('lista_empresas', model.lista_empresas);
  }
});

