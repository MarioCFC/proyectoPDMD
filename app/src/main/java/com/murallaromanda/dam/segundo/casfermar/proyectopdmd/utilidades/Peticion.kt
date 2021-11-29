package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades

import android.os.AsyncTask
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.PeticionParameters
import java.io.BufferedInputStream
import java.net.URL
import java.net.URLConnection

class Peticion : AsyncTask<PeticionParameters, Void, String>(){

    override fun doInBackground(vararg params: PeticionParameters?): String {

            var conexion : URLConnection = URL(params[0]?.peticion).openConnection()
            conexion.setRequestProperty("Accept-Charset","UTF-8");

            var cadenaResultante:String = ""
            var buffer = BufferedInputStream(conexion.inputStream)
            var num: Int = buffer.read()
            while (num != -1) {
                cadenaResultante += num.toChar()
                num = buffer.read()
            }

            buffer.close()
            return cadenaResultante
    }
}