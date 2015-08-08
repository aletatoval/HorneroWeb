var get            = Ember.get;
var set            = Ember.set;
var computed       = Ember.computed;
var getWithDefault = Ember.getWithDefault;
var run            = Ember.run;
var on             = Ember.on;
var assert         = Ember.assert;

K.FlashMessagesService = Ember.Object.extend({
  queue          : Ember.A([]),
  isEmpty        : computed.equal('queue.length', 0),

  defaultTimeout : 6000,

  success: function(message, timeout) {
    timeout = (timeout === undefined) ? get(this, 'defaultTimeout') : timeout;

    return this._addToQueue(message, 'success', timeout);
  },

  info: function(message, timeout) {
    timeout = (timeout === undefined) ? get(this, 'defaultTimeout') : timeout;

    return this._addToQueue(message, 'info', timeout);
  },

  warning: function(message, timeout) {
    timeout = (timeout === undefined) ? get(this, 'defaultTimeout') : timeout;

    return this._addToQueue(message, 'warning', timeout);
  },

  danger: function(message, timeout) {
    timeout = (timeout === undefined) ? get(this, 'defaultTimeout') : timeout;

    return this._addToQueue(message, 'danger', timeout);
  },

  addMessage: function(message, type, timeout) {
    type    = (type === undefined) ? 'info' : type;
    timeout = (timeout === undefined) ? get(this, 'defaultTimeout') : timeout;

    return this._addToQueue(message, type, timeout);
  },

  clearMessages: function() {
    var flashes = get(this, 'queue');
    flashes.clear();

    return flashes;
  },

  // private
  _addToQueue: function(message, type, timeout) {
    var flashes = get(this, 'queue');
    var flash   = this._newFlashMessage(this, message, type, timeout);

    flashes.pushObject(flash);
    return flash;
  },

  _newFlashMessage: function(service, message, type, timeout) {
    type    = (type === undefined) ? 'info' : type;
    timeout = (timeout === undefined) ? get(this, 'defaultTimeout') : timeout;

    Ember.assert('Must pass a valid flash service', service);
    Ember.assert('Must pass a valid flash message', message);

    return K.FlashMessage.create({
      type         : type,
      message      : message,
      timeout      : timeout,
      flashService : service
    });
  }
});