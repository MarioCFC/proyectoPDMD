package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities

import android.util.Patterns
import com.google.gson.annotations.SerializedName
import java.util.regex.Pattern


data class User (

    //@SerializedName("id"        ) var id        : String? = null
    @SerializedName("email"     ) var email     : String? = null,
    @SerializedName("password"  ) var password  : String? = null,
    //@SerializedName("firstname" ) var firstname : String? = null,
   // @SerializedName("lastName"  ) var lastName  : String? = null

){

    fun validarEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }
}