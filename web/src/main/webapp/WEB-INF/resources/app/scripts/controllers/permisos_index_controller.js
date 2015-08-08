K.PermisosIndexController = Ember.ArrayController.extend(K.ListaPaginadaFiltrableController, {
	hayFiltros: false,
	actions: {
    nuevo: function(){
      var nuevoPermiso = this.get('store').createRecord('permiso', {activo: 'S'});
      var permisos = this.get('model');

      var nuevoArray = Ember.A();

      nuevoArray.pushObject(nuevoPermiso);
      permisos.forEach(function(permiso){
      	nuevoArray.pushObject(permiso);
      });

      this.set('model', nuevoArray);
    },
    guardar: function(permiso, view){
      permiso.save().then(function(data){
        Materialize.toast('El permiso '+ permiso.get('nombre') +' se guardó correctamente.', 4000);
        view.set('editar', false);
      },
      function(){
        Materialize.toast('Error: el permiso '+ permiso.get('nombre') +' no pudo guardarse.', 4000);
      });

    },
    cancelarNuevo: function(permiso, view){
      this.get('model').removeObject(permiso);
      permiso.destroyRecord();
      view.destroy();
    }
	}
});
