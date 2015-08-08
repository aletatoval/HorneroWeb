K.EmpresaRolController = Ember.ObjectController.extend({
  titulo: function(){
    return this.get('model.nombre');
  }.property('model'),
  listado: false,
  editando: true,
  mostrando: false,
  lista_entidades: [],
  lista_permisos: [],
  entidadPermiso: [],
  content: {},
  empresa: {},

  matrizEntidadPermisos: function(){
    var matriz = [];
    var cantPermisos = this.get('lista_permisos.length');
    var cantEntidades = this.get('lista_entidades.length');
    console.log('permisos, entidades: ', cantPermisos, ' ', cantEntidades)
    for(var i = 0; i<cantEntidades; i++) {
      var permisos = [];
      for(var j = 0; j<cantPermisos; j++) {
        permisos.push(0);
      }
      matriz.push(permisos);
    }
    this.set('entidadPermiso', matriz);
    console.log('matriz de permisos ', matriz);
  }.observes('lista_permisos.[],lista_entidades.[]'),

  actions: {
    save: function(){
      var that = this;
    },
    cancelar: function(){
      this.transitionToRoute('empresas.roles');
    },
    save: function(){
      var permisos = this.get('lista_permisos');
      var entidades = this.get('lista_entidades');
      var entidadPermiso = this.get('entidadPermiso');

      var idPermisos = [];
      var idEntidades = [];

      entidades.forEach(function(entidad, ie){
        idEntidades.push(entidad.id);
      });
      permisos.forEach(function(permiso, ip){
        idPermisos.push(permiso.id);
      });

      var envio = {};
      envio.entidades = idEntidades;
      envio.permisos = idPermisos;
      envio.EntidadPermiso = entidadPermiso;

      var that = this;
      var id = this.get('model.id');

      Ember.$.ajax({
        url: window.location.origin + window.location.pathname + 'roles/' + id + '/permisos',
        type: 'PUT',
        data: JSON.stringify(envio),
        success: function(){
          that.transitionToRoute('roles.index');
          Materialize.toast('Guardado correctamente', 4000);
        },
        error: function(){
          Materialize.toast('No se pudo guardar :(', 4000);
        }
      });
    },

    descartar: function(){
      // if(this.get('model')) this.get('model').rollback();
      this.transitionToRoute('empresa.roles', this.get('empresa'));
    },
    seleccionar: function(modulo){

      var m = this.get('seleccionados');
      if(m.contains(modulo)){
        m.removeObject(modulo);
      } else {
        this.get('seleccionados').pushObject(modulo);
      }
    },
    marcarPermiso: function(permiso, entidad){
      console.log('entidad: ', entidad);
      console.log('permiso: ', permiso);
      var entidadPermiso = this.get('entidadPermiso');
      var entidadIndex = this.get('lista_entidades').indexOf(entidad);
      var permisoIndex = this.get('lista_permisos').indexOf(permiso);

      console.log('indexes, entidad , permiso: ' + entidadIndex + ' , ' + permisoIndex);

      entidadPermiso[entidadIndex][permisoIndex] = entidadPermiso[entidadIndex][permisoIndex] == 1 ? 0 : 1;
      // var posicion = entidadPermiso[entidadIndex][permisoIndex];
      // if(posicion == 1){
      //   posicion = 0;
      // } else {
      //   posicion = 1;
      // }

      this.set('entidadPermiso', entidadPermiso);
      console.log('matriz de permisos ', entidadPermiso);
    }

  }
});
