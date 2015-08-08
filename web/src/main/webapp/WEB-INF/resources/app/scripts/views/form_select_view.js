K.FormSelectView = Ember.View.extend({
	classNames: ['input-field'],
	templateName: 'partes/form_select',
	classNameBindings: ['columnas'],

	columnas: function(){
		var ancho = this.get('ancho') ? this.get('ancho') : '12';
		return 'col s'+ ancho;
	}.property('ancho'),

	didInsertElement: function(){
		var seleccionado = this.get('seleccionado');
		if(seleccionado != undefined) {
			aElegir = this.$('option[value="'+seleccionado+'"]');
			this.$('option').prop('selected', 'false');
			aElegir.prop('selected', 'selected');
		}
		this.$('select').material_select();
	},
	click: function(){
		var s = this.$('select').val();
		var empresa = {};
		if(s){
			empresa = this.get('opciones').findBy('id', s);
		} 
		if(!this.$('ul.dropdown-content').hasClass('active')) {
			this.get('controller').set(this.get('selected'), empresa);
		}
	}
});