/*global Ember*/
K.Modulo = DS.Model.extend({
  nombre: DS.attr('string', {required: true}),
  entidades: DS.hasMany('entidad', {async: true})  ,
  activo: DS.attr('string'),
  esActivo: function(){
    return this.get('activo') == 'S';
  }.property('activo')
});
