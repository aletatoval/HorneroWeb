// Capturar los errores incluidos en el cuerpo de la respuesta: error:true, mensaje="xxx"
K.ApplicationSerializer = DS.RESTSerializer.extend({
  extractMeta: function(store, type, payload) {
    if (payload && payload.error && payload.mensaje) {
    	console.log('payload.error ', payload.error, ' payload.mensaje: ', payload.mensaje );
    }
  }
});