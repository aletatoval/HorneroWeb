var K = window.K = Ember.Application.create({

  /** Modo desarrollador **/
  LOG_TRANSITIONS: true,

  /** Diccionario **/
  diccionario: $.parseJSON($.ajax({
      url: urlDiccionario,
      dataType: 'json',
      success:function(data){
        return data;
      },
      async: false
    }).responseText),

  /** Control del path para ruta dinámica **/


});

K.ApplicationController = Ember.Controller.extend({
  currentPath: [],
    updateCurrentPath: function() {
      var path = this.get('currentPath');
      K.set('currentPath', path);
    }.observes('currentPath'),

    menuMostrado: false,

    actions: {
      mostrarMenu: function(){
        this.set('menuMostrado', true);
      },
      ocultarMenu: function(){
        this.set('menuMostrado', false);
      },
    }

  });

Ember.Application.initializer({
  name: 'flash-messages',
  initialize: function(container, application){
    application.register('service:flash-messages', application.FlashMessagesService, { singleton: true });
    application.inject('controller', 'flashes', 'service:flash-messages');
    application.inject('route', 'flashes', 'service:flash-messages');
  }
});


var inflector = Ember.Inflector.inflector;

inflector.irregular('rol', 'roles');
inflector.irregular('entidad', 'entidades');
inflector.irregular('localizacion', 'localizaciones');


/* Order and include as you please. */
require('scripts/componentes');
require('scripts/mixins/*');
require('scripts/controllers/*');
require('scripts/store');
require('scripts/models/*');
require('scripts/routes/*');
require('scripts/serializers/*');
require('scripts/services/*');
require('scripts/components/*');
require('scripts/views/*');
require('scripts/router');
