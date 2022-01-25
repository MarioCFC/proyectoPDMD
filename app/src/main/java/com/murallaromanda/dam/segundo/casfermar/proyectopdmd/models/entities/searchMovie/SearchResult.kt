package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.searchMovie

import com.google.gson.annotations.SerializedName


data class SearchResults (
  //Datos sobre la busqueda
  @SerializedName("page"          ) var page         : Int?          = null,
  @SerializedName("results"       ) var resultShortDataMovie  : List<ShortDataMovieSearchResult> = arrayListOf(),
  @SerializedName("total_results" ) var totalResults : Int?          = null,
  @SerializedName("total_pages"   ) var totalPages   : Int?          = null

)