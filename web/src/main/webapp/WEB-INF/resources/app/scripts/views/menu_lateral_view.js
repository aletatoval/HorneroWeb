K.MenuLateralView = Ember.View.extend({
  tagName: 'ul',
  classNameBindings: ['active'],
  classNames: ['sidebar-nav'],
  templateName: 'partes/menu-lateral',
  active: false,
  
  mouseEnter: function(event){
    this.set('active', true);
    this.get('controller').send('mostrarMenu');
  },
  mouseLeave: function(event){
    this.set('active', false);
    this.get('controller').send('ocultarMenu');
  },

  
});

K.MenuItemView = Ember.View.extend({
  tagName: 'li',
  classNames: ['menu', 'open'],
  classNameBindings: ['abierto:mostrarSubmenu'],
  mostrarSubmenu: false,

  actions: {
    desplegar: function(){
      this.toggleProperty('mostrarSubmenu');
    }
  }
});  