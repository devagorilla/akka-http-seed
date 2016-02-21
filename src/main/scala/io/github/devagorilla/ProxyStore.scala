package io.github.devagorilla

import java.io.IOException

import akka.actor.ActorSystem
import akka.http.scaladsl.client.RequestBuilding
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{ HttpHeader, StatusCodes }
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer

import scala.concurrent.{ ExecutionContext, Future }

object ProxyStore extends ClientHelper {

  protected val clientName: String = "localhost"
  protected val port: Int = 8080

  def fetchInfo(id: String)(implicit ec: ExecutionContext): Future[Either[String, String]] = {
    val request = RequestBuilding.Get(s"/api/v3/user/$id").
      addHeader(RawHeader("Authorization", "token  baa9fb94c9ba1cedd9ba471fda23c1783f4bd33c"))
    makeRequest(request).flatMap { response =>
      response.status match {
        case StatusCodes.OK         => Unmarshal(response.entity).to[String].map(Right(_))
        case StatusCodes.BadRequest => Future.successful(Left(s"bad request"))
        case _ => Unmarshal(response.entity).to[String].flatMap { entity =>
          val error = s"FAIL - ${response.status}"
          Future.failed(new IOException(error))
        }
      }

    }

  }

  //  def clrStr(in : String) : String = in.stripMargin.replace("\n", "")
  //    .replaceAll("[\\x00-\\x09\\x11\\x12\\x14-\\x1F\\x7F]", "")

}

