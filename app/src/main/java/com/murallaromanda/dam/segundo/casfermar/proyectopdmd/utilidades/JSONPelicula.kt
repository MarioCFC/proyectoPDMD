package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades

import android.os.AsyncTask
import java.net.HttpURLConnection
import java.net.URL
import android.util.Log
import androidx.loader.content.AsyncTaskLoader
import com.google.gson.Gson
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.JSON.searchMovie.Results
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.JSON.searchMovie.SearchMovies
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Pelicula
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.PeliculaJSON
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Peticion
import java.io.*
import java.lang.Exception
import java.net.URLConnection
import kotlin.concurrent.thread

//Dan problemas todo lo relacionado con los inputStream
class JSONPelicula() {

    fun buscarPelicula(cadenaBuscada:String):ArrayList<Pelicula> {
        var peliculasResultantes:ArrayList<Pelicula> = ArrayList()

        val gson = Gson()
        var peticion = HacerPeticiones()
        var jsonResultadosBusqueda:String = peticion.execute(Peticion(0,cadenaBuscada)).get()
        val idPel: SearchMovies = gson.fromJson(jsonResultadosBusqueda, SearchMovies::class.java)

        idPel.results.forEach {
            var peticion = HacerPeticiones()
            var jsonResultadosPelicula = peticion.execute(Peticion(1,it.id.toString())).get()

            peliculasResultantes.add(obtenerPelicula(jsonResultadosPelicula))
            peticion.cancel(true)
        }

        return peliculasResultantes

    }
//Hay peliculas con null
    fun obtenerPelicula(inp:String):Pelicula{
        val gsonPel = Gson()
        val jsonPelicula:PeliculaJSON = gsonPel.fromJson(inp, PeliculaJSON::class.java)
        return Pelicula(jsonPelicula.title.toString(),jsonPelicula.genres[0].toString(), "directorCambiar",jsonPelicula.voteAverage.toString(),
        jsonPelicula.overview.toString(),jsonPelicula.posterPath.toString(),jsonPelicula.backdropPath.toString())
    }


}