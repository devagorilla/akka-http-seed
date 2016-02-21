package io.github.devagorilla

import java.lang.management.ManagementFactory
import akka.actor.ActorSystem
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

trait StatusService extends BaseService {
  protected val serviceName = "my service"
  import scala.concurrent.ExecutionContext.Implicits.global

  protected val routes = pathPrefix("api" / "v1") {
    (get & path(Segment)) { id =>
      complete {
        ProxyStore.fetchInfo(id).map[ToResponseMarshallable] {
          case Right(zombie)      => zombie
          case Left(errorMessage) => StatusCodes.BadRequest -> errorMessage
        }
      }
    }
  }
}
