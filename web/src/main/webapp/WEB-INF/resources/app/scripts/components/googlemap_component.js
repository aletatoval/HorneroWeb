K.GoogleMapsComponent = Ember.Component.extend({

	latitud_default: -25.2722801,
	longitud_default: -57.6209006,
	markers: [],
	markerCache: [],
	alto: 300,
	draggable: true,

	latitud: function(){
		return this.get('lat') == undefined ? this.get('latitud_default') : this.get('lat');
	}.property('lat'),

	longitud: function(){
		return this.get('lng') == undefined ? this.get('longitud_default') : this.get('lng');
	}.property('lng'),

	insertMap: function() {
		var container = this.$('.map-canvas');
		container.css('height', this.get('alto') + 'px');

		var modelo = this.get('targetObject.model');

		var markers = this.get('markers');
		markers.pushObject({latitud: this.get('latitud'), longitud: this.get('longitud')});
		var options = {
			center: new google.maps.LatLng(this.get('latitud'), this.get('longitud')),
			zoom: 10,
			mapTypeId: google.maps.MapTypeId.ROADMAP,
			draggable: this.get('draggable')
		};

		this.set('map', new google.maps.Map(container[0], options));
		this.set('markerCache', []);

		this.setMarkers();
	}.on('didInsertElement'),

	coordinatesChanged: function() {
		var map = this.get('map');

		if (map) map.setCenter(new google.maps.LatLng(this.get('latitud'), this.get('longitud')));
	}.observes('latitud', 'longitud'),

	setMarkers: function() {
		var map = this.get('map'),
			markers = this.get('markers'),
			markerCache = this.get('markerCache'), message = this.get('mensaje');

		markerCache.forEach(function(marker) {
			marker.setMap(null);
		}); // Remove all existing markers

		markers.forEach(function(marker) {
			var gMapsMarker = new google.maps.Marker({
				position: new google.maps.LatLng(marker.latitud, marker.longitud),
				map: map
			});
			var infoWindow = new google.maps.InfoWindow({
          content: message
      });

      google.maps.event.addListener(marker, 'click', function () {
          infoWindow.open(map, marker);
      });
			markerCache.pushObject(gMapsMarker); // Add this marker to our cache
		}, this);

	}.observes('markers.@each.latitud', 'markers.@each.longitud')
});
