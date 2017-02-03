package org.beeherd.riemann.monitor

import scala.sys.process._
import scala.util.{Failure, Success, Try}

object App {

  def main(args: Array[String]): Unit = {
    val parsedArgs = args.toList match {
      case h :: p :: s :: pp :: "--" :: rest => Try { (h, p.toInt, s, pp.toInt, rest) }
      case _ => Failure(new Exception("Invalid Usage"))
    }

    parsedArgs match {
      case Success((riemannHost, riemannPort, riemannService, pollPeriodInSecs, cmd)) =>
        val proc = Process(cmd).run()
      case Failure(e) =>
        println(e.getMessage) // :(
        printUsage() // are we guaranteed they always need to see usage here?
        sys.exit(1)
    }
  }

  def printUsage(): Unit = {
    println("Usage: <cmd> <riemann-host> <riemann-port> <riemann-service-name> -- <cmd>")
  }
}
