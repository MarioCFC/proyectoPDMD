package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.JSON.searchMovie

import com.google.gson.annotations.SerializedName


data class SearchMovies (

  @SerializedName("page"          ) var page         : Int?          = null,
  @SerializedName("results"       ) var results      : List<Results> = arrayListOf(),
  @SerializedName("total_results" ) var totalResults : Int?          = null,
  @SerializedName("total_pages"   ) var totalPages   : Int?          = null

)