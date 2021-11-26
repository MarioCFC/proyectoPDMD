package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class PeliculaJSON (

  @SerializedName("backdrop_path"         ) var backdropPath        : String?                   = null,
  @SerializedName("genres"                ) var genres              : List<Genres>              = arrayListOf(),
  @SerializedName("id"                    ) var id                  : Int?                      = null,
  @SerializedName("imdb_id"               ) var imdbId              : String?                   = null,
  @SerializedName("overview"              ) var overview            : String?                   = null,
  @SerializedName("poster_path"           ) var posterPath          : String?                   = null,
  @SerializedName("title"                 ) var title               : String?                   = null,
  @SerializedName("video"                 ) var video               : Boolean?                  = null,
  @SerializedName("vote_average"          ) var voteAverage         : Double?                   = null,
):Serializable