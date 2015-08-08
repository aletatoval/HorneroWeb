K.DestinatarioEditRoute = Ember.Route.extend({
  model: function(params) {
    return Ember.RSVP.hash({
      destinatario: this.get('store').find('destinatario', this.modelFor('destinatario').id),
      lista_empresas: this.store.find('empresa', {_search: false, page: 1, rows: 10, filters: null, sidx:'nombre', sord: 'asc',  todos:true})
    });
  },

  setupController: function(controller, model) {
    controller.set('model', model.destinatario);
    controller.set('lista_empresas', model.lista_empresas);
  }
});

