package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Genres (

  @SerializedName("id"   ) var id   : Int?    = 0,
  @SerializedName("name" ) var name : String? = "Sin genero"

): Serializable