name := "Plume Examples"
organization := "com.example"
version := "0.1.0-SNAPSHOT"
scalaVersion := "2.13.7"

lazy val root = (project in file("."))
  .settings(
    name := "plume-examples"
  )

val plumeVersion = "1.1.3"
val log4jVersion = "2.17.2"

resolvers ++= Seq(
  Resolver.mavenLocal,
  Resolver.mavenCentral,
  Resolver.JCenterRepository,
  "jitpack" at "https://jitpack.io",
)

libraryDependencies ++= Seq(
  "com.github.plume-oss" % "plume" % plumeVersion,
  "org.apache.logging.log4j" % "log4j-core" % log4jVersion,
  "org.apache.logging.log4j" % "log4j-slf4j-impl" % log4jVersion
)
