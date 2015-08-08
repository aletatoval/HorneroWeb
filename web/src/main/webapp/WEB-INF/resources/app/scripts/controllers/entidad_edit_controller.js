K.EntidadEditController = Ember.ObjectController.extend({
  editando: true,
  lista_modulos: [],
  modulos: [],
  hayErrores: false,
  titulo: function(){
    return this.get('model.nombre'); 
    }.property('model.nombre'),
  actions: {
    save: function(){
      var modelo = this.get('model');
      var modulos = this.get('modulos');
      for(var i = 0; i<modulos.length;i++){
        modelo.get('modulos').addObject(modulos[i]);
      }
      var that = this;
      this.get('model').save().then(
        function(data){
          Ember.get(that, 'flashes').success("La entidad " + data.get('data.nombre') +  " se modifico exitosamente. ");
          that.transitionToRoute('entidades.index');
        },
        function(response){
          that.set('hayErrores', true);
        }
      );
    },
    descartar: function(){
      this.get('model').rollback();
      this.transitionToRoute('entidades.index');
    },
    seleccionar: function(modulo){
      // console.log('seleccionar ', modulo.get('nombre'));
      var m = this.get('modulos');
      if(m.contains(modulo)){
        m.removeObject(modulo);
      } else {
        this.get('modulos').push(modulo);
      }
    }
  }
});

