K.EntidadModulosController = Ember.ObjectController.extend({
  editando: true,
  lista_modulos: [],
  seleccionados: [],
  titulo: function(){
    return 'Asignar módulos a ' + this.get('model.nombre'); 
    }.property('model.nombre'),
  actions: {
    save: function(){
      var seleccionados = this.get('seleccionados'); // Lista de módulos elegidos
      var id = this.get('model.id');
      var modulos = [];
      seleccionados.forEach(function(modulo){
        modulos.push(modulo.get('id'));
      });
      var that = this;
      Ember.$.ajax({
        url: window.location.origin + window.location.pathname + 'entidades/' + id + '/modulos',
        type: 'PUT',
        data: JSON.stringify(modulos),
        success: function(){
          that.transitionToRoute('entidades.index');
        }
      });
    },
    iniciar: function(){
      var modulosEntidad = this.get('model.modulos');
      var modulos = this.get('seleccionados');
      
      modulosEntidad.forEach(function(mods){
        modulos.pushObject(mods);
      });
      // console.log(modulos);
      // this.get('listaSeleccionableModulos').rerender();
    },
    descartar: function(){
      this.get('model').rollback();
      this.transitionToRoute('entidades.index');
    },
    seleccionar: function(modulo){

      var m = this.get('seleccionados');
      console.log('seleccionados: ',m );
      console.log('modulo a seleccionar: ', modulo);
      if(m.contains(modulo)){
        m.removeObject(modulo);
      } else {
        m.pushObject(modulo);
      }
    }
  }
});

