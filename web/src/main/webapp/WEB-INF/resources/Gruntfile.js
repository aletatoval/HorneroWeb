// Generated on 2014-12-09 using generator-ember 0.8.5
'use strict';
var LIVERELOAD_PORT = 35729;
var lrSnippet = require('connect-livereload')({port: LIVERELOAD_PORT});
var mountFolder = function (connect, dir) {
    return connect.static(require('path').resolve(dir));
};

// # Globbing
// for performance reasons we're only matching one level down:
// 'test/spec/{,*/}*.js'
// use this if you want to match all subfolders:
// 'test/spec/**/*.js'

module.exports = function (grunt) {
    // show elapsed time at the end
    require('time-grunt')(grunt);
    // load all grunt tasks
    require('load-grunt-tasks')(grunt);

    // configurable paths
    var yeomanConfig = {
        app: 'app',
        dist: 'dist',
        publish: 'dist'
    };

    grunt.initConfig({
        yeoman: yeomanConfig,
        watch: {
            emberTemplates: {
                files: '<%= yeoman.app %>/templates/**/*.hbs',
                tasks: ['emberTemplates', 'copy:templates']
            },
            compass: {
                files: ['<%= yeoman.app %>/styles/{,*/}*.{scss,sass}'],
                tasks: ['compass:server', 'copy:styles']
            },
            neuter: {
                files: ['<%= yeoman.app %>/scripts/{,*/}*.js'],
                tasks: ['neuter', 'copy:scripts']
            },
            // livereload: {
            //     options: {
            //         livereload: LIVERELOAD_PORT
            //     },
            //     files: [
            //         '.tmp/scripts/*.js',
            //         '<%= yeoman.app %>/*.jsp',
            //         '{.tmp,<%= yeoman.app %>}/styles/{,*/}*.css',
            //         '<%= yeoman.app %>/images/{,*/}*.{png,jpg,jpeg,gif,webp,svg}',
            //         '<%= yeoman.app %>/i18n/*.json'
            //     ]
            // }
        },        
        connect: {
            options: {
                port: 9000,
                // change this to '0.0.0.0' to access the server from outside
                hostname: '0.0.0.0'
            },
            livereload: {
                options: {
                    middleware: function (connect) {
                        return [
                            lrSnippet,
                            mountFolder(connect, '.tmp'),
                            mountFolder(connect, yeomanConfig.app)
                        ];
                    }
                }
            },
            test: {
                options: {
                    middleware: function (connect) {
                        return [
                            mountFolder(connect, 'test'),
                            mountFolder(connect, '.tmp')
                        ];
                    }
                }
            },
            dist: {
                options: {
                    middleware: function (connect) {
                        return [
                            mountFolder(connect, yeomanConfig.dist)
                        ];
                    }
                }
            }
        },
        open: {
            server: {
                path: 'http://localhost:<%= connect.options.port %>'
            }
        },
        clean: {
            dist: {
                files: [{
                    dot: true,
                    src: [
                        '.tmp/scripts/*',
                        '!.tmp/scripts/combined-dependencies.js',
                        '.tmp/styles/*',
                        '.tmp/index.jsp',
                        '<%= yeoman.dist %>/*',
                        '!<%= yeoman.dist %>/.git*',
                        '!<%= yeoman.dist %>/login.jsp'
                    ]
                }]
            },
            server: '.tmp',
            publish:{
              options:{force: true},
              files: [{
                    dot: true,
                    src: [
                        '<%= yeoman.publish %>/*',
                        '!<%= yeoman.publish %>/login.jsp',
                        '!<%= yeoman.publish %>/README.txt'
                    ]
                }]
            }
        },
        jshint: {
            options: {
                jshintrc: '.jshintrc',
                reporter: require('jshint-stylish')
            },
            all: [
                'Gruntfile.js',
                '<%= yeoman.app %>/scripts/{,*/}*.js',
                '!<%= yeoman.app %>/scripts/vendor/*',
                'test/spec/{,*/}*.js'
            ]
        },
        mocha: {
            all: {
                options: {
                    run: true,
                    urls: ['http://localhost:<%= connect.options.port %>/index.html']
                }
            }
        },
        compass: {
            options: {
                sassDir: '<%= yeoman.app %>/styles',
                cssDir: '.tmp/styles',
                generatedImagesDir: '.tmp/images/generated',
                imagesDir: '<%= yeoman.app %>/images',
                javascriptsDir: '<%= yeoman.app %>/scripts',
                fontsDir: '<%= yeoman.app %>/styles/fonts',
                importPath: '<%= yeoman.app %>/bower_components',
                httpImagesPath: '/images',
                httpGeneratedImagesPath: '/images/generated',
                httpFontsPath: '/styles/fonts',
                relativeAssets: true
            },
            dist: {
                options: {
                    debugInfo: true
                }},
            server: {
                options: {
                    debugInfo: true
                }
            }
        },
        // not used since Uglify task does concat,
        // but still available if needed
        /*concat: {
            dist: {}
        },*/
        // not enabled since usemin task does concat and uglify
        // check index.html to edit your build targets
        // enable this task if you prefer defining your build targets here
        /*uglify: {
            dist: {}
        },*/
        rev: {
            dist: {
                files: {
                    src: [
                        '<%= yeoman.dist %>/scripts/{,*/}*.js',
                        '<%= yeoman.dist %>/styles/{,*/}*.css',
                        '<%= yeoman.dist %>/images/{,*/}*.{png,jpg,jpeg,gif,webp}',
                        '<%= yeoman.dist %>/styles/fonts/*'
                    ]
                }
            }
        },
        useminPrepare: {
            html: '.tmp/index.html',
            options: {
                dest: '<%= yeoman.dist %>'
            }
        },
        usemin: {
            html: ['<%= yeoman.dist %>/{,*/}*.jsp', '<%= yeoman.dist %>/{,*/}*.html'],
            css: ['<%= yeoman.dist %>/styles/{,*/}*.css'],
            options: {
                dirs: ['<%= yeoman.dist %>']
            }
        },
        imagemin: {
            dist: {
                files: [{
                    expand: true,
                    cwd: '<%= yeoman.app %>/images',
                    src: '{,*/}*.{png,jpg,jpeg}',
                    dest: '<%= yeoman.dist %>/images'
                }]
            }
        },
        svgmin: {
            dist: {
                files: [{
                    expand: true,
                    cwd: '<%= yeoman.app %>/images',
                    src: '{,*/}*.svg',
                    dest: '<%= yeoman.dist %>/images'
                }]
            }
        },
        cssmin: {
            dist: {
                files: {
                    '<%= yeoman.dist %>/styles/main.css': [
                        '.tmp/styles/{,*/}*.css',
                        '<%= yeoman.app %>/styles/{,*/}*.css'
                    ]
                }
            }
        },
        htmlmin: {
            dist: {
                options: {
                    /*removeCommentsFromCDATA: true,
                    // https://github.com/yeoman/grunt-usemin/issues/44
                    //collapseWhitespace: true,
                    collapseBooleanAttributes: true,
                    removeAttributeQuotes: true,
                    removeRedundantAttributes: true,
                    useShortDoctype: true,
                    removeEmptyAttributes: true,
                    removeOptionalTags: true*/
                },
                files: [{
                    expand: true,
                    cwd: '<%= yeoman.app %>',
                    src: '*.jsp',
                    dest: '<%= yeoman.dist %>'
                }]
            }
        },
        replace: {
          app: {
            options: {
              variables: {
                ember: 'bower_components/ember/ember.debug.js',
                ember_data: 'bower_components/ember-data/ember-data.js',
                page: '',
                taglib: '',
                diccionario: ' "i18n/es_PY.json" '
              }
            },
            files: [
              {src: '<%= yeoman.app %>/index.html', dest: '.tmp/index.html'}
            ]
          },
          dist: {
            options: {
              variables: {
                ember: 'bower_components/ember/ember.debug.js',
                ember_data: 'bower_components/ember-data/ember-data.js',
                diccionario: ' "resources/i18n/es_PY.json" '
              }
            },
            files: [
              {src: '<%= yeoman.app %>/index.html', dest: '.tmp/index.jsp'}
            ]
          },
          publish: {
            options: {
              patterns: [
                {
                  match: 'scripts',
                  replacement: '${pageContext.request.contextPath}/resources/scripts'
                },
                {
                  match: 'styles/',
                  replacement: '${pageContext.request.contextPath}/resources/styles/'
                },
                {
                  match: '@@diccionario',
                  replacement: ' "resources/i18n/es_PY.json" '
                },
                {
                  match: '@@page',
                  replacement: ' <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" @@cerrar '
                },
                {
                  match: '@@taglib',
                  replacement: '<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" @@cerrar',
                },
              ],
              usePrefix: false
            },
            files: [
              {src: 'dist/index.html', dest: 'dist/index.jsp'}
            ]
          },
          publish2: {
            options: {
              variables: {
                cerrar: '%>'
              }
            },
            files: [
              {src: 'dist/index.jsp', dest: 'dist/index.jsp'}
            ]
          },
          html2jsp: {
            files: [
              {src: 'dist/index.html', dest: 'dist/index.jsp'}
            ]
          }
        },
        // Put files not handled in other tasks here
        copy: {
            fonts: {
                files: [
                    {
                        expand: true,
                        flatten: true,
                        filter: 'isFile',
                        cwd: '<%= yeoman.app %>/',
                        dest: '.tmp/styles/fonts/',
                        src: ['styles/fonts/**']
                    }
                ]
            },
            styles: {
                files: [
                    {
                        expand: true,
                        flatten: true,
                        filter: 'isFile',
                        cwd: '.tmp/styles/',
                        dest: 'dist/styles/',
                        src: ['styles.css']
                    }
                ]
            },
            scripts: {
                files: [
                    {
                        expand: true,
                        flatten: true,
                        filter: 'isFile',
                        cwd: '.tmp/scripts',
                        dest: 'dist/scripts/',
                        src: ['combined-scripts.js']
                    }
                ]
            },
            templates: {
                files: [
                    {
                        expand: true,
                        flatten: true,
                        filter: 'isFile',
                        cwd: '.tmp/scripts',
                        dest: 'dist/scripts/',
                        src: ['compiled-templates.js']
                    }
                ]
            },
            i18nTmp: {
                files: [
                    {
                        expand: true,
                        flatten: true,
                        filter: 'isFile',
                        cwd: '<%= yeoman.app %>/',
                        dest: '.tmp/i18n/',
                        src: ['scripts/i18n/**']
                    }
                ]
            },
            i18nDist: {
                files: [
                    {
                        expand: true,
                        flatten: true,
                        filter: 'isFile',
                        cwd: '<%= yeoman.app %>/',
                        dest: '<%= yeoman.dist %>/i18n/',
                        src: ['scripts/i18n/**']
                    }
                ]
            }, 
            dist: {
                files: [
                    {
                        expand: true,
                        dot: true,
                        cwd: '<%= yeoman.app %>',
                        dest: '<%= yeoman.dist %>',
                        src: [
                            // 'index.html',
                            'index.jsp',
                            '*.{ico,txt}',
                            '.htaccess',
                            'images/{,*/}*',
                            'styles/fonts/*',
                            'i18n/**',
                            '../.tmp/{,*/}*'
                        ]
                    },
                    {
                        expand: true,
                        dot: true,
                        cwd: '.tmp',
                        dest: '<%= yeoman.dist %>',
                        src: ['**']
                    }
                ]
            },
            publish: {
              files: [
              {
                expand: true,
                dot: true,  
                cwd: '<%= yeoman.dist %>',
                dest: '<%= yeoman.publish %>',
                src: [
                  '**/*.*', '!index.html'
                ]
              }
              ]
            }
        },
        concurrent: {
            server: [
                'emberTemplates',
                'compass:server'
            ],
            test: [
                'emberTemplates',
                'compass'
            ],
            dist: [
                'emberTemplates',
                'compass:dist',
                'imagemin',
                'svgmin',
                'htmlmin'
            ]
        },
        emberTemplates: {
            options: {
                templateCompilerPath: 'app/bower_components/ember/ember-template-compiler.js',
                handlebarsPath: 'node_modules/handlebars/dist/handlebars.js',
                templateNamespace: 'HTMLBars',
                templateName: function (sourceFile) {
                    var templatePath = yeomanConfig.app + '/templates/';
                    return sourceFile.replace(templatePath, '');
                }

            },
            dist: {
                files: {
                    '.tmp/scripts/compiled-templates.js': '<%= yeoman.app %>/templates/**/*.hbs'
                }
            }
        },
        neuter: {
            app: {
                options: {
                    filepathTransform: function (filepath) {
                        return yeomanConfig.app + '/' + filepath;
                    }
                },
                src: ['<%= yeoman.app %>/scripts/app.js'],
                dest: '.tmp/scripts/combined-scripts.js'
            }
        },
        uglify: {
            librerias: {
                options: {
                    beautify: true,
                    mangle: false
                },
                files: {
                        '.tmp/scripts/combined-dependencies.js': 
                        [
                            "app/bower_components/jquery/dist/jquery.js",
                            "app/bower_components/materialize/dist/js/materialize.js",
                            "app/bower_components/handlebars/handlebars.runtime.js",
                            "app/scripts/layout.js",
                            "app/bower_components/jquery-cookie/jquery.cookie.js",
                            "app/bower_components/ember/ember.debug.js",
                            "app/bower_components/ember-data/ember-data.js",
                            "app/bower_components/ember-localstorage-adapter/localstorage_adapter.js",
                            "app/bower_components/moment/min/moment-with-locales.js",
                            // "app/bower_components/bootstrap/dist/js/bootstrap.js",
                        ]
                    }
                    
                }
            
        }
    });

    grunt.registerTask('server', function (target) {
        grunt.log.warn('The `server` task has been deprecated. Use `grunt serve` to start a server.');
        grunt.task.run(['serve:' + target]);
    });

    grunt.registerTask('serve', function (target) {
        if (target === 'dist') {
            return grunt.task.run(['build', 'open', 'connect:dist:keepalive']);
        }

        grunt.task.run([
            'clean:server',
            'replace:app',
            'concurrent:server',
            'neuter:app',
            'copy:fonts',
            'copy:i18nTmp',
            'connect:livereload',
            'open',
            'watch'
        ]);
    });

    grunt.registerTask('test', [
        'clean:server',
        'replace:app',
        'concurrent:test',
        'connect:test',
        'neuter:app',
        'mocha'
    ]);

    grunt.registerTask('build', [
        'clean:dist',
        'replace:dist',
        'useminPrepare',
        'concurrent:dist',
        'neuter:app',
        'concat',
        'cssmin',
        'uglify',
        'copy:i18nDist',
        'copy:dist',
        // 'rev',
        'usemin',
        'replace:publish',
        'replace:publish2',
        'clean:publish',
        'copy:publish'
    ]);    
    grunt.registerTask('watcher', [
        'clean:dist',
        // 'uglify:librerias',
        // 'replace:dist',
        // 'useminPrepare',
        'concurrent:server',
        'neuter:app',
        // 'concat',
        // 'uglify',
        // 'cssmin',
        // 'copy:i18nDist',
        'replace:dist',
        'copy:dist',
        // 'replace:publish',
        // 'rev',
        // 'usemin',
        // 'clean:publish',
        // 'copy:publish',
        'watch'
    ]);
    grunt.registerTask('buildProd', [
        'clean:dist',
        'replace:dist',
        'useminPrepare',
        'concurrent:dist',
        'neuter:app',
        'concat',
        'cssmin',
        'uglify',
        'copy:i18nDist',
        'copy:dist',
        'rev',
        'usemin',
        'replace:publish',
        'replace:publish2',
        'clean:publish',
        'copy:publish'
    ]);

    grunt.registerTask('default', [
        'jshint',
        'test',
        'build'
    ]);
};
