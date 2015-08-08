
K.EmpresaRolRoute = Ember.Route.extend({
  // model: function(params){
  //   return Ember.RSVP.hash({
  //     rol: this.get('store').find('rol', params.rol_id),
  //     empresa: this.get('store').find('empresa', this.modelFor('empresa').id),
  //     lista_entidades: this.store.find('entidad', {_search: false, page: 1, rows: 10, filters: null, sidx:'nombre', sord: 'asc',  todos:true, idEmpresa:this.modelFor('empresa').id}),
  //     lista_permisos: this.store.find('permiso', {_search: false, page: 1, rows: 10, filters: null, sidx:'nombre', sord: 'asc',  todos:true})
  //   });
  // },

  setupController: function(controller, model) {
    var idEmpresa = model.get('empresa').content.id;
    var modelo = Ember.RSVP.hash({
      rol: this.get('store').find('rol', model.id),
      // empresa: this.get('store').find('empresa', model.get('empresa').id),
      lista_entidades: this.store.find('entidad', {_search: false, page: 1, rows: 10, filters: null, sidx:'nombre', sord: 'asc',  todos:true}),
      lista_permisos: this.store.find('permiso', {_search: false, page: 1, rows: 10, filters: null, sidx:'nombre', sord: 'asc',  todos:true})
    });

    modelo.then(function(resultado){
      controller.set('model', resultado.rol);
      controller.set('empresa', {id: idEmpresa});
      controller.set('lista_permisos', resultado.lista_permisos.content);
      controller.set('lista_entidades', resultado.lista_entidades.content);
    });
  }
});
