package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities

import com.google.gson.annotations.SerializedName


data class ProductionCompanies (

  @SerializedName("id"             ) var id            : Int?    = null,
  @SerializedName("logo_path"      ) var logoPath      : String? = null,
  @SerializedName("name"           ) var name          : String? = null,
  @SerializedName("origin_country" ) var originCountry : String? = null

)