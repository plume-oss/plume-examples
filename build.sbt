name := "Plume Examples"
organization := "com.example"
version := "0.1.0-SNAPSHOT"
scalaVersion := "2.13.7"

lazy val root = (project in file("."))
  .settings(
    name := "plume-examples"
  )

val plumeVersion = "1.0.16"
val logbackVersion = "1.2.10"

resolvers ++= Seq(
  Resolver.mavenLocal,
  Resolver.mavenCentral,
  Resolver.JCenterRepository,
  "jitpack" at "https://jitpack.io",
)

libraryDependencies ++= Seq(
  "com.github.plume-oss" % "plume" % plumeVersion,
  "ch.qos.logback" % "logback-classic"  % logbackVersion,
)
