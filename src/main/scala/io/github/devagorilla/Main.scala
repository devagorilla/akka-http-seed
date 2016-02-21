package io.github.devagorilla

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContext

object Main extends App with Config with StatusService {
  override protected implicit val system: ActorSystem = ActorSystem("akka-http-actor-system")
  override protected implicit val materializer: ActorMaterializer = ActorMaterializer()

  ActorSystems.systemR = Some(system)
  ActorSystems.materializerR = Some(materializer)

  Http().bindAndHandle(routes, httpInterface, httpPort)

}
