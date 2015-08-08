K.Router.map(function() {

  this.resource('empresas', function() {
    this.resource('empresa', {
      path: '/:empresa_id'
    }, function() {
        this.route('edit');
        this.route('modulos');
        this.route('roles');
        this.route('rol', {path: '/rol/:rol_id'});
        this.route('nuevoRol');
    });
    this.route('nuevo');
  });

  this.resource('entidades', function() {
    this.resource('entidad', {
      path: '/:entidad_id'
    }, function() {
        this.route('modulos');
        this.route('edit');
      });
    this.route('nuevo');
  });

  this.resource('localizaciones', function() {
    this.resource('localizacion', {
      path: '/:localizacion_id'
    }, function() {
        this.route('edit');
      });
    this.route('nuevo');
  });

  this.resource('destinatarios', function() {
    this.resource('destinatario', {
      path: '/:destinatario_id'
    }, function() {
        this.route('edit');
      });
    this.route('nuevo');
  });

  this.resource('modulos', function() {
    this.resource('modulo', {
      path: '/:modulo_id'
    }, function() {
        this.route('edit');
      });
    this.route('nuevo');
  });

  this.resource('permisos', function() {
    this.resource('permiso', {
      path: '/:permiso_id'
    }, function() {
        this.route('edit');
      });
    this.route('nuevo');
  });

  this.resource('roles', function() {
    this.resource('rol', {
      path: '/:rol_id'
    }, function() {
        this.route('edit');
        this.route('permisos');
      });
    this.route('nuevo');
  });

  this.resource('usuarios', function() {
    this.resource('usuario', {
      path: '/:usuario_id'
    }, function() {
        this.route('edit');
      });
    this.route('nuevo');
  });

  this.resource('estilos');
  this.resource('documentacion', function() {
    this.route('frontend');
    this.resource('documentacion.backend', {
      path: '/backend'
    }, function() {
        this.route('respuestas');
        this.route('ruteo');
      });
    this.route('diccionario');
    this.route('trello');
    this.route('formularios');
  });

});
