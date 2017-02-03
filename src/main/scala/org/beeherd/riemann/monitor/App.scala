package org.beeherd.riemann.monitor

import scala.sys.process._

object App {

  def main(args: Array[String]): Unit = {
    val (riemannHost, riemannPort, riemannService, cmd) = args.toList match {
      case h :: p :: s :: "--" :: rest => (h, p, s, rest)
      case _ => 
        printUsage()
        sys.exit(1)
    }
    
    Process(cmd).run()
  }

  def printUsage(): Unit = {
    println("Usage: <cmd> <riemann-host> <riemann-port> <riemann-service-name> -- <cmd>")
  }
}
