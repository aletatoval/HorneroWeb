K.EntidadController = Ember.ObjectController.extend({
  // Implement your controller here.
});
K.EntidadSerializer = DS.RESTSerializer.extend({
  serializeIntoHash: function(hash, type, record, options) {
    Ember.merge(hash, this.serialize(record, options));
  }
});

