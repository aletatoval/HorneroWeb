// Helpers
Ember.Handlebars.helper('tag', function(value, options) {
  // if(!K.diccionario[value]){
  //   console.error(value + " <<< la clave no existe en el diccionario");
  // }
  var escaped = K.diccionario[value] ? Handlebars.Utils.escapeExpression(K.diccionario[value]) : value;
  return new Ember.Handlebars.SafeString(escaped);
});
