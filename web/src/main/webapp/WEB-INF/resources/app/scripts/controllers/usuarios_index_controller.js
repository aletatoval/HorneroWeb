K.UsuariosIndexController = Ember.ArrayController.extend( K.ListaPaginadaFiltrableController, {
  // queryParams: ['_search', 'page', 'filters', 'rows', 'sidx', 'sord', 'todos'],

  // _search: false,
  // page: 1,
  // filters: null,
  // rows: 10,
  // sidx: 'nombre',
  // sord: 'asc',
  // todos: false,

  // totalDatos: 0,
  // paginaActual: 0,
  // totalPaginas: function(){
  //   return Math.ceil( this.get('totalDatos') / this.get('rows') );
  // }.property('totalDatos,rows'),

  // needs: ['usuarios'],
  // listado: true,
  hayFiltros: true,
  // buscarSimpleActivo: false,
  // buscarAvanzadoActivo: false,

  actions: {
    nuevo: function(){
      this.transitionToRoute('usuarios.nuevo');
    },
    // resetLista: function() {
    //   // console.log('resetando filtros');
    //   this.setProperties({_search: false, page: 1, filters: null, rows: 10, sidx: 'nombre', sord: 'asc', todos: true }); 
    // },
    // recargar: function() {
    //   // console.log('recargar la página');
    //   this.send('resetLista');
    //   this.get('target.router').refresh();
    // },
    // buscarSimple: function(valor) {
    //   var filtro = {groupOp: "OR", rules: [{field: "nombre", data: valor, op: "cn"}] };
    //   // console.log('buscar ', valor);
    //   this.setProperties({_search: true, page: 1, filters: JSON.stringify(filtro), rows: 10, todos: false }); 
    // },
    // mostrarBuscarSimple: function() {
    //   this.set('buscarSimpleActivo', true);
    //   this.set('buscarAvanzadoActivo', false);
    // },
    // mostrarBuscarAvanzado: function() {
    //   this.set('buscarSimpleActivo', false);
    //   this.set('buscarAvanzadoActivo', true);
    // },
    // cancelarFiltrado: function() {
    //   this.set('buscarAvanzadoActivo', false);
    //   this.set('buscarSimpleActivo', false);
    // },
    // ocultarBuscarSimple: function() {
    //   this.set('buscarSimpleActivo', false);
    // },
    // filtrar: function() {
    //   var form = $('#form-filtros').serializeArray();
    //   var filtro = {groupOp: "AND"}; 
    //   filtro.rules = [];
    //   $(form).each(function(i, item) {
    //     if (item.value != "") 
    //       filtro.rules.push({field: item.name, data: item.value, op: "cn"}); 
    //   });
    //   // console.log(JSON.stringify(filtro));
    //   this.setProperties({_search: true, page: 1, filters: JSON.stringify(filtro), rows: 10, todos: false }); 
    // },
    // reordenar: function(valor, orden) {
    //   // console.log('controller: reordenar según ', valor, ' orden ', orden);
    //   this.set('sidx', valor).set('sord', orden);
    // }
  }
});

