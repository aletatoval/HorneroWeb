K.BotonRecargarView = Ember.View.extend({
	classNames: ['btn'],
	active: false,

	mouseEnter: function(){
		// console.log('mouse sobre botón recargar');
		this.set('active', true);
	},
	mouseLeave: function(){
		// console.log('mouse sobre botón recargar');
		this.set('active', false);
	},
	click: function(){
		this.get('controller').send('recargar');
	}
});