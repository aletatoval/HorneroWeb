K.LocalizacionRoute = Ember.Route.extend({
  model: function(params) {
    return this.get('store').find('localizacion', params.localizacion_id);
  }
});

