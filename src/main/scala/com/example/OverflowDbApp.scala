package com.example

import com.github.plume.oss.Jimple2Cpg
import com.github.plume.oss.domain.DataFlowCacheConfig
import com.github.plume.oss.drivers.OverflowDbDriver

import java.io.File
import java.util.Optional
import scala.util.Using

object OverflowDbApp {

  def main(args: Array[String]): Unit = {
    val targetDir = "./example"
    val dbOutputFile = "cpg.odb"
    val dbOutputXml = "cpg.xml"

    println("Creating driver")
    Using.resource(new OverflowDbDriver(
      storageLocation = Some(dbOutputFile),
      cacheConfig = DataFlowCacheConfig(dataFlowCacheFile = None)
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

    println("Finding data flows from arguments to 'taint' that are eventually passed to `println`")
    val cpg = d.cpg
    d.flowsBetween(() => cpg.call("taint").argument, () => cpg.call("println"))
      .map { result => result.path.map(x => (x.node.method.name, x.node.code, x.node.label, x.node.propertyOption("LINE_NUMBER"))) }
      .distinct
      .map { n: Vector[(String, String, String, Optional[AnyRef])] =>
        n.map { case (methodName, code, nodeLabel, lineNumber) =>
          if (lineNumber.isPresent) {
            s"[$methodName:${lineNumber.get()}] $code ($nodeLabel)"
          } else {
            s"[$methodName] $code ($nodeLabel)"
          }
        }
      }
      .zipWithIndex
      .foreach { case (path, i) =>
        println(s"PATH $i:")
        path.foreach { pathElement => println(s"\t$pathElement") }
      }
  }

}
