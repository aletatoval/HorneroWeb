K.ListaPaginadaFiltrableController = Ember.Mixin.create({
  queryParams: ['_search', 'page', 'filters', 'rows', 'sidx', 'sord', 'todos'],

  _search: false,
  page: 1,
  filters: null,
  rows: 10,
  sidx: 'nombre',
  sord: 'asc',
  todos: false,

  totalDatos: 0,
  paginaActual: 0,
  totalPaginas: function(){
    return Math.ceil( this.get('totalDatos') / this.get('rows') );
  }.property('totalDatos,rows'),

  listado: true,
  buscarSimpleActivo: false,
  buscarSimpleValor: '',
  buscarAvanzadoActivo: false,
  hayFiltros: false,

  actions: {
    resetLista: function() {
      this.setProperties({_search: false, page: 1, filters: null, rows: 10, sidx: 'nombre', sord: 'asc', todos: false });
    },
    recargar: function() {
      this.get('target.router').refresh();
    },
    buscarSimple: function() {
      var form = $('#form-filtros').serializeArray();
      var filtro = {groupOp: "OR"};
      var valor = this.get('buscarSimpleValor');
      filtro.rules = [];
      this.set('buscarSimpleValor', valor);
      if(form.length){
        $(form).each(function(i, item) {
          filtro.rules.push({field: item.name, data: valor, op: "cn"});
        });
      } else {
        filtro.rules = [{field: 'nombre', data: valor, op: 'cn'}];
      }
      this.setProperties({_search: true, page: 1, filters: JSON.stringify(filtro), rows: 10, todos: false });
    },
    mostrarBuscarSimple: function() {
      this.set('buscarSimpleActivo', true);
      this.set('buscarAvanzadoActivo', false);
    },
    mostrarBuscarAvanzado: function() {
      this.set('buscarSimpleActivo', false);
      this.set('buscarAvanzadoActivo', true);
    },
    cancelarFiltrado: function() {
      this.set('buscarAvanzadoActivo', false);
      this.set('buscarSimpleActivo', false);
      this.send('resetLista');
    },
    ocultarBuscarSimple: function() {
      this.set('buscarSimpleActivo', false);
    },
    filtrar: function() {
      var form = $('#form-filtros').serializeArray();
      var filtro = {groupOp: "AND"};
      filtro.rules = [];
      $(form).each(function(i, item) {
        if (item.value != "")
          filtro.rules.push({field: item.name, data: item.value, op: "cn"});
      });
      this.setProperties({_search: true, page: 1, filters: JSON.stringify(filtro), rows: 10, todos: false });
    },
    reordenar: function(valor, orden) {
      this.set('sidx', valor).set('sord', orden);
    },
    paginaSiguiente: function(){
      if(this.get('page') < this.get('totalPaginas')){
        this.incrementProperty('page');
      }
    },
    paginaAnterior: function(){
      if(this.get('page') > 1){
        this.decrementProperty('page');
      }
    },
    iraPagina: function(pagina){
      if(pagina > 0 && pagina <= this.get('totalPaginas') && pagina != this.get('page')){
        this.set('page', pagina);
      }
    }
  }
});
