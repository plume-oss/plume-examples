package com.example

import com.github.plume.oss.Jimple2Cpg
import com.github.plume.oss.drivers.TinkerGraphDriver

import scala.util.Using

object TinkerGraphApp {

  def main(args: Array[String]): Unit = {
    val targetDir = "./example"
    val dbOutputFile = "cpg.xml"

    println("Creating driver")
    Using.resource(new TinkerGraphDriver()) { d =>
      println(s"Creating CPG from .class files found under $targetDir")
      new Jimple2Cpg().createCpg(rawSourceCodePath = targetDir, driver = d)
      d.exportGraph(dbOutputFile)
    }
    println(s"Done! CPG persisted at $dbOutputFile")
  }

}
