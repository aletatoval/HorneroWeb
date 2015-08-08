K.GruposIndexController = Ember.ObjectController.extend(K.ListaPaginadaFiltrableController, {
  queryParams: ['_search', 'page', 'filters', 'rows', 'sidx', 'sord', 'todos'],

  // _search: false,
  // page: 1,
  // filters: null,
  // rows: 10,
  // sidx: 'nombre',
  // sord: 'asc',
  // todos: false,

  // totalDatos: 0,
  // paginaActual: 0,

  // listado: true,
  hayFiltros: true,
  // buscarSimpleActivo: false,
  // buscarAvanzadoActivo: false,

  actions: {
    nuevo: function(){
      this.transitionToRoute('grupos.nuevo');
    },
    activar: function(grupo){
      var that = this;
      this.$.ajax({
        url: window.location.origin + window.location.pathname + 'grupos/' + id + '/activar',
        type: 'PUT',
        success: function(data){
          if(!data.error){
            grupo.set('activo', true);
            var mensaje = "El grupo " + grupo.get('nombre') + " se activó correctamente";
            Materialize.toast(mensaje, 4000);
          }
        }
      });
    },
    desactivar: function(grupo){
      this.$.ajax({
        url: window.location.origin + window.location.pathname + 'grupos/' + id,
        type: 'DELETE',
        success: function(data){
          if(!data.error){
            grupo.set('activo', false);
            var mensaje = "El grupo " + grupo.get('nombre') + " se desactivó correctamente";
            Materialize.toast(mensaje, 4000);
          }
        }
      });
    }
  }
});
