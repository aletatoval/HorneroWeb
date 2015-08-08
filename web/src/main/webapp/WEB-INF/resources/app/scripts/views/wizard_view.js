K.WizardView = Ember.View.extend({
	classNames: ['wizard'],

	didInsertElement: function(){
		this.set('controller.pasoActivo',1);
		this.get('controller').send('validar');
	}
});


K.IndicePasoWizardView = Ember.View.extend({
	tagName: 'li',
	classNameBindings: ['estado'],
	classNames: ['paso','col', 's3'],
	role: 'presentation',
	estado: 'disabled',
	pasoActual: function(){
		var e = (this.get('controller.pasoActivo') == this.get('paso')) ? 'active' : 'disabled';
		this.set('estado', e);
	}.observes('controller.pasoActivo').on('init')

});

K.PasoWizardView = Ember.View.extend({
	classNameBindings: ['oculto:hide'],

	redibujar: function(){
		if(this.get('paso') == this.get('parentView.controller.pasoActivo')){
			// console.log('hay que redibujar la vista del paso 2');
			this.rerender();
		}
	}.observes('{parentView.controller.pasoActivo,paso}'),

	oculto: function(){
		return this.get('parentView.controller.pasoActivo') != this.get('paso');
	}.property('{parentView.controller.pasoActivo,paso}'),

});
