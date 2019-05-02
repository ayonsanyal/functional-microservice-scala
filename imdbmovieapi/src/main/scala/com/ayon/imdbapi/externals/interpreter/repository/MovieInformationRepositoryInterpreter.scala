package com.ayon.imdbapi.externals
package interpreter
package repository

import akka.http.scaladsl.HttpExt
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import cats.data.Kleisli
import com.ayon.imdbapi.domain.movies.repository.{MovieInfo, MovieInformationRepository}
import com.typesafe.config.ConfigFactory

import scala.concurrent.Future

class MovieInformationRepositoryInterpreter extends MovieInformationRepository {

  override def getMovieInfo(imdbId: String): MovieInfo = Kleisli[Future, HttpExt, HttpResponse] { (http: HttpExt) => {
    val imdbUrl = ConfigFactory.load().getString("myApiFilmsUrl")
    val myApiFilmsToken = ConfigFactory.load().getString("myApiFilmsToken")
    val url = s"$imdbUrl$imdbId&apikey=$myApiFilmsToken"
    http.singleRequest(HttpRequest(uri = url))
  }
  }
}
