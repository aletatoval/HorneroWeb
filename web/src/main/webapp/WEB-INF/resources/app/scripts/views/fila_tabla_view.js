K.FilaTablaView = Ember.View.extend({
	tagName: 'tr',
	classNameBindings: ['active', 'disabled'],
  active: false,
  editar: false,
	esNuevo: false,
	didInsertElement: function(){
		// console.log(this);
		var tooltip = this.$('.tooltipped');
		setTimeout(function(){tooltip.tooltip();}, 1000);
	},

	nuevo: function(){
		var mo = this.get('modelo');
		if(mo.get('isNew')){
		this.set('editar', true);
		this.set('esNuevo', true);
		}
	}.on('didInsertElement'),
	disabled: function(){
		return !this.get('modelo.esActivo');
	}.property('modelo.esActivo'),

  mouseEnter: function(event){
    this.set('active', true);
  },
  mouseLeave: function(event){
    this.set('active', false);
  },
  actions: {
  	editar: function(){
  		this.set('editar', true);
  	},
  	cancelar: function(){
			if(this.get('esNuevo')){
				this.get('controller').send('cancelarNuevo', this.get('modelo'), this);
			} else {
  			this.get('modelo').rollback();
  			this.set('editar', false);
			}
  	},
    desactivar: function(){
      var that = this.get("controller");
      var modelo = this.get('modelo');
      modelo.set('activo', 'N');
      modelo.save().then(
        function(data){
          Materialize.toast('se ha desactivado correctamente: '+data.get('nombre'), 4000);
      },function(data){
          Materialize.toast('Error', 4000);
          modelo.set('activo', 'S');
      }
      );
    },
    activar: function(){
      var that = this.get("controller");
      var modelo = this.get('modelo');
      modelo.set('activo', 'S');
      modelo.save().then(
        function(data){
          Materialize.toast('Se ha activado correctamente: '+data.get('nombre'), 4000);
      },function(data){
          Materialize.toast('Error', 4000);
          modelo.set('activo', 'N');
      }
      );
    },
  	guardar: function(){
  		var that = this.get("controller");
  		if(!this.get('modelo.nombre')){
  			Materialize.toast('No se puede guardar un registro vacío', 4000)
  			return;
  		} else {
        var modelo = this.get('modelo');
				this.get('controller').send('guardar', modelo, this);
			}
  	}
  }
});
