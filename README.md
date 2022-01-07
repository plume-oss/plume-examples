# Plume Example Application

Repository showcasing an example use of the Plume library.

## Known Issues

All known issues can be found [here](https://github.com/plume-oss/plume/issues).

## Setup

This section outline the requirements and setup involved before using this application.

### System Requirements

* `git`
* `docker`
* `docker-compose`
* `java` (11+)
* `sbt`

### Installation

Replace `X.X.X` with the desired version on [![](https://jitpack.io/v/plume-oss/plume.svg)](https://jitpack.io/#plume-oss/plume).

```sbt
libraryDependencies ++= Seq(
  com.github.plume-oss % plume % X.X.X
)

resolvers += "jitpack" at "https://jitpack.io"
```


### Selecting a file to project

Example code to analyze can be found under `example`. The `.class` files are used
during the CPG extraction but the source `.java` files are there for reference.

## Example Setups

The basic operation that the `com.example.*App.java` classes do is:

1) Create a driver that connects to the selected graph database backend.
2) Loads a directory with class files to project to the database.
3) TinkerGraph and OverflowDb can serialize and deserialize the CPG to files whereas the other databases store the
   graphs onto disk in their systems.

One can then visualize this graph using a visualization tool such as [Cytoscape](https://cytoscape.org/).

### TinkerGraph

_"TinkerGraph is a lightweight, POJO based, in-memory property graph that serves as the reference implementation for the
property graph model."_

The TinkerGraph example can be run using `com.example.TinkerGraphApp::main`.

### OverflowDb

_"ShiftLeft's OverflowDB is an in-memory graph database, which implements a swapping mechanism to deal with large
graphs."_

The OverflowDb example can be  run using `com.example.OverflowDbApp::main`. To serialize/de-serialize the database, the
location needs to be specified in the constructor.

If the file does not exist, then it will be created. If it does exist then that CPG will be imported.

### TigerGraph

_TigerGraph is the fastest and most scalable graph database analytics platform—and the only native parallel graph database._

To run with TigerGraph as the graph database backend, start by running the database with:

```bash
docker-compose -f src/main/resources/tigergraph.yml up
```

Running the TigerGraph example:

1) Start TigerGraph using `docker-compose`. This will install the schema and queries. Make sure the queries have
   finished installing before moving to the next step.
2) Project a program's CPG into TigerGraph with `com.example.TigerGraphApp::main`
3) Open your web-browser and navigate to http://localhost:14240/ to view GraphStudio where you can play around with the CPG.

# Amazon Neptune

_Amazon Neptune is a fast, reliable, fully managed graph database service that makes it easy to build and run applications that work with highly connected datasets._

To communicate with a Neptune cluster, one needs to run this examples repository from within an EC2 instance on the same
VPC as the Neptune cluster. More on how this works can be found in the
[documentation](https://plume-oss.github.io/plume-docs/storage-backends/neptune/).

Running the Neptune example:

1) Make sure the Neptune cluster is up and fill in the missing parameters under `com.example.NeptuneApp`.
2) Project a program's CPG into Neptune with `com.example.NeptuneApp::main`

# Neo4j

_Neo4j is the graph database platform powering mission-critical enterprise applications like artificial intelligence, fraud detection and recommendations._

To run with Neo4j as the graph database backend, start by running the database with:

```bash
docker-compose -f src/main/resources/neo4j.yml up
```

This starts up the Bitnami Neo4j image. Running the Neo4j example:

1) Project a program's CPG into Neo4j with `com.example.Neo4jApp::main`
2) View the CPG by navigating to `http://localhost:7474` and (if required) log in with the following credentials

   Username: `neo4j`

   Password: `neo4j`
