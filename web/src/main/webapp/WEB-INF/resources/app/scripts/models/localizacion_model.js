/*global Ember*/
K.Localizacion = DS.Model.extend({
    latitud: DS.attr('number'),

    longitud: DS.attr('number'),

    precision: DS.attr('number'),

    imei: DS.attr('string'),

    fecha: DS.attr('date'),

    hora: DS.attr('date'),

    usuario: DS.belongsTo('usuario'),
    activo: DS.attr('string'),
    esActivo: function(){
      return this.get('activo') == 'S';
    }.property('activo')
});
