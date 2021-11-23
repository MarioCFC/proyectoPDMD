package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities

import java.lang.Exception

class Peticion(tipoPeticion: Int, cadenaBuscada:String) {
    val peticion :String
    init {
        when(tipoPeticion){
            0 -> this.peticion = "https://api.themoviedb.org/3/search/movie?api_key=1e07834b3cf658fea5163c09e574b152&page=1&query=" + cadenaBuscada
            1 -> this.peticion = "https://api.themoviedb.org/3/movie/"+ cadenaBuscada +"?api_key=1e07834b3cf658fea5163c09e574b152&language=es-ES"
            else -> throw Exception("Tipo de peticion no valida")
        }
    }

}
