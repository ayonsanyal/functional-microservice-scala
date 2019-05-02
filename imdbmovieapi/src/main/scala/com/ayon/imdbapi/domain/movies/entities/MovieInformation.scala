package com.ayon.imdbapi.domain
package movies
package entities
sealed trait MovieInformation
case class Movie(imdbId:String, title:String, language:String= "English") extends MovieInformation
case class MovieNotFound(reason:String) extends MovieInformation

