K.FormularioView = Ember.View.extend({
	tagName: 'form',
	// classNames: ['form-horizontal', 'form-entidad'],
	valido: false,
	submit: function(){
		if($(this).find('.form-control.has-error').length == 0){
			this.set('valido', true)
		}
		this.get('controller').send('save');
	},
	didInsertElement: function(){
		
	},
	input: function(){
		// console.log('se dispara el método input');
	}
});