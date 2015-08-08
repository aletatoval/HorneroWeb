K.RolController = Ember.ObjectController.extend({
  // Implement your controller here.
});

K.RolSerializer = DS.RESTSerializer.extend({
  serializeIntoHash: function(hash, type, record, options) {
    Ember.merge(hash, this.serialize(record, options));
  }
});