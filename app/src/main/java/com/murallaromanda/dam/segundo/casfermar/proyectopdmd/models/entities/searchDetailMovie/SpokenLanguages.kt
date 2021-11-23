package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class SpokenLanguages (

  @SerializedName("english_name" ) var englishName : String? = null,
  @SerializedName("iso_639_1"    ) var iso6391     : String? = null,
  @SerializedName("name"         ) var name        : String? = null

): Serializable