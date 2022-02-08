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
    @SerializedName("releaseYear"         ) var releaseYear         : String? = null,
    @SerializedName("country"             ) var country             : String? = null,
    @SerializedName("ageRating"           ) var ageRating           : String? = null,
    @SerializedName("directorFirstname"   ) var directorFirstname   : String? = null,
    @SerializedName("directorLastname"    ) var directorLastname    : String? = null,
    @SerializedName("directorFullname"    ) var directorFullname    : String? = null,
    @SerializedName("directorPhone"       ) var directorPhone       : String? = null,
    @SerializedName("musicDirector"       ) var musicDirector       : String? = null,
    @SerializedName("photographyDirector" ) var photographyDirector : String? = null,
    @SerializedName("Screenwriters"       ) var Screenwriters       : String? = null

)