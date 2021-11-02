package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.data

import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Pelicula

interface PeliculaData {
    fun getLista() : List<Pelicula>
}