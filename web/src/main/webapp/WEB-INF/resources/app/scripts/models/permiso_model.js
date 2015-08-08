/*global Ember*/
K.Permiso = DS.Model.extend({
    nombre: DS.attr('string', {required: true}),
    activo: DS.attr('string'),
    esActivo: function(){
      return this.get('activo') == 'S';
    }.property('activo')
});
