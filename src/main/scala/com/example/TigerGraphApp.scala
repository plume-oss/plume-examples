package com.example

import com.github.plume.oss.Jimple2Cpg
import com.github.plume.oss.drivers.TigerGraphDriver

import scala.util.Using

object TigerGraphApp {

  def main(args: Array[String]): Unit = {
    val targetDir = "./example"

    println("Creating driver")
    Using.resource(new TigerGraphDriver(
      hostname = "localhost",
      restPpPort = 9000,
      gsqlPort = 14240,
      username = "tigergraph",
      password = "tigergraph",
    )) { d =>
      println(s"Creating CPG from .class files found under $targetDir")
      new Jimple2Cpg().createCpg(rawSourceCodePath = targetDir, driver = d)
    }
    println(s"Done! CPG persisted on the database")
  }

}
