 package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.JSON.searchMovie

import com.google.gson.annotations.SerializedName


data class Results (

  @SerializedName("poster_path"       ) var posterPath       : String?   = null,
  @SerializedName("id"                ) var id               : Int?      = null,
  @SerializedName("title"             ) var title            : String?   = null,
  @SerializedName("vote_average"      ) var voteAverage      : Double?   = null

)
