K.ModuloSerializer = DS.RESTSerializer.extend(DS.EmbeddedRecordsMixin, {
	serializeHasMany: function(snapshot, json, relationship) {
    var key = relationship.key;
    if (key === 'entidades') {
      return;
    } else {
      this._super.apply(this, arguments);
    }
  },
  serializeIntoHash: function(hash, type, record, options) {
    Ember.merge(hash, this.serialize(record, options));
  }
});