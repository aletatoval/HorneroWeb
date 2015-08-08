K.RolSerializer = DS.RESTSerializer.extend(DS.EmbeddedRecordsMixin, {
  attrs: {
    empresa: {embedded: 'always'}
  },
	serializeIntoHash: function(hash, type, record, options) {
    Ember.merge(hash, this.serialize(record, options));
  },
  serializeHasMany: function(snapshot, json, relationship) {
    var key = relationship.key;
    if (key === 'permisos') {
      return;
    } else {
      this._super.apply(this, arguments);
    }
  },
  normalizeHash: {
    rol: function(hash){
      hash.empresa = {};
      hash.empresa.id = hash["empresa.id"];
      hash.empresa.nombre = hash["empresa.nombre"];
      delete hash["empresa.nombre"];
      delete hash["empresa.id"];
    }
  }
});