K.ApplicationRoute = Ember.Route.extend({
  actions: {
    loading: function(transition, originRoute) {
      // console.log('transition', transition);
      // console.log('originRoute', originRoute);

      // this.render('partes/cargando', {outlet: 'mensajes'});    // substate implementation when returning `true`
      return true;
    }
  }
});
