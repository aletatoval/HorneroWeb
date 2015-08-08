K.EmpresasIndexController = Ember.ObjectController.extend(K.ListaPaginadaFiltrableController, {
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
      this.transitionToRoute('empresas.nuevo');
    },
    activar: function(empresa){
      var that = this;
      this.$.ajax({
        url: window.location.origin + window.location.pathname + 'empresas/' + id + '/activar',
        type: 'PUT',
        success: function(data){
          if(!data.error){
            empresa.set('activo', true);
            var mensaje = "La empresa " + empresa.get('nombre') + " se activó correctamente";
            Materialize.toast(mensaje, 4000);
          }
        }
      });
    },
    desactivar: function(empresa){
      this.$.ajax({
        url: window.location.origin + window.location.pathname + 'empresas/' + id,
        type: 'DELETE',
        success: function(data){
          if(!data.error){
            empresa.set('activo', false);
            var mensaje = "La empresa " + empresa.get('nombre') + " se desactivó correctamente";
            Materialize.toast(mensaje, 4000);
          }
        }
      });
    }
  }
});
