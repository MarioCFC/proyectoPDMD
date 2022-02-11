package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities

import com.google.gson.annotations.SerializedName


data class Movie (
//Los parametros obligatorios son title,rating,runtimeMinutes
    @SerializedName("id"                  ) var id                  : String? = null,
    @SerializedName("title"               ) var title               : String? = null,
    @SerializedName("rating"              ) var rating              : String? = null,
    @SerializedName("runtimeMinutes"      ) var runtimeMinutes      : String? = null,
    @SerializedName("genre"               ) var genre               : String? = null,
    @SerializedName("description"         ) var description         : String? = null,
    @SerializedName("imageUrl"            ) var imageUrl            : String? = null,
    @SerializedName("trailerUrl"          ) var trailerUrl          : String? = null,

){
    fun getDataEditTextDataArray(): Array<String?> {
        return arrayOf(title,genre,runtimeMinutes,rating,description)
    }
    //TODO:Cambiar de sitio
    fun getCadenasDatosPeliculaNull(): Array<String>{
        return arrayOf("Sin titulo","Sin genero","Sin duracion","Sin valoraciones","Sin sinopsis")
    }
}