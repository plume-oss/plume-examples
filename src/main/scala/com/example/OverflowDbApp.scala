package com.example

import com.github.plume.oss.Jimple2Cpg
import com.github.plume.oss.drivers.OverflowDbDriver

import java.io.File
import scala.util.Using

object OverflowDbApp {

  def main(args: Array[String]): Unit = {
    val targetDir = "./example"
    val dbOutputFile = "cpg.odb"
    val dbOutputXml = "cpg.xml"

    println("Creating driver")
    Using.resource(new OverflowDbDriver(
      storageLocation = Some(dbOutputFile),
      dataFlowCacheFile = None
    )) { d =>
      println(s"Creating CPG from .class files found under $targetDir")
      new Jimple2Cpg().createCpg(rawSourceCodePath = targetDir, driver = d)
      taintAnalysis(d)
      d.exportAsGraphML(new File(dbOutputXml))
    }
    println(s"Done! CPG persisted at $dbOutputFile")
  }

  def taintAnalysis(d: OverflowDbDriver): Unit = {
    import io.shiftleft.semanticcpg.language._
    import io.shiftleft.codepropertygraph.{Cpg => CPG}

    println("Finding data flows from source identifiers with the name 'a' sinking at methods with names 'add'")
    val cpg = CPG(d.cpg.graph)
    d
      .nodesReachableBy(cpg.identifier("a"), cpg.call("add"))
      .map { n => s"${n.label}: ${n.property("CODE")} @ line ${n.property("LINE_NUMBER")}" }
      .foreach(println(_))
  }

}
