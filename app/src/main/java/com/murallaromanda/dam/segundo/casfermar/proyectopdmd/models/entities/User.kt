package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities

import com.google.gson.annotations.SerializedName


data class User (

    @SerializedName("id"        ) var id        : String? = null,
    @SerializedName("email"     ) var email     : String? = null,
    @SerializedName("password"  ) var password  : String? = null,
    @SerializedName("firstname" ) var firstname : String? = null,
    @SerializedName("lastName"  ) var lastName  : String? = null

)