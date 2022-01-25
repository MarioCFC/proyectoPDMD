package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class PeliculaJSON (
//Datos de una película
  @SerializedName("backdrop_path"         ) var backdropPath        : String?                   = "Sin fondo",
  @SerializedName("genres"                ) var genres              : List<Genres>              = arrayListOf(),
  @SerializedName("overview"              ) var overview            : String?                   = "Sin sinopsis",
  @SerializedName("poster_path"           ) var posterPath          : String?                   = "Sin poster",
  @SerializedName("runtime"               ) var runtime             : Int?                      = 0,
  @SerializedName("title"                 ) var title               : String?                   = "Sin titulo",
  @SerializedName("video"                 ) var video               : Boolean?                  = false,
  @SerializedName("vote_average"          ) var voteAverage         : Double?                   = 0.0,
):Serializable