K.UsuarioRoute = Ember.Route.extend({
	mostrando: true,
  model: function(params) {
    return this.get('store').find('usuario', params.usuario_id);
  }
});

