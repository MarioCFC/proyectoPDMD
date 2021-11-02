package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.data

import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Pelicula

class PeliculaDataMock : PeliculaData{
    override fun getLista(): List<Pelicula> {
        return listOf(
            Pelicula("Avatar","Ciencia ficcion","Jame Cameron","8","https://pics.filmaffinity.com/avatar-208925608-mmed.jpg"),
            Pelicula("El castillo ambulante","Fantasia","Hayao Miyazaki","7","https://pics.filmaffinity.com/hauru_no_ugoku_shiro-376386887-mmed.jpg"),
            Pelicula("Shrek","Comedia","Andrew Adamson","9","https://pics.filmaffinity.com/shrek-903764423-mmed.jpg")
        )
    }

}