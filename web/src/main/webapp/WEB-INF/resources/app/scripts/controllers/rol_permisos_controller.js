K.RolPermisosController = Ember.ObjectController.extend({
  editando: true,
  lista_permisos: [],
  lista_entidades: [],
  seleccionados: [],

  titulo: function(){
    return this.get('model.nombre'); 
    }.property('model.nombre'),
  actions: {
    save: function(){
      var seleccionados = this.get('seleccionados'); // Lista de módulos elegidos
      var id = this.get('model.id');
      var permisos = [];
      seleccionados.forEach(function(modulo){
        permisos.push(modulo.get('id'));
      });
      var that = this;
      Ember.$.ajax({
        url: window.location.origin + window.location.pathname + 'roles/' + id + '/permisos',
        type: 'PUT',
        data: JSON.stringify(permisos),
        success: function(){
          that.transitionToRoute('roles.index');
        }
      });
    },
    iniciar: function(){
      var rolPermisos = this.get('model.permisos');
      var permisos = this.get('seleccionados');
      
      rolPermisos.forEach(function(mods){
        permisos.pushObject(mods);
      });
      // console.log(permisos);
      // this.get('listaSeleccionablepermisos').rerender();
    },
    descartar: function(){
      this.get('model').rollback();
      this.transitionToRoute('roles.index');
    },
    seleccionar: function(modulo){

      var m = this.get('seleccionados');
      if(m.contains(modulo)){
        m.removeObject(modulo);
      } else {
        this.get('seleccionados').pushObject(modulo);
      }
    },
    marcarPermiso: function(entidad, permiso){
      console.log('se eligio el permiso '+ entidad.get('nombre') + '.' + permiso.get('nombre'));
    }
  }
});

