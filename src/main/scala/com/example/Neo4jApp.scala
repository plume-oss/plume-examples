package com.example

import com.github.plume.oss.Jimple2Cpg
import com.github.plume.oss.drivers.Neo4jDriver

import scala.util.Using

object Neo4jApp {

  def main(args: Array[String]): Unit = {
    val targetDir = "./example"

    println("Creating driver")
    Using.resource(new Neo4jDriver(
      hostname = "localhost",
      port = 7474,
      username = "neo4j",
      password = "neo4j"
    )) { d =>
      println(s"Creating CPG from .class files found under $targetDir")
      d.buildSchema() // Optional in Neo4j - also adds indices
      new Jimple2Cpg().createCpg(rawSourceCodePath = targetDir, driver = d)
    }
    println(s"Done! CPG persisted on the database")
  }

}
