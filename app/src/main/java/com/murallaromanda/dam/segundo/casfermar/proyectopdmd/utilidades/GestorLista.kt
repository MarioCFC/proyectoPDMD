package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades

import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.searchMovie.ListaPeliculas
import android.app.Activity
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.PeliculaJSON

class GestorLista(val activity: Activity) {
    companion object {
        private var peliculas: ListaPeliculas? = null
    }

    var json = GestorJSON()

    init {
        if (peliculas == null) {
            peliculas = json.parsearLista(activity)
        }
    }

    fun getPeliculas():ArrayList<PeliculaJSON>{
        return peliculas!!.listaPeliculas
    }

    fun añadirPelicula(pelicula: PeliculaJSON) {
        peliculas!!.listaPeliculas.add(pelicula)
        guardarPelicula()
    }

    fun borrarPelicula(pelicula: PeliculaJSON) {
        peliculas!!.listaPeliculas.remove(pelicula)
        guardarPelicula()
    }

    fun guardarPelicula() {
        json.guardarFicheroPeliculas(activity, peliculas!!)
    }

}