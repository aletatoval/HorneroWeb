K.UsuarioIndexController = Ember.ObjectController.extend({
  listado: false,
  mostrando: true,

  actions: {
    editar: function(){
      this.transitionToRoute('usuario.edit');
    },
    eliminar: function(){
      this.get('model').deleteRecord(); //marcar el modelo como "isDeleting"
      // desea eliminar? blablabla
      this.get('model').save(); // persistir el borrado
    }
  }
});

K.UsuarioSerializer = DS.RESTSerializer.extend(DS.EmbeddedRecordsMixin, {
	attrs: {
		empresa: {embedded: 'always'},
		rol: {embedded: 'always'}
	},
  serializeIntoHash: function(hash, type, record, options) {
    Ember.merge(hash, this.serialize(record, options));
  },
  serializeBelongsTo: function(snapshot, json, relationship) {
    var key = relationship.key;
    
    var belongsTo = snapshot.belongsTo(key);

    key = this.keyForRelationship ? this.keyForRelationship(key, "belongsTo") : key;

    json[key] = Ember.isNone(belongsTo) ? belongsTo : {id: belongsTo.id};
  },
  normalizeHash: {
  	usuarios: function(hash){
  		hash.empresa = {};
  		hash.empresa.id = hash["empresa.id"];
  		hash.empresa.nombre = hash["empresa.nombre"];
  		hash.rol = {};
  		hash.rol.id = 1;
  		hash.rol.nombre = hash["rol.nombre"];
  		delete hash["rol.nombre"];
  		delete hash["empresa.nombre"];
  		delete hash["empresa.id"];
  	}
  }
});
