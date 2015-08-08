K.UsuarioEditRoute = Ember.Route.extend({
  model: function(params) {
    return Ember.RSVP.hash({
      usuario: this.get('store').find('usuario', this.modelFor('usuario').id),
      lista_empresas: this.store.find('empresa', {_search: false, page: 1, rows: 10, filters: null, sidx:'nombre', sord: 'asc',  todos:true})
    });
  },

  setupController: function(controller, model) {
    controller.set('model', model.usuario);
    controller.set('lista_empresas', model.lista_empresas);
    if(model.usuario._data['empresa.id'])
    {
      this.get('store').find('empresa', model.usuario._data['empresa.id']).then(function(empresa){
        controller.set('empresa', empresa);
      });
    } else if(model.usuario.get('empresa.id')) 
    {
      this.get('store').find('empresa', model.usuario.get('empresa.id')).then(function(empresa){
        controller.set('empresa', empresa);
      });
    }
  }
});
