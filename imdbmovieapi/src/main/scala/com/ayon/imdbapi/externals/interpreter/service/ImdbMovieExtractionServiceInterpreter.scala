package com.ayon.imdbapi.externals.interpreter.service


import akka.http.scaladsl.HttpExt
import akka.http.scaladsl.model.{HttpResponse, ResponseEntity, StatusCodes}
import akka.stream.Materializer
import akka.util.ByteString
import cats.data.{EitherT, Kleisli}
import cats.implicits._
import com.ayon.imdbapi.domain.movies.entities.{Movie, MovieInformation, MovieNotFound}
import com.ayon.imdbapi.domain.movies.repository.MovieInformationRepository
import com.ayon.imdbapi.domain.movies.service.{ImdbMovieExtractionService, MovieExtraction, MovieInfo}
import play.api.libs.json.{JsDefined, Json}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
class ImdbMovieExtractionServiceInterpreter(http: HttpExt)(implicit val materializer: Materializer)
  extends ImdbMovieExtractionService {
  override def getMovieInfo(imdbId: String): MovieExtraction[MovieInformation] =
    Kleisli[MovieInfo, MovieInformationRepository, MovieInformation] {
    repo: MovieInformationRepository =>
      EitherT {
        repo.getMovieInfo(imdbId)(http) flatMap {
          case HttpResponse(StatusCodes.OK, _, entity, _) => bodyToString(entity)
              .map {
              ImdbMovieExtractionServiceInterpreter.parse(_, imdbId).asRight[String]
              }
          case _  => Future.successful("Service Not Available".asLeft[MovieInformation])
        }
      }
  }


 private def bodyToString(entity: ResponseEntity): Future[String] =
   entity.dataBytes.runFold(ByteString(""))(_ ++ _).map(_.utf8String)
}

object ImdbMovieExtractionServiceInterpreter {

 private def parse(jsonString: String, imdbId: String): MovieInformation = {
    val jsValue = Json.parse(jsonString)
    jsValue \ "Error" match {
      case JsDefined(error) => MovieNotFound(error.as[String])
      case _ =>  Movie(imdbId, ((jsValue \ "Title").as[String]))
    }
 }
}