package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.searchMovie

import ListaPeliculas
import android.app.Activity
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.PeliculaJSON
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.Json

class GestorLista(val activity: Activity) {
    companion object {
        private lateinit var peliculas: ListaPeliculas
    }

    var gestor = Json()

    init {
        if (peliculas == null) {
            peliculas = gestor.parsearLista(activity)
        }
    }

    fun getPeliculas():ArrayList<PeliculaJSON>{
        return peliculas.listaPeliculas
    }

    fun añadirPelicula(pelicula: PeliculaJSON) {
        peliculas.listaPeliculas.add(pelicula)
        guardarPelicula()
    }

    fun borrarPelicula(pelicula: PeliculaJSON) {
        peliculas.listaPeliculas.remove(pelicula)
        guardarPelicula()
    }

    fun guardarPelicula() {
        gestor.guardarFicheroPeliculas(activity, peliculas)
    }


}