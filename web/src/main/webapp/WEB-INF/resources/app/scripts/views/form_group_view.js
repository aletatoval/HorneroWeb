K.FormGroupView = Ember.View.extend({
	classNames: ['input-field'],
	classNameBindings: ['conError:invalid', 'validado:valid','columnas'],
	conError: false,
	validado: false,
	mensajeError: '',
	active: false,

	columnas: function(){ // Ancho del input-field, necesario porque Material ignora las columnas padre
		var ancho = this.get('ancho') ? this.get('ancho') : '12';
		return 'col s'+ ancho;
	}.property('ancho'),

	hayValor: function(){
		var input = this.$('input');
		if( $(input).val() ){
			this.set('active', true); // Ésto se encarga de ponerle ".active" a los labels de Material
 		}
	}.on('didInsertElement'),

	hayError: function(){
		var modelo = this.get('controller.model');
		var errors = modelo.get('errors');
		var campo = this.get('campo');
		this.set('conError', errors.has(campo));
	}.observes('controller.hayErrores'),

	mostrarError: function(){
		var modelo = this.get('controller.model');
		var campo = 'errors.' + this.get('campo');
		
		// TODO cambiar forma de agregar error
		
		// if(this.get('conError')){
		// 	modelo.set(campo, {message: 'ocurrió un error'});
		// }

	}.observes('conError')

})
