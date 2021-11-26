package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades

import ListaPeliculas
import android.app.Activity
import com.google.gson.Gson
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.searchMovie.SearchMovies
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.PeliculaJSON
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Peticion
import java.io.File

//Dan problemas todo lo relacionado con los inputStream
class Json() {
companion object{
    private var nombreFicheroLista:String = "listaPeliculas"
    private  var listaJson:ListaPeliculas = ListaPeliculas()
}

    fun getLista(): ArrayList<PeliculaJSON> {
        return listaJson.listaPeliculas
    }

    fun buscarPelicula(cadenaBuscada:String):ArrayList<PeliculaJSON> {
        var peliculasResultantes:ListaPeliculas = ListaPeliculas()

        val gson = Gson()
        var peticion = HacerPeticiones()
        var jsonResultadosBusqueda:String = peticion.execute(Peticion(0,cadenaBuscada)).get()
        val idPel: SearchMovies = gson.fromJson(jsonResultadosBusqueda, SearchMovies::class.java)

        var contador :Int = 0
        idPel.results.forEach {

            var peticion = HacerPeticiones()
            var jsonResultadosPelicula = peticion.execute(Peticion(1,it.id.toString())).get()
            peliculasResultantes.listaPeliculas.add(obtenerPelicula(jsonResultadosPelicula))
            peticion.cancel(true)

        }

        return peliculasResultantes.listaPeliculas

    }
    //Hay peliculas con null
    fun obtenerPelicula(inp:String):PeliculaJSON{
        val gsonPel = Gson()
        return gsonPel.fromJson(inp, PeliculaJSON::class.java)
    }

    fun guardarFicheroPeliculas(activity:Activity){
        var jsonString:String = Gson().toJson(listaJson)
        var file = File(activity.applicationContext.getFilesDir(), nombreFicheroLista)
        file.writeText(jsonString)
    }

    fun parsearLista(activity: Activity): ArrayList<PeliculaJSON> {
        var cadenaJson:String
        var buffer = File(activity.applicationContext.getFilesDir(), nombreFicheroLista).bufferedReader()
        cadenaJson = buffer.use {it.readText()}
        listaJson = Gson().fromJson(cadenaJson,ListaPeliculas::class.java)
        return listaJson.listaPeliculas
    }


}