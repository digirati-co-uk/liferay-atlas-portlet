<%@ include file="/WEB-INF/includes/includes.jsp"%>
<%@ page import="com.liferay.util.portlet.PortletProps" %>

<html>
	<head>
		 <link rel="stylesheet" href="<c:url value="/css/vendor/leaflet.css" />" />
		 <style type="text/css">
		 	#atlas {
		 		height: <%= portletPreferences.getValue("mapHeight", PortletProps.get("default.map.height")) %>px;
		 	}
		 </style>
		 <script src="<c:url value="/js/vendor/leaflet.js" />"></script>
		 <script src="<c:url value="/js/vendor/jquery-3.1.1.min.js" />"></script>
	</head>
	<body>
		<div id="atlas"></div>
	</body>
</html>

<liferay-util:html-bottom>
	<script type="text/javascript">
		var lat = <%= portletPreferences.getValue("lat", PortletProps.get("default.lat")) %>;
		var lon = <%= portletPreferences.getValue("lon", PortletProps.get("default.lon")) %>;
		var bbox = {
			bottom: <%= portletPreferences.getValue("bboxBottom", PortletProps.get("default.bbox.bottom")) %>,
			left: <%= portletPreferences.getValue("bboxLeft", PortletProps.get("default.bbox.left")) %>,
			top: <%= portletPreferences.getValue("bboxTop", PortletProps.get("default.bbox.top")) %>,
			right: <%= portletPreferences.getValue("bboxRight", PortletProps.get("default.bbox.right")) %>
		};
		var zoom = {
			def: <%= portletPreferences.getValue("zoom", PortletProps.get("default.zoom")) %>,
			min: <%= portletPreferences.getValue("minZoom", PortletProps.get("default.minZoom")) %>,
			max: <%= portletPreferences.getValue("maxZoom", PortletProps.get("default.maxZoom")) %>
		};
		var overlays = <%= portletPreferences.getValue("overlays", PortletProps.get("[]")) %>;
		Atlas.init(lat, lon, bbox, zoom, overlays);
	</script>
</liferay-util:html-bottom>