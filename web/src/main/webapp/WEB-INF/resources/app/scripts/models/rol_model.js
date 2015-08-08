/*global Ember*/
K.Rol = DS.Model.extend({
    nombre: DS.attr('string', {required: true, filtrable: true}),
    permisos: DS.hasMany('permiso'),
    empresa: DS.belongsTo('empresa', {async: true}),
    activo: DS.attr('string'),
    esActivo: function(){
      return this.get('activo') == 'S';
    }.property('activo')
});
