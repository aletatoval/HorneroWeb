K.TituloColumnaView = Ember.View.extend({
  templateName: 'partes/titulo_columna',
  classNames: 'ember-accion',
  classNameBindings: ['activo:active'],
  orden: '',
  conAtencion: false,
  
  activo: function(){
    return this.get('controller.sidx') == this.get('nombre');
  }.property('controller.sidx'),

  didInsertElement: function(){
    if(this.get('controller.sidx') == this.get('nombre')){
      this.set('orden', this.get('controller.sord'));
      // console.log('por defecto: ordenando ', this.get('orden'), ' por ', this.get('nombre'));
    }
  },
  icono: function(){
    if(this.get('activo')){
      if(this.get('orden') == 'asc'){
        return 'fa fa-caret-down';
      } else {
        return 'fa fa-caret-up';
      }
    } else {
      return 'fa fa-sort';
    }
  }.property('orden'),

  titulo: function(){
    var t = K.diccionario[this.get('nombre')] ? K.diccionario[this.get('nombre')] : this.get('nombre');
    return t;
  }.property('nombre'),

  click: function(event,view){
    // this.set('activo', true);
    if(this.get('orden') == ""){
      this.set('orden', 'asc')
    } else {
      var o = this.get('orden') == 'asc' ? 'desc' : 'asc';
      this.set('orden', o);
    }
    this.get('controller').send('reordenar', this.get('nombre'), this.get('orden'));
    // console.log('ordenando ', this.get('nombre'), ' por ', this.get('orden'));
  },
  mouseEnter: function(event){
    this.set('conAtencion', true);
  },
  mouseLeave: function(event){
    this.set('conAtencion', false);
  }
});