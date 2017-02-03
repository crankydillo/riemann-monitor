package org.beeherd.riemann.monitor

import scala.sys.process._
import scala.util.{Failure, Success, Try}

import io.riemann.riemann.client.RiemannClient

object App {

  def main(args: Array[String]): Unit = {
    val parsedArgs = args.toList match {
      case host :: port :: svc :: poll :: "--" :: cmd => 
        Try { 
          require(!cmd.isEmpty) // cmd is a list
          (host, port.toInt, svc, poll.toInt, cmd) 
        }
      case _ => 
        Failure(new Exception("Invalid Usage"))
    }

    parsedArgs match {
      case Success((riemannHost, riemannPort, riemannService, pollPeriodInSecs, cmd)) =>

        val timePadInSecs = 10.0f

        def sendRiemannEvent() = {
          // should I leave this client open, especially if the poll period is
          // quick?
          val c = RiemannClient.tcp(riemannHost, riemannPort)
          // Use scala-arm?  If not, explain why deviating from scala.util.Try
          try {
            c.connect()
            c.event()
              .service(riemannService)
              .state("running")
              .description(cmd.mkString(" "))
              .tags(cmd.head)
              .ttl(pollPeriodInSecs + timePadInSecs)
              .send().deref(5000, java.util.concurrent.TimeUnit.MILLISECONDS)
          } catch {
            case e: Exception => 
              // Are we bringing in a logging lib?  Is there a point in
              // re-trying given that we loop the event?
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
    println("Usage: <cmd> <riemann-host> <riemann-port> <riemann-service-name> <poll-period> -- <cmd>")
  }
}
