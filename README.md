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

Replace `X.X.X` with the desired version on [jCenter](https://bintray.com/plume-oss/maven/plume-core/_latestVersion).

Maven:
```mxml
<dependency>
  <groupId>io.github.plume-oss</groupId>
  <artifactId>plume</artifactId>
  <version>X.X.X</version>
  <type>pom</type>
</dependency>
```

Gradle:
```groovy
implementation 'io.github.plume-oss:plume:X.X.X'
```

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

### OverflowDb

_"ShiftLeft's OverflowDB is an in-memory graph database, which implements a swapping mechanism to deal with large 
graphs."_

This requires no setup other than declaring the OverflowDb dependencies in `build.gradle`. The
OverflowDb example can be run using `./gradlew overflowDbApp`.

### JanusGraph

_"JanusGraph is an open source, distributed graph database under The Linux Foundation. JanusGraph is available under the
Apache License 2.0. The project is supported by IBM, Google, and Hortonworks. JanusGraph supports various storage 
backends."_

To run with JanusGraph as the graph database backend, start by running the database with either:

Berkeley Java Edition and Lucene (embedded, lightweight)
```bash
docker-compose -f src/main/resources/docker/janus-berkeleyje-lucene.yml up
```
or Scylla and ElasticSearch (can cluster, is faster, can handle heavier loads)
```bash
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
```groovy
gremlin> g.V().hasLabel("FILE").values("name").toList()
==>Basic1
```
*How many times each variable is assigned within a method*:
```groovy
gremlin> g.V().has("METHOD", "fullName", "intraprocedural.basic.Basic1.main") \
    .repeat(__.out("AST")).emit().has("BLOCK", "name", "STORE") \
        .out().hasLabel("LOCAL") \
    .groupCount("lv").by("name").cap("lv")
==>{1=1, 2=1, 3=1, 4=1, 5=1, 6=1}
```

TODO: Add more operations

# TigerGraph

_TigerGraph is the fastest and most scalable graph database analytics platform—and the only native parallel graph database._

To run with TigerGraph as the graph database backend, start by running the database with:

```bash
docker-compose -f src/main/resources/docker/tigergraph.yml up
```

Running the TigerGraph example:

1) Start TigerGraph using `docker-compose`. This will install the schema and queries. Make sure the queries have 
finished installing before moving to the next step.
2) Project a program's CPG into TigerGraph with `./gradlew tigerGraphApp`.
3) Open your web-browser and navigate to http://localhost:14240/ to view GraphStudio where you can play around with the CPG.

# Amazon Neptune

_Amazon Neptune is a fast, reliable, fully managed graph database service that makes it easy to build and run applications that work with highly connected datasets._

To communicate with a Neptune cluster, one needs to run this examples repository from within an EC2 instance on the same
VPC as the Neptune cluster. More on how this works can be found in the 
[documentation](https://plume-oss.github.io/plume-docs/storage-backends/neptune/).

Running the Neptune example:

1) Make sure the Neptune cluster is up and fill in the missing parameters under `src/main/java/com/example/NeptuneApp`.
2) Project a program's CPG into Neptune with `./gradlew neptuneApp`.

# Neo4j

_Neo4j is the graph database platform powering mission-critical enterprise applications like artificial intelligence, fraud detection and recommendations._

To run with Neo4j as the graph database backend, start by running the database with:

```bash
docker-compose -f src/main/resources/docker/neo4j.yml up
```

This starts up the Bitnami Neo4j image. Running the Neo4j example:

1) Project a program's CPG into Neptune with `./gradlew neo4jApp`.
2) View the CPG by navigating to http://localhost:7474 and (if required) log in with the following credentials

    Username: `neo4j`
    
    Password: `neo4j123`

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
