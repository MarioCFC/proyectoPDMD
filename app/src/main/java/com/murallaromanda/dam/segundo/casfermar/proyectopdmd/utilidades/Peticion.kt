package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades

import android.os.AsyncTask
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Pelicula
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.PeticionParameters
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection

class Peticion : AsyncTask<PeticionParameters, Void, String>(){

    //La codificacon final parece ser Latin1
    override fun doInBackground(vararg params: PeticionParameters?): String {
            //Esto es un switch

            var url: URL
            var conexion: HttpURLConnection? = null


            var conexion2 : URLConnection = URL(params[0]?.peticion).openConnection()
            conexion2.setRequestProperty("Accept-Charset","UTF-8");

            var cadenaResultante:String = ""
            var buffer = BufferedInputStream(conexion2.inputStream)
            var num: Int = buffer.read()
            while (num != -1) {
                cadenaResultante += num.toChar()
                num = buffer.read()
            }

            buffer.close()
            return cadenaResultante
    }
}