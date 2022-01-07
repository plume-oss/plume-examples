package com.example

import com.github.plume.oss.Jimple2Cpg
import com.github.plume.oss.drivers.OverflowDbDriver

import scala.util.Using

object OverflowDbApp {

  def main(args: Array[String]): Unit = {
    val targetDir = "./example"
    val dbOutputFile = "cpg.odb"

    println("Creating driver")
    Using.resource(new OverflowDbDriver(
      storageLocation = Some(dbOutputFile)
    )) { d =>
      println(s"Creating CPG from .class files found under $targetDir")
      new Jimple2Cpg().createCpg(rawSourceCodePath = targetDir, driver = d)
    }
    println(s"Done! CPG persisted at $dbOutputFile")
  }

}
