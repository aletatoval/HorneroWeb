/*global Ember*/
K.Usuario = DS.Model.extend({
  nombre: DS.attr('string', {required: true, filtrable: true}),
  alias: DS.attr('string', {required: true, filtrable: true}),
  clave: DS.attr('string', {required: true, type: 'password'}),
  documento: DS.attr('string'),
  telefono: DS.attr('string'),
  email: DS.attr('string',{filtrable: true}),
  imei: DS.attr('string'),
  imsi: DS.attr('string'),
  horaInicioMuestra: DS.attr('string', {type: 'date'}),
  horaFinMuestra: DS.attr('string', {type: 'date'}),
  tiempoMuestra: DS.attr('number'),
  rol: DS.belongsTo('rol', {required: true}),
  activo: DS.attr('string'),
  fechaCreacion: DS.attr('string', {type: 'date'}),
  // idUsuarioCreacion: DS.attr('number'),
  // idUsuarioModificacion: DS.attr('number'),
  // origenModificacion: DS.attr('string'),
  empresa: DS.belongsTo('empresa', {required: true}),
  esActivo: function(){
    return this.get('activo') == 'S';
  }.property('activo')
});
