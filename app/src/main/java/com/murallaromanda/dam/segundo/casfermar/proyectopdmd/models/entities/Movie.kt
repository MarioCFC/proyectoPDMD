package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities

import android.content.Context
import com.google.gson.annotations.SerializedName
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R


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
        fun getCadenasDatosPeliculaNull(context: Context): Array<String> {
            return arrayOf(
                context.getString(R.string.stringNullIdMovie),
                context.getString(R.string.stringNullTitleMovie),
                context.getString(R.string.stringNullGenreMovie),
                context.getString(R.string.stringNullRunTimeMovie),
                context.getString(R.string.stringNullRatingMovie),
                context.getString(R.string.stringNulDescriptionMovie)
            )
        }
    }
}