# Atlas

Atlas is a Liferay Portlet Plugin that renders an OpenStreetMap map in the browser, allowing users to overlay any type of marker data.

## Getting Started

### Prerequisites

Atlas has been built and tested against Java 8 and Liferay 6.2.

### Building

The Atlas Portlet Plugin is a standalone project and does not contain any dependencies that are not readily available in the Maven Central Repository.

To build the project, ensure that the Liferay paths defined the [`pom.xml`](pom.xml) have been updated to point to a valid Liferay 6.2 installation:

```
<liferay.auto.deploy.dir>C:\path\to\liferay-portal-6.2-ce-ga5\deploy</liferay.auto.deploy.dir>
<liferay.app.server.deploy.dir>CC:\path\to\liferay-portal-6.2-ce-ga5\tomcat-7.0.62\webapps</liferay.app.server.deploy.dir>
<liferay.app.server.lib.global.dir>C:\path\to\liferay-portal-6.2-ce-ga5\tomcat-7.0.62\lib</liferay.app.server.lib.global.dir>
<liferay.app.server.portal.dir>C:\path\to\liferay-portal-6.2-ce-ga5\tomcat-7.0.62\webapps\ROOT</liferay.app.server.portal.dir>
```

The project can then be built by executing:

```
mvn clean package -U
```

The `liferay:deploy` target can also be provided, which will result in the portlet being automatically deployed to Liferay upon build completion:

```
mvn clean package liferay:deploy -U
```

## Deployment

The Atlas Portlet Plugin can be deployed as standard Liferay portlet. Once deployed, the portlet will be available in the **Tools** category of the portlet library.

## Configuration

The Atlas Portlet Plugin can be configured using the standard portlet configuration window, once the portlet has been added to a page.

The following configuration options are available:

### Map Settings

Currently allows the height of the map to be defined in pixels. This is required in all circumstances. The width of the height is always 100% of the available portlet space.

### Default Coordinates

These coordinates define the default centre point of the map when it is rendered on the page. These points must be within the bounding box defined in [Bounding Box](#bounding-box).

### Bounding Box

The bounding box defines the maximum area over which the map can be panned. It is defined using two pairs of coordinates: a point that represents the south west (i.e. bottom left) of the bounding box and a point that represents the north east (i.e. top right) of the bounding box.

The range of valid values for the bounding box are defined by the maximum and minimum longitude and latitude values that exist for the planet Earth. These are:

- Minimum Latitude: `-85.0`
- Minimum Longitude: `-180.0`
- Maximum Latitude: `+85.0`
- Maximum Longitude: `+180.0`

### Zoom Levels

The zoom level configuration defines how far the map can be zoomed in and out of, and the default zoom level. The minimum zoom level defines how far out the map can be zoomed, and a lower number indicates the ability to zoom out further. Conversely, the maximum zoom level defines how far in the map can be zoomed, whereby a higher number indicates the ability to zoom in further.

The range of valid values for the maximum and minimum zoom levels are defined by the [Leaflet](http://leafletjs.com/) and are as follows:

- Minimum Zoom Level: `1`
- Maximum Zoom Level: `18`

The default zoom level must be between the minumum and the maximum zoom levels.

### Overlays

This configuration allows any number of overlays to be rendered on the map. A name of the overlay, and an endpoint (relative or absolute) returning data that represents the markers to be overlaid must be provided.

The provided endpoint must return a JSON structure that adheres to the following format:

```
[
  {
    "text" : "Glasgow Office",
    "icon" : {
      "url" : "/atlas-portlet/images/office-building.png",
      "anchorX" : 16,
      "anchorY" : 34
    },
    "lat" : 55.856162,
    "lon" : -4.295647
  },
  {
    "text" : "London Office",
    "icon" : {
      "url" : "/atlas-portlet/images/office-building.png",
      "anchorX" : 16,
      "anchorY" : 34
    },
    "lat" : 51.520956,
    "lon" : -0.107365
  }
]
```

#### Sample Data

The Atlas Portlet Plugin ships with three sample JSON endpoints that can be used to test the overlay functionality:

- `/delegate/data/digirati`
 - Overlays the locations of Digirati's current offices.
- `/delegate/data/trainstations`
 - Overlays the locations of all UK train stations.
 - Data provided by [trainline.eu](https://github.com/trainline-eu/stations)
- `/delegate/data/tflundergroundstations`
 - Overlays the locations of TfL Underground stations.
 - Data provided by [Chris Bell](https://www.doogal.co.uk/london_stations.php)

For all sample feeds, the icons are provided by Maps Icons Collection https://mapicons.mapsmarker.com.

## Built With

- [Liferay](https://www.liferay.com/)
- [Spring Framework](https://spring.io/)
- [Leaflet](http://leafletjs.com/)
- [OpenStreetMap](https://www.openstreetmap.org/)

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

Questions, queries and comments can be directed to opensource@digirati.com.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/digirati-co-uk/liferay-plugins/tags).

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
