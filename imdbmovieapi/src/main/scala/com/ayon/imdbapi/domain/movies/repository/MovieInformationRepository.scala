package com.ayon.imdbapi.domain.movies.repository

trait MovieInformationRepository {
  def getMovieInfo(imdbId: String): MovieInfo
}
