package com.example

import com.github.plume.oss.Jimple2Cpg
import com.github.plume.oss.drivers.NeptuneDriver

import scala.util.Using

object NeptuneApp {

  def main(args: Array[String]): Unit = {
    val targetDir = "./example"

    println("Creating driver")
    Using.resource(new NeptuneDriver(
      hostname = "<neptune-cluster-address-without-https>",
      port = 8182,
      keyCertChainFile = "<pem-certificate-path-here>"
    )) { d =>
      println(s"Creating CPG from .class files found under $targetDir")
      new Jimple2Cpg().createCpg(rawSourceCodePath = targetDir, driver = d)
    }
    println(s"Done! CPG persisted on the database")
  }

}
