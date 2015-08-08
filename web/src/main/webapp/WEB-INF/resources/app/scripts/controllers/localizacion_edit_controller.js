K.LocalizacionEditController = Ember.ObjectController.extend({
  needs: 'localizacion',
  actions: {
    save: function(){
      self = this
      this.get('buffer').forEach(function(attr){
        self.get('controllers.localizacion.model').set(attr.key, attr.value);
      });
      this.transitionToRoute('localizacion',this.get('model'));
    }
  }
});

