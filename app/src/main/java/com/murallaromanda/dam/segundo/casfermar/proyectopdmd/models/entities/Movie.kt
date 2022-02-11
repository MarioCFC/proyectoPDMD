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
        return arrayOf(id,title, genre, runtimeMinutes, rating, description,imageUrl)
    }

    fun setEditTextDataOfArray(datos: Array<String?>) {
        id = datos[0]
        title = datos[1]!!
        genre = datos[2]
        runtimeMinutes = datos[3]
        rating = datos[4]
        description = datos[5]
        imageUrl = datos[6]
    }

    //TODO:Cambiar de sitio
    companion object {
        fun getCadenasDatosPeliculaNull(): Array<String> {
            return arrayOf(
                "Sin id",
                "Sin titulo",
                "Sin genero",
                "Sin duracion",
                "Sin valoraciones",
                "Sin sinopsis"
            )
        }
    }
}