K.EmpresaSerializer = DS.RESTSerializer.extend(DS.EmbeddedRecordsMixin, {
	serializeIntoHash: function(hash, type, record, options) {
    Ember.merge(hash, this.serialize(record, options));
  },
  serializeHasMany: function(snapshot, json, relationship) {
    var key = relationship.key;
    if (key === 'entidades' || key === 'modulos') {
      return;
    } else {
      this._super.apply(this, arguments);
    }
  }
});
