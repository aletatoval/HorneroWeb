/*global Ember*/
K.Entidad = DS.Model.extend({
    nombre: DS.attr('string', {required: true, filtrable: true}),

    urlEntrada: DS.attr('string'),

    urlSalida: DS.attr('string'),

    urlExtra: DS.attr('string'),

    modulos: DS.hasMany('modulo'),
    activo: DS.attr('string'),
    esActivo: function(){
      return this.get('activo') == 'S';
    }.property('activo')
});
