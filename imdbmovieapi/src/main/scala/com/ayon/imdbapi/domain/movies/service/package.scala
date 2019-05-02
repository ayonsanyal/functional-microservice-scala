package com.ayon.imdbapi.domain.movies

import cats.data.{EitherT, Kleisli}
import com.ayon.imdbapi.domain.movies.repository.MovieInformationRepository

import scala.concurrent.Future

package object service {
type MovieInfo[A] = EitherT[Future,String,A]
type MovieExtraction[A] = Kleisli[MovieInfo,MovieInformationRepository,A]
}
