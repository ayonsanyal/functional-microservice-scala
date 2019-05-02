package com.ayon.imdbapi.domain.movies

import akka.http.scaladsl.HttpExt
import akka.http.scaladsl.model.HttpResponse
import cats.data.Kleisli

import scala.concurrent.Future

package object repository {
  type MovieInfo = Kleisli[Future,HttpExt,HttpResponse]
}
