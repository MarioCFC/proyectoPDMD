package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("status") var status: Int? = null,
    @SerializedName("message") var message: String? = null
)
