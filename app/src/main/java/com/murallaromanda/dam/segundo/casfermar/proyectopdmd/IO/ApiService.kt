package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.IO

import ListaPeliculas
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Pelicula
import retrofit2.Call
import retrofit2.http.GET

public interface ApiService {

    @GET("peliculas")
    fun getPeliculas(): Call<ListaPeliculas>

    @GET("pelicula")
    fun getPelicula(): Call<Pelicula>
}