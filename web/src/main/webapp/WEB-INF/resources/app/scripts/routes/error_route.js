K.ErrorRoute = Ember.Route.extend({
  // setupController: function(controller, model){
  //   this._super(controller, model);
  //   // Implement your custom setup after
  //   this.controllerFor('error').set('codigo', model.get('status'));
  //   this.controllerFor('error').set('mensaje', model.get('errorThrown'));
  // },
  renderTemplate: function(error, transition){
    this.render('partes/error');
  }
});