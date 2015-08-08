K.RolesIndexController = Ember.ArrayController.extend(K.ListaPaginadaFiltrableController,{
  hayFiltros: false,
   actions: {
    nuevo: function(){
      this.transitionToRoute('roles.nuevo');
    }
	}
});
