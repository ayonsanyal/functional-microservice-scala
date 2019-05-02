package com.ayon.imdbapi.externals.routes

import akka.http.scaladsl.model.StatusCodes.InternalServerError
import akka.http.scaladsl.server.Route
import akka.stream.Materializer
import com.ayon.imdbapi.domain.movies.repository.MovieInformationRepository
import com.ayon.imdbapi.domain.movies.service.ImdbMovieExtractionService
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.auto._
import io.circe.syntax._
import scala.concurrent.ExecutionContext

class Routes(svc: ImdbMovieExtractionService,repo: MovieInformationRepository)
            (implicit val executionContext: ExecutionContext,mat:Materializer) extends FailFastCirceSupport {
def routes:Route = {
  import akka.http.scaladsl.server.Directives._
  pathPrefix("api")
  {
    pathPrefix("v1")
    {
      pathPrefix("movies")
      {
        get {
          getMovieNames
        }
      }
    }
  }
}

  def getMovieNames: Route = {
  import akka.http.scaladsl.server.Directives._
    path(Segment)
  { imdbId =>
    onComplete(svc.getMovieInfo(imdbId)(repo).value)
    {
      case util.Success(Right(value)) => complete(value.asJson)
      case util.Success(Left(error))=> complete((InternalServerError, error))
      case util.Failure(exception) => complete(InternalServerError)
    }
  }
}
}
