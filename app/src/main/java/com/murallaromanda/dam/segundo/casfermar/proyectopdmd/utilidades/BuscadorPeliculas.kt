package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades

import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.PeliculaJSON
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.PeticionParameters
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.searchMovie.SearchMovies

class BuscadorPeliculas {

    val json = GestorJSON()
    lateinit var peticion:Peticion

    fun hacerBusqueda(cadenaBuscada:String): SearchMovies {
        peticion = Peticion()
        var parametrosPeticion = PeticionParameters(PeticionParameters.BUSCAR_PELICULA,cadenaBuscada)
        var cadenaJsonPeliculasResultado = peticion.execute(parametrosPeticion).get()
        peticion.cancel(true)
        return json.parsearListaPeliculasResultado(cadenaJsonPeliculasResultado)
    }

    fun obtenerDatosPelicula(idPelicula:Int): PeliculaJSON {
        peticion = Peticion()
        var parametrosPeticion = PeticionParameters(PeticionParameters.OBTENER_DATOS_PELICULA,idPelicula.toString())
        var datosPelicula = peticion.execute(parametrosPeticion).get()
        peticion.cancel(true)
        return json.parsearPelicula(datosPelicula)
    }

    fun datosDePeliculasBuscadas(cadenaBuscada: String): ArrayList<PeliculaJSON> {
        var datosPeliculas = ArrayList<PeliculaJSON>()
        hacerBusqueda(cadenaBuscada).results.forEach{
            datosPeliculas.add(obtenerDatosPelicula(it.id!!))
        }
        return datosPeliculas
    }
}