package com.ayon.imdbapi.domain.movies.service

import com.ayon.imdbapi.domain.movies.entities.MovieInformation

trait ImdbMovieExtractionService {
  def getMovieInfo(imdbId: String): MovieExtraction[MovieInformation]
}
