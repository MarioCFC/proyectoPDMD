package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.data

import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Pelicula

class PeliculaDataMock : PeliculaData{
    override fun getLista(): List<Pelicula> {
        return listOf(
            Pelicula("El juego de Ender","Ciencia ficcion","Jame Cameron","4.5","https://es.web.img3.acsta.net/c_310_420/pictures/210/326/21032626_20130828115205976.jpg"),
            Pelicula("El castillo ambulante","Fantasia","Hayao Miyazaki","5","https://es.web.img3.acsta.net/c_310_420/medias/nmedia/18/80/29/24/20062836.jpg"),
            Pelicula("Shrek","Comedia","Andrew Adamson","9","https://es.web.img3.acsta.net/c_310_420/pictures/14/03/06/10/13/369709.jpg"),
            Pelicula("Vengadores: Endgame","Accón","Hermanos Russo","9","https://es.web.img3.acsta.net/c_310_420/pictures/19/03/26/17/22/0896830.jpg")
        )
    }

}