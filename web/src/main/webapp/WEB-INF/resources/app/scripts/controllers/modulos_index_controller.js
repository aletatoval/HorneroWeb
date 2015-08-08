K.ModulosIndexController = Ember.ArrayController.extend(K.ListaPaginadaFiltrableController, {
	hayFiltros: false,
	actions: {
		nuevo: function(){
      this.transitionToRoute('modulos.nuevo');
    },
	}
  // Implement your controller here.
});
