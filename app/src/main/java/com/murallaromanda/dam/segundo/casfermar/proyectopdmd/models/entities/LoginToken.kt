package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities

import com.google.gson.annotations.SerializedName

data class LoginToken(
    @SerializedName("token" ) var token : String? = null
)
