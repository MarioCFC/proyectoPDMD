package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades

import com.google.gson.Gson
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.searchMovie.SearchMovies
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Pelicula
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.PeliculaJSON
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Peticion

//Dan problemas todo lo relacionado con los inputStream
class Json() {

    fun buscarPelicula(cadenaBuscada:String):ArrayList<PeliculaJSON> {
        var peliculasResultantes:ArrayList<PeliculaJSON> = ArrayList()

        val gson = Gson()
        var peticion = HacerPeticiones()
        var jsonResultadosBusqueda:String = peticion.execute(Peticion(0,cadenaBuscada)).get()
        val idPel: SearchMovies = gson.fromJson(jsonResultadosBusqueda, SearchMovies::class.java)

        var contador :Int = 0
        idPel.results.forEach {

            var peticion = HacerPeticiones()
            var jsonResultadosPelicula = peticion.execute(Peticion(1,it.id.toString())).get()

            peliculasResultantes.add(obtenerPelicula(jsonResultadosPelicula))
            peticion.cancel(true)

            if (contador++ == 3){
                return peliculasResultantes
            }
        }

        return peliculasResultantes

    }
//Hay peliculas con null
    fun obtenerPelicula(inp:String):PeliculaJSON{
        val gsonPel = Gson()
        return gsonPel.fromJson(inp, PeliculaJSON::class.java)
    }


}