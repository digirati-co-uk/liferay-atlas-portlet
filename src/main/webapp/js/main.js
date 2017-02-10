var Atlas = (function() {

	var
	
	// Map
	mapObject,
	
	// Layers
	osmBaseLayer,
	wikimediaBaseLayer,
	
	// Controls
	layerControls,
	
	_initMap = function(lat, lon, bbox, zoom) {
		
		mapObject = L.map('atlas', {
			maxBounds: [[bbox.bottom, bbox.left], [bbox.top, bbox.right]],
			minZoom: zoom.min,
			maxZoom : zoom.max,
		}).setView([lat, lon], zoom.def);
		
		osmBaseLayer = L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
			attribution: '© OpenStreetMap contributors'
		}).addTo(mapObject);
	},
	
	_initLayerControls = function() {
		
		layerControls = L.control.layers({
			"OpenStreetMap": osmBaseLayer
		}, null).addTo(mapObject);
	},
	
	_addLayer = function(name, data) {
		
		var markers = [];
		
		jQuery.each(data, function(indexInArray, marker) {
			markers.push(L.marker([marker.lat, marker.lon], {
				icon: L.icon({
					iconUrl: marker.icon.url,
					iconAnchor: [marker.icon.anchorX, marker.icon.anchorY],
				})
			}).bindPopup(marker.text));
		});
		
		var layer = L.layerGroup(markers).addTo(mapObject);
		layerControls.addOverlay(layer, name);
	};
	
	return {
		
		init: function(lat, lon, bbox, zoom, overlays) {
			
			_initMap(lat, lon, bbox, zoom);
			
			_initLayerControls();
			
			jQuery.each(overlays, function(indexInArray, overlay) {
				
				jQuery.ajax(overlay.endpoint, {
					cache: false,
					dataType: 'json',
					method: 'GET',
					success: function(data, textStatus, jqXHR) {
						_addLayer(overlay.name, data);
					}
				});
			});
		}
	}
})();