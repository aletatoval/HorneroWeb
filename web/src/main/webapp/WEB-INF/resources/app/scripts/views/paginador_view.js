K.PaginadorView = Ember.View.extend({
	templateName: 'partes/paginador',
	numberOfPages: 10,
	
	pages: function() {
		var result = [],
			totalPaginas = this.get('controller.totalPaginas'),
			page = this.get('controller.page'),
			length = (totalPaginas >= this.get('numberOfPages')) ? this.get('numberOfPages') : totalPaginas,
			startPos;
		// If only one page, don't show pagination
		if (totalPaginas === 1)
			return;

		if (page <= Math.floor(this.get('numberOfPages') / 2) + 1 || totalPaginas <= this.get('numberOfPages')) {
			startPos = 1;
		} else {
			// Check to see if in the last section of pages
			if (page >= totalPaginas - (Math.ceil(this.get('numberOfPages') / 2) - 1)) {
				// Start pages so that the total number of pages is shown and the last page number is the last page
				startPos = totalPaginas - ((this.get('numberOfPages') - 1));
			} else {
				// Start pages so that current page is in the center
				startPos = page - (Math.ceil(this.get('numberOfPages') / 2) - 1);
			}
		}
		// Go through all of the pages and make an entry into the array
		for (var i = 0; i < length; i++)
			result.push(i + startPos);
		return result;
	}.property('controller.totalPaginas', 'controller.page'),
	/**
	 * Computed property to determine if the previous page link should be disabled or not.
	 * @return {Boolean}
	 */
	disablePrev: function() {
		return this.get('controller.page') == 1;
	}.property('controller.page'),
	/**
	 * Computed property to determine if the next page link should be disabled or not.
	 * @return {Boolean}
	 */
	disableNext: function() {
		return this.get('controller.page') == this.get('controller.totalPaginas');
	}.property('controller.page', 'controller.totalPaginas'),
	
	actions: {
		anterior: function(){
			this.get('controller').send('paginaAnterior');
		},
		siguiente: function(){
			this.get('controller').send('paginaSiguiente');
		}
	}
});

K.BotonPaginadorView= Ember.View.extend({
	// Bootstrap page buttons are li elements
	tagName: 'li',
	// Bind to is current to show the button as active
	classNameBindings: ['isCurrent:active'],

	goToPage: function() {
		this.set('parentView.controller.page', this.get('content'));
	},

	isCurrent: function() {
		// console.log('pagina ', this.get('content'));
		// console.log('page ', this.get('parentView.controller.page'));
		return this.get('content') == this.get('parentView.controller.page');
	}.property('parentView.controller.page'),

	actions: {
		ir: function(pagina){
			this.get('parentView.controller').send('iraPagina', pagina);
		}
	}
})