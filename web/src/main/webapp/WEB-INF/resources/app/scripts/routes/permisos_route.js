var filtrosDefault = {_search: false, page: 1, rows: 10, filters: null, sidx:'nombre', sord: 'asc',  todos:false};
K.PermisosIndexRoute = Ember.Route.extend({
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
    return this.get('store').find('permiso', Object.getOwnPropertyNames(params).length == 0 ? filtrosDefault : params);
  },
  setupController: function (controller, model) {
    this._super(controller, model);

  	var meta = this.store.metadataFor('permiso');
    
    this.controllerFor('permisosIndex').set('paginaActual', meta.pagina).set('totalDatos', meta.totalDatos);
  }
});

