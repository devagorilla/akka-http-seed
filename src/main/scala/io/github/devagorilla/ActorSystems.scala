package io.github.devagorilla

import akka.actor.ActorSystem
import akka.stream.Materializer

object ActorSystems {
  var systemR: Option[ActorSystem] = None

  var materializerR: Option[Materializer] = None
}
