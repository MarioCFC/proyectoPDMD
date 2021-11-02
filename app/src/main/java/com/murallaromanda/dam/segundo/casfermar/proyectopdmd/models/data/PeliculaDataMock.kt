package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.data

import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Pelicula

class PeliculaDataMock : PeliculaData{
    override fun getLista(): List<Pelicula> {
        return listOf(
            Pelicula("El juego de Ender","Ciencia ficcion","Jame Cameron","8","https://res.cloudinary.com/practicaldev/image/fetch/s--sWV8Y0kc--/c_imagga_scale,f_auto,fl_progressive,h_900,q_auto,w_1600/https://dev-to-uploads.s3.amazonaws.com/i/kml9j34p9taplrnqtcez.jpg"),
            Pelicula("El castillo ambulante","Fantasia","Hayao Miyazaki","7","https://pics.filmaffinity.com/hauru_no_ugoku_shiro-376386887-mmed.jpg"),
            Pelicula("Shrek","Comedia","Andrew Adamson","9","https://pics.filmaffinity.com/shrek-903764423-mmed.jpg")
        )
    }

}