K.EmpresaModulosController = Ember.ObjectController.extend({
	titulo: function(){
		return this.get('model.nombre');
	}.property('model'),
	listado: false,
	editando: false,
	mostrando: false,
	wizard: true,
	pasoActivo: 1,
	seleccionados: [],
	entidades: [],
	pasos: 2,

  esValido: false,
  preSeleccionado: new Date(),

	esPrimero: function(){
		return this.get('pasoActivo') == 1;
	}.property('pasoActivo'),
	esUltimo: function(){
		return this.get('pasoActivo') == this.get('pasos');
	}.property('pasoActivo'),

  actions: {
    seleccionar: function(modulo){
  		var seleccionados = this.get('seleccionados');
      console.log('length', seleccionados.length);
      if(seleccionados.length > 0) { // Si hay algún seleccionado
  		  if(seleccionados.contains(modulo)) // Y coincide con la selección actual
        {
          seleccionados.removeObject(modulo); // Remover
        }
        else{
          this.get('seleccionados').pushObject(modulo);
        }
        this.send('filtrarEntidades');
      }
      else // si no hay seleccionados
      {
        this.set('entidades', []); // vaciar la lista de entidades
        this.get('seleccionados').pushObject(modulo); // Agregar
        this.send('filtrarEntidades'); // volver a cargar las entidades
      }
     // this.get('listaPermisos').rerender();
  	},
    preSeleccionar: function(modulos){
      var seleccionados = this.get('seleccionados');
      modulos.forEach(function(modulo){
        seleccionados.pushObject(modulo);
      });
      this.set('preSeleccionado', new Date());
    },
    iniciar: function(){
      var modulos = this.get('model.modulos');
      if(modulos.length > 0) this.send('preSeleccionar', modulos);
    },
    filtrarEntidades: function(){
      var modulos = this.get('seleccionados'); // Lista de módulos elegidos
      var entidades = this.get('entidades');
      this.get('seleccionados').forEach(function(modulo){
        var em = modulo.get('entidades');
        modulo.get('entidades').forEach(function(entidad){
          if(!entidades.contains(entidad)){
            entidades.push(entidad);

          }
        });
      });
   /*   for(var i = 0; i<modulos.length; i++){
        console.log(modulos.get('entidades'));
        var em = modulos[i].get('entidades');
        em.get('content').forEach(function(entidad){
          if(!entidades.contains(entidad)){
            entidades.push(entidad);
          }
        });
      }*/
    },
    validar: function(){
    if(this.get('pasoActivo') == 1){
        this.set('esValido', this.get('seleccionados.length') > 0);
      }
    },
    anterior: function(){
      this.decrementProperty('pasoActivo');
    },
    siguiente: function(){
      this.send('validar');
      this.incrementProperty('pasoActivo');
    },
    finalizar: function(){
      var seleccionados = this.get('seleccionados'); // Lista de módulos elegidos
      var id = this.get('model.id');
      var modulos = [];
      seleccionados.forEach(function(modulo){
        modulos.push(modulo.get('id'));
      });
      // console.log('todos los módulos elegidos', modulos)
      var entidades = [];
      this.get('entidades').forEach(function(entidad){
        entidades.push({id: entidad.get('id'), urlEntrada: entidad.get('urlEntrada'), urlSalida: entidad.get('urlSalida'), urlExtra: entidad.get('urlExtra')});
      });
      var that = this;
      Ember.$.ajax({
        url: window.location.origin + window.location.pathname + 'empresas/' + id + '/modulos',
        type: 'PUT',
        data: JSON.stringify({modulos: modulos, entidades: entidades}),
        success: function(){
          that.transitionToRoute('empresas.index');
        }
      });

      // // console.log('modulos: ', this.get('modulos'));
      // // console.log('entidades: ', this.get('entidades'));
    },

  }
});
