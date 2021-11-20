package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades

import android.content.Context
import android.content.SharedPreferences

class GestorSharedPreferences(val context: Context){
    val nombreArchivo = "baseDatos"
    /*Mirar el segundo parametro*/
    val preferencias = context.getSharedPreferences(nombreArchivo,0)

    fun setPreferencias(email:String, contraseña:String){
        preferencias.edit().putString("Email", email).commit()
        preferencias.edit().putString("Password", contraseña).commit()
    }


    fun getPreferencias(datoRecuperar:String):String?{
            if (datoRecuperar.equals("Email")){
                return preferencias.getString("Email","")
            }else if (datoRecuperar.equals("Password")){
                return preferencias.getString("Password", "")
            }
        return null
    }
}