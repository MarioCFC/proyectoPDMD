package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class ProductionCountries (

  @SerializedName("iso_3166_1" ) var iso31661 : String? = null,
  @SerializedName("name"       ) var name     : String? = null

): Serializable