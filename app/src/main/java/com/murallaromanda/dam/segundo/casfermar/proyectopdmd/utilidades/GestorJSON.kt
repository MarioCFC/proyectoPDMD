package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades

import ListaPeliculas
import android.app.Activity
import com.google.gson.Gson
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.searchMovie.SearchMovies
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.PeliculaJSON
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.PeticionParameters
import java.io.File

//Dan problemas todo lo relacionado con los inputStream
class GestorJSON() {
companion object{
    private var nombreFicheroLista: String = "listaPeliculas"
}

    private val gson = Gson()

    fun parsearListaPeliculasResultado(resultadosBusqueda:String): SearchMovies {
        return gson.fromJson(resultadosBusqueda, SearchMovies::class.java)
    }


    fun parsearPelicula(inp:String):PeliculaJSON{
        return gson.fromJson(inp, PeliculaJSON::class.java)
    }

    fun parsearLista(activity: Activity): ListaPeliculas {
        var cadenaJson:String
        var file = File(activity.applicationContext.getFilesDir(), nombreFicheroLista)
        //Si no existe el archivo lo creamos
        if (!file.exists()){
            crearFicheroPeliculas(activity,ListaPeliculas())
        }

        var buffer = file.bufferedReader()
        cadenaJson = buffer.use {it.readText()}
        return gson.fromJson(cadenaJson,ListaPeliculas::class.java)
    }

    fun guardarFicheroPeliculas(activity:Activity,listaJson:ListaPeliculas){
        var jsonString:String = gson.toJson(listaJson)
        var file = File(activity.applicationContext.getFilesDir(), nombreFicheroLista)
        file.writeText(jsonString)
    }

    fun crearFicheroPeliculas(activity: Activity,listaPeliculas: ListaPeliculas){
        guardarFicheroPeliculas(activity,listaPeliculas)
    }






}