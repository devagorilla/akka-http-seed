package io.github.devagorilla

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ HttpResponse, HttpRequest }
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{ Sink, Source, Flow }

import scala.concurrent.{ ExecutionContext, Future }

trait ClientHelper extends BaseClient {
  implicit val system = ActorSystems.systemR.getOrElse(ActorSystem("akka-client"))
  implicit val materializer = ActorSystems.materializerR.getOrElse(ActorMaterializer())

  lazy val connection: Flow[HttpRequest, HttpResponse, Any] =
    Http().outgoingConnection(clientName, port)

  def makeRequest(req: HttpRequest): Future[HttpResponse] = {
    Source.single(req).via(connection).runWith(Sink.head)
  }

}
