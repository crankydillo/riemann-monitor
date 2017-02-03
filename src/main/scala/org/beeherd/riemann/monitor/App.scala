package org.beeherd.riemann.monitor

import scala.sys.process._
import scala.util.{Failure, Success, Try}

import io.riemann.riemann.client.RiemannClient

object App {

  def main(args: Array[String]): Unit = {
    val parsedArgs = args.toList match {
      case h :: p :: s :: pp :: "--" :: rest => Try { (h, p.toInt, s, pp.toInt, rest) }
      case _ => Failure(new Exception("Invalid Usage"))
    }

    parsedArgs match {
      case Success((riemannHost, riemannPort, riemannService, pollPeriodInSecs, cmd)) =>

        def sendRiemannEvent() = {
          // should I leave this client open, especially if the poll period is
          // quick?
          val c = RiemannClient.tcp(riemannHost, riemannPort)
          // Use scala-arm?  If not, explain why deviating from scala.util.Try
          try {
            c.connect()
            // what is this deref thing?
            c.event().service(riemannService).state("running").tags(cmd.mkString(" "))
              .send().deref(5000, java.util.concurrent.TimeUnit.MILLISECONDS)
          } catch {
            case e: Exception => 
              // are we bringing in a logging lib?
              println(e.getMessage)
          } finally {
            c.close()
          }
        }

        val proc = Process(cmd).run()
        while (proc.isAlive()) {
          sendRiemannEvent()
          Thread.sleep(pollPeriodInSecs * 1000)
        }
        proc.exitValue()
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
