package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.searchMovie

import com.google.gson.annotations.SerializedName
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.PeliculaJSON


data class ListaPeliculas (
    @SerializedName("listaPeliculas"       ) var listaPeliculas      : ArrayList<PeliculaJSON> = arrayListOf()
)