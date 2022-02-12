package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades

import android.content.Context

class GestorSharedPreferences(val context: Context){
    val nombreArchivo = "baseDatos"
    val preferencias = context.getSharedPreferences(nombreArchivo,0)

    fun setPersonalToken( personalToken:String){
        preferencias.edit().putString("personalToken", personalToken).commit()
    }

    fun getPersonalToken():String?{
        return preferencias.getString("personalToken","erro")
    }

    fun resetPersonalToken():Unit{
        setPersonalToken("")
    }
}