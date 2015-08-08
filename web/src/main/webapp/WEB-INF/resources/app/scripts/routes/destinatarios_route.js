var filtrosDefault = {_search: false, page: 1, rows: 10, filters: null, sidx:'nombre', sord: 'asc',  todos:true};

K.DestinatariosIndexRoute = Ember.Route.extend({
	queryParams: {
		filters: {
			refreshModel: true,
			replace: true
		},
		_search: {
			refreshModel: true,
			replace: true
		},
		page: {
			refreshModel: true,
			replace: true
		},
		filters: {
			refreshModel: true,
			replace: true
		},
		rows: {
			refreshModel: true,
			replace: true
		},
		sidx: {
			refreshModel: true,
			replace: true
		},
		sord: {
			refreshModel: true,
			replace: true
		},
		todos: {
			refreshModel: true,
			replace: true
		}
	},
	model: function(params) {
		return this.get('store').find('destinatario', Object.getOwnPropertyNames(params).length == 0 ? filtrosDefault : params);
	},
	afterModel: function(){
  	var meta = this.store.metadataFor('destinatario');
  	// console.log('metadatos empresa ', meta);
  	this.controllerFor('destinatariosIndex').setProperties({paginaActual: meta.pagina, totalDatos: meta.totalDatos});
	}
});