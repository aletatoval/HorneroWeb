/*global Ember*/
K.Empresa = DS.Model.extend({
  nombre: DS.attr('string', {required: true, filtrable: true}),
  alias: DS.attr('string', {required: true, filtrable: true}),
  ruc: DS.attr('string', {required: true, filtrable: true}),
  descripcion: DS.attr('string'),
  direccion: DS.attr('string'),
  telefono: DS.attr('string'),
  email: DS.attr('string', {filtrable: true}),
  soporteNombre: DS.attr('string'),
  soporteTelefono: DS.attr('string'),
  soporteEmail: DS.attr('string'),
  codigoAcceso: DS.attr('string'),
  latitud: DS.attr('number'),
  longitud: DS.attr('number'),
  activo: DS.attr('string'),
  fechaCreacion: DS.attr('date', {type: 'date'}),
  fechaModificacion: DS.attr('date', {type: 'date'}),
  origenModificacion: DS.attr('string'),
  usuarios: DS.hasMany('usuario', {async: true}),
  modulos: DS.hasMany('modulo'),
  roles: DS.hasMany('rol'),

  esActivo: function(){
    return this.get('activo') == 'S';
  }.property('activo')
});
