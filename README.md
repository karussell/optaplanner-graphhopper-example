# OptaPlanner with GraphHopper precalculated routes

Open project and execute Main.main method and see the Vehicle Routing Plan problem solved by
GraphHopper and Optaplanner. Currently not working with OptaPlanner.

Geoffrey [blogged about](http://www.optaplanner.org/blog/2014/09/02/VehicleRoutingWithRealRoadDistances.html) a similar project recently and created [this repo](https://github.com/ge0ffrey/vrp-dataset-generator).

## Usage

 * Download the berlin.pbf from [geofabrik](http://download.geofabrik.de/europe/germany/berlin-latest.osm.pbf) or elsewhere
 * mvn clean install assembly:single
 * `java -jar target/*-jar-with-dependencies.jar osmreader.osm=berlin-latest.osm.pbf config=config.properties`
 * The first time will take a minute, the second time will be in a few milliseconds, depending on the matrix size

## License

This code stands under the Apache License
