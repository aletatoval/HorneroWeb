// Adapter de la aplicación: REST
K.ApplicationAdapter = DS.RESTAdapter.extend({
  host: '/tangerineapp',
  ajaxError: function(jqXHR) {
    var error = this._super(jqXHR);

    if (jqXHR && jqXHR.status === 422) {
      var jsonErrors = Ember.$.parseJSON(jqXHR.responseText);
      console.log(jsonErrors);
      return new DS.InvalidError(jsonErrors);
    } else {
      return error;
    }
  }
});
// K.ApplicationAdapter.reopen({
//   ajax: function(url, type, hash) {
//     var adapter = this;

//     return new Ember.RSVP.Promise(function(resolve, reject) {
//       hash = adapter.ajaxOptions(url, type, hash);

//       hash.success = function( json ) {
//         Ember.run(null, resolve, json);
//       };

//       hash.error = function(jqXHR, textStatus, errorThrown) {
//         Ember.run(null, reject, adapter.ajaxError(jqXHR));
//       };

//       Ember.$.ajax(hash);
//     },
//     "DS: RestAdapter#ajax " + type + " to " + url).then(function(json){
//       if(json && json.redirect){
//         //redirect here
//         alert('redirect');
//       }
//       return json;
//     },
//     function(jqXHR){
//       if(jqXHR.status == 302){
//         console.log(jqXHR);
//         alert('redirect');
//       }

//     });
//   },

// });
