var get            = Ember.get;
var set            = Ember.set;
var computed       = Ember.computed;
var getWithDefault = Ember.getWithDefault;
var run            = Ember.run;
var on             = Ember.on;
var assert         = Ember.assert;

K.FlashMessageComponent = Ember.Component.extend({
  classNames:        [ 'alert', 'flashMessage' ],
  classNameBindings: [ 'alertType' ],

  alertType: computed('flash.type', function() {
    var flashType = get(this, 'flash.type');

    return 'alert-' + flashType;
  }),

  click: function() {
    this._destroyFlashMessage();
  },

  //private
//   _destroyOnTeardown: on('willDestroyElement', function() {
//     this._destroyFlashMessage();
//   }),

  _destroyFlashMessage: function() {
    var flash = get(this, 'flash');

    flash.destroyMessage();
  }
});