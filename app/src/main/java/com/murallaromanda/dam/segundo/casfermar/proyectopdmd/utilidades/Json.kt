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
    private var nombreFicheroLista: String = "listaPeliculas"
}

    //Este metodo deberia de estar en otra clase
    fun buscarPelicula(cadenaBuscada:String):ArrayList<PeliculaJSON> {
        var peliculasResultantes = ListaPeliculas()

        val gson = Gson()
        var peticion = HacerPeticiones()
        var jsonResultadosBusqueda:String = peticion.execute(Peticion(0,cadenaBuscada)).get()
        val idPel: SearchMovies = gson.fromJson(jsonResultadosBusqueda, SearchMovies::class.java)

        idPel.results.forEach {

            var peticion = HacerPeticiones()
            var jsonResultadosPelicula = peticion.execute(Peticion(1,it.id.toString())).get()
            peliculasResultantes.listaPeliculas.add(parsearPelicula(jsonResultadosPelicula))
            peticion.cancel(true)

        }

        return peliculasResultantes.listaPeliculas

    }
    //Hay peliculas con null
    fun parsearPelicula(inp:String):PeliculaJSON{
        val gsonPel = Gson()
        return gsonPel.fromJson(inp, PeliculaJSON::class.java)
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
        return Gson().fromJson(cadenaJson,ListaPeliculas::class.java)
    }

    fun guardarFicheroPeliculas(activity:Activity,listaJson:ListaPeliculas){
        var jsonString:String = Gson().toJson(listaJson)
        var file = File(activity.applicationContext.getFilesDir(), nombreFicheroLista)
        file.writeText(jsonString)
    }

    fun crearFicheroPeliculas(activity: Activity,listaPeliculas: ListaPeliculas){
        guardarFicheroPeliculas(activity,listaPeliculas)
    }






}