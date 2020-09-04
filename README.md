# Plume Example Application

Repository showcasing an example use of the Plume libraries.

## Setup

This section outline the requirements and setup involved before using this application.

### System Requirements

* `git`
* `docker`
* `docker-compose`
* `java` (8-11)
* `gradle` (optional, as one can use `./gradlew`)

### Installation

Running `INSTALL` uses `git` to pull and build the latest stable Plume libraries based on the `develop` branch.

### Selecting a file to project

Example files can be found under `src/main/resources/examples`. Under `com.example.*App.java` a hard-coded file path to 
`Arithmetic1.java` is set but this can easily be changed to one of the other example files. Note that Plume can accept
a source, class, or directories as input.

## Example Setups

The basic operation that the `com.example.*App.java` classes do is:

1) Create a hook that connects to the selected graph database backend.
2) Loads a Java file to project to the database.
3) (TinkerGraph) Exports the projected graph as `graph.xml` to the base directory.

One can then visualize this graph using a visualization tool such as [Cytoscape](https://cytoscape.org/).

### TinkerGraph

_"TinkerGraph is a lightweight, POJO based, in-memory property graph that serves as the reference implementation for the 
property graph model."_ 

This requires no setup other than declaring the TinkerGraph dependencies in `build.gradle`. The 
TinkerGraph example can be run using `./gradlew tinkerGraphApp`.

### JanusGraph

_"JanusGraph is an open source, distributed graph database under The Linux Foundation. JanusGraph is available under the
Apache License 2.0. The project is supported by IBM, Google, and Hortonworks. JanusGraph supports various storage 
backends."_

To test with JanusGraph as the graph database backend, start by running the database with either:

Berkeley Java Edition and Lucene (embedded, lightweight)
```
docker-compose -f src/main/resources/docker/janus-berkeleyje-lucene.yml up
```
or Scylla and ElasticSearch (can cluster, is faster, can handle heavier loads)
```
docker-compose -f src/main/resources/docker/janus-scylla-es.yml up
```

Referring to the [JanusGraph documentation](https://docs.janusgraph.org/), one can configure this to use other storage 
backends such as Cassandra or HBase and other index engines such as Solr.

The JanusGraph example can be run using `./gradlew janusGraphApp`. The JanusGraph remote config file can be found under 
`src/main/resources/conf/remote.properties`.

## Connecting to the Gremlin Console

The Gremlin Console is a Groovy Console that allows one to connect to the graph database and run Gremlin queries. A 
separate graph database instance is required i.e. not in-memory configurations. Using JanusGraph as an example:

1) Start JanusGraph using `docker-compose`. (See [JanusGraph](#janusgraph))
2) Project a program's CPG into JanusGraph with `./gradlew janusGraphApp`.
3) Run the Gremlin Console using `docker exec -it janusgraph-grapl ./bin/gremlin.sh`.
4) Connect to the JanusGraph instance "remotely" using:
```groovy
:remote connect tinkerpop.server conf/remote.yaml
:remote console
```

You should now be connected to the JanusGraph database with the Gremlin Console and can run some basic queries such as:

*List all classes*:
```
gremlin> g.V().hasLabel("FILE").values("name").toList()
==>Arithmetic1
```
*How many times each variable is assigned within a method*:
```
gremlin> g.V().has("METHOD", "fullName", "intraprocedural.arithmetic.Arithmetic1.main") \
    .repeat(__.out("AST")).emit().has("BLOCK", "name", "STORE") \
        .out().hasLabel("LOCAL") \
    .groupCount("lv").by("name").cap("lv")
==>{1=1, 2=1, 3=1, 4=1, 5=1, 6=1}
```

TODO: Add more operations

# TigerGraph

```
docker-compose -f src/main/resources/docker/tigergraph.yml up
```

## Contributing

If you find a bug or would like to see a [certain kind of query](#connecting-to-the-gremlin-console) on this repository
then simply open up an issue with the following format:
### Bug Issue
* Title: "BUG: \<short title\>"
* Brief description of expected output vs actual output
* Steps to reproduce
### Query Example
* Title: "QRY: \<short title\>"
* Description of the output you would like to see given a CPG
### Documentation
* Title: "DOC: \<short title\>"
* Description of what you would like explained or documented better

If you can fix these yourself then by all means fork, and my a pull request linked to the issue. Otherwise, I will see to 
the fix or enhancement myself.
