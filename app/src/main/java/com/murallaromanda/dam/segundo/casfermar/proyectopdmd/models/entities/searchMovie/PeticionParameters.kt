package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.searchMovie

import java.lang.Exception

class PeticionParameters(tipoPeticion: Int, cadenaBuscada:String) {
    companion object{
        public var BUSCAR_PELICULA = 0
        public var OBTENER_DATOS_PELICULA = 1
    }

    val peticion :String
    init {
        when(tipoPeticion){
            BUSCAR_PELICULA -> this.peticion = "https://api.themoviedb.org/3/search/movie?api_key=1e07834b3cf658fea5163c09e574b152&page=1&query=" + cadenaBuscada
            OBTENER_DATOS_PELICULA -> this.peticion = "https://api.themoviedb.org/3/movie/"+ cadenaBuscada +"?api_key=1e07834b3cf658fea5163c09e574b152&language=es-ES"
            else -> throw Exception("Tipo de peticion no valida")
        }
    }

}
