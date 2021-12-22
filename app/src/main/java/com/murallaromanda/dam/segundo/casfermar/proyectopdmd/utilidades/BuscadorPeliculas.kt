package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades

import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Genres
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.PeliculaJSON
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.PeticionParameters
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.searchMovie.SearchMovies

class BuscadorPeliculas {

    val json = GestorJSON()
    lateinit var peticion:Peticion

    fun hacerBusqueda(cadenaBuscada:String): SearchMovies {
        peticion = Peticion()
        var parametrosPeticion = PeticionParameters(PeticionParameters.BUSCAR_PELICULA,cadenaBuscada)
        var cadenaJsonPeliculasResultado = peticion.enviarPeticion(parametrosPeticion)
        return json.parsearListaPeliculasResultado(cadenaJsonPeliculasResultado)
    }

    fun obtenerDatosPelicula(idPelicula:Int): PeliculaJSON {
        peticion = Peticion()
        var parametrosPeticion = PeticionParameters(PeticionParameters.OBTENER_DATOS_PELICULA,idPelicula.toString())
        var datosPelicula = peticion.enviarPeticion(parametrosPeticion)

        var pelicula = json.parsearPelicula(datosPelicula)

        modificarLinkImagenes(pelicula)
        comprobarGeneros(pelicula)

        return pelicula
    }

    fun datosDePeliculasBuscadas(cadenaBuscada: String): ArrayList<PeliculaJSON> {
        var datosPeliculas = ArrayList<PeliculaJSON>()
        hacerBusqueda(cadenaBuscada).results.forEach{
            datosPeliculas.add(obtenerDatosPelicula(it.id!!))
        }
        return datosPeliculas
    }

    //Apaño para que no de problemas la carga de las imagenes
    fun modificarLinkImagenes(pelicula:PeliculaJSON){
            pelicula.posterPath = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2" +  pelicula.posterPath
            pelicula.backdropPath ="https://www.themoviedb.org/t/p/w600_and_h900_bestv2" +  pelicula.backdropPath
    }

    fun comprobarGeneros(pelicula: PeliculaJSON){
        if(pelicula.genres.size == 0)
            pelicula.genres = List<Genres>(1) { it -> Genres() }
    }
}