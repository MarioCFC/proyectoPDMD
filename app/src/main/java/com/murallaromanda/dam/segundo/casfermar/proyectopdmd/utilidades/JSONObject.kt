package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades

import android.os.AsyncTask
import java.net.HttpURLConnection
import java.net.URL
import android.util.Log
import com.google.gson.Gson
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.JSON.searchMovie.Results
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.JSON.searchMovie.SearchMovies
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Pelicula
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.PeliculaJSON
import java.io.*
import java.net.URLConnection

//Dan problemas todo lo relacionado con los inputStream
class JSONsObject() : AsyncTask<Void, Void, Void>() {
//Falta cerrar los strams
     lateinit var peliculaBuscada:String
     lateinit var cadenaBuscada:String
     var tipoConsulta:Int = 0
     lateinit var cadenaInputStream:String

    private fun hacerPeticion() {
        var cadenaInputStream:String = ""
        //Esto es un switch
        when(tipoConsulta){
            0 -> cadenaInputStream = "https://api.themoviedb.org/3/search/movie?api_key=1e07834b3cf658fea5163c09e574b152&page=1&query=" + cadenaBuscada
            1 -> cadenaInputStream = "https://api.themoviedb.org/3/movie/"+ cadenaBuscada +"?api_key=1e07834b3cf658fea5163c09e574b152&language=es-ES"
        }


        var url: URL
        var conexion: HttpURLConnection? = null

        var conexion2 :URLConnection = URL(cadenaInputStream).openConnection()
        conexion2.setRequestProperty("Accept-Charset","UTF-8");

        var cadenaResultante:String = ""
        var buffer = BufferedInputStream(conexion2.inputStream)
        var num: Int = buffer.read()
        while (num != -1) {
            cadenaResultante += num.toChar()
            num = buffer.read()
        }

    }


    @Throws(IOException::class)
    fun buscarPelicula(cadenaBuscada:String): ArrayList<Pelicula> {
        var peliculasResultantes:ArrayList<Pelicula> = ArrayList()

        val gson = Gson()
        this.tipoConsulta = 0
        this.cadenaBuscada = cadenaBuscada
        execute()
        val idPel: SearchMovies = gson.fromJson(cadenaInputStream, SearchMovies::class.java)

        this.tipoConsulta = 1
        idPel.results.forEach {
            this.cadenaBuscada = it.id.toString()
            execute()
            peliculasResultantes.add(obtenerPelicula(cadenaInputStream))
        }

        return peliculasResultantes

    }

    fun obtenerPelicula(inp:String):Pelicula{
        val gsonPel = Gson()
        val jsonPelicula:PeliculaJSON = gsonPel.fromJson(inp, PeliculaJSON::class.java)
        return Pelicula(jsonPelicula.title.toString(),jsonPelicula.genres[0].toString(), "directorCambiar",jsonPelicula.voteAverage.toString(),
        jsonPelicula.overview.toString(),jsonPelicula.posterPath.toString(),jsonPelicula.backdropPath.toString())
    }

    override fun doInBackground(vararg p0: Void?): Void? {
        hacerPeticion()
        return null
    }
}