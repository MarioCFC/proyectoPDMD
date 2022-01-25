 package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.searchMovie

import com.google.gson.annotations.SerializedName
import java.io.Serializable


 data class ShortDataMovieSearchResult (
  //Datos reducidos de cada pelicula resultado de una búsqueda
  @SerializedName("poster_path"       ) var posterPath       : String?   = null,
  @SerializedName("id"                ) var id               : Int?      = null,
  @SerializedName("title"             ) var title            : String?   = null,
  @SerializedName("vote_average"      ) var voteAverage      : Double?   = null
):Serializable
