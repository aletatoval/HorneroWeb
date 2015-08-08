K.Grupo = DS.Model.extend({
    nombre: DS.attr('string', {required: true, filtrable: true}),
    activo: DS.attr('string'),
    empresa: DS.belongsTo('empresa', {async: true}),
    esActivo: function(){
      return this.get('activo') == 'S';
    }.property('activo')
});