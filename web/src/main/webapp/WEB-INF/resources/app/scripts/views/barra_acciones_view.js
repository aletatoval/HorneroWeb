K.BarraAccionesView = Ember.View.extend({
	templateName: 'partes/barra_acciones',
	path: function(){
		var path = K.get('currentPath');
		var rutas = path.split('.');
		var lista = [];
		var str_url = window.location.hash;
		str_url = str_url.slice(2);
		var url = str_url.split('/');
		var model_id = this.get('controller.model.id');
		for(var i = 0; i < rutas.length; i++){
			if(url[i] == model_id)
			{ // Si la ruta es del model actual (id)
				lista.push({ruta: rutas[i], modelo: url[i], modulo: rutas[i]});
			} else if(i+1 < rutas.length)
			{ // Si no es el último
				lista.push({ruta: rutas[i], modulo: rutas[i]});
			} else if(rutas[i] != "index")
			{
				lista.push({modulo: rutas[i]});
			}
		}
		// Completar con las rutas para los demas modulos
		// { ruta="empresas",  modulo="empresas"}
		// { ruta="empresas.empresa",  modulo="empresa"}
		// { ruta="empresas.empresa.modulos",  modulo="modulos"}
		return lista;
	}.property(),
	didInsertElement: function(){
		this.$('.barra-acciones').pushpin({ top: 300 });
		// this.$('.barra-acciones').pushpin({ top: $('.barra-acciones').offset().top + 100 });
		$('.material-tooltip').remove();
	}
});
