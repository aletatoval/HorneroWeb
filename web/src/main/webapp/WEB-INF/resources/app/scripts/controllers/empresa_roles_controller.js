K.EmpresaRolesController = Ember.ObjectController.extend(Ember.Evented, {
  titulo: function(){
    return this.get('model.nombre');
  }.property('model'),
  listado: true,
  sinBusqueda: true,
  roles: [],

  actions: {
    nuevo: function(){
      var nuevoRol = this.get('store').createRecord('rol', {activo: 'S'});
      nuevoRol.set('empresa', this.get('model'));
      var roles = this.get('roles');

      var nuevoArray = Ember.A();

      // De ésta forma se logra que el nuevo registro esté al principio de la lista

      nuevoArray.pushObject(nuevoRol);
      roles.forEach(function(rol){
        nuevoArray.pushObject(rol);
      });

      this.set('roles', nuevoArray);
    },
    guardar: function(rol, view){
      rol.save().then(function(){
        Materialize.toast('El rol '+ rol.get('nombre') + ' se guardó correctamente.', 4000);
        view.set('editar', false);
      }, function(){
        Materialize.toast('El rol '+ rol.get('nombre') + ' se guardó correctamente.', 4000);
      });

    },
    cancelarNuevo: function(rol, view){
      this.get('roles').removeObject(rol);
      rol.destroyRecord();
      view.destroy();
    }
  }
});
