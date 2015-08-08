K.EntidadesIndexController = Ember.ArrayController.extend(K.ListaPaginadaFiltrableController, {
	hayFiltros: false,
	actions: {
		nuevo: function(){
      this.transitionToRoute('entidades.nuevo');
    },
    nuevo: function(){
      var nuevaEntidad = this.get('store').createRecord('entidad', {activo: 'S'});
      var entidades = this.get('model');
      var nuevoArray = Ember.A();

      nuevoArray.pushObject(nuevaEntidad);
      entidades.forEach(function(entidad){
      	nuevoArray.pushObject(entidad);
      });
      // nuevoArray.pushObjects(entidades);

      // if(!entidades) entidades = [];

      // entidades.pushObject(entidad);
      this.set('model', nuevoArray);
    },
    guardar: function(entidad, view){
      entidad.save().then(function(data){
        Materialize.toast('La entidad '+ entidad.get('nombre') +' se guardó correctamente.', 4000);
        view.set('editar', false);
      },
      function(){
        Materialize.toast('Error: la entidad '+ entidad.get('nombre') +' no pudo guardarse.', 4000);
      });

    },
    cancelarNuevo: function(entidad, view){
      this.get('model').removeObject(entidad);
      entidad.destroyRecord();
      view.destroy();
    }
	}
  // Implement your controller here.
});
