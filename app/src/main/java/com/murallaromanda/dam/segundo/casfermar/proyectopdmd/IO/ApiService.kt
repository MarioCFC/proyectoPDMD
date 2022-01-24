package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.IO

import ListaPeliculas
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Pelicula
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.searchMovie.Results
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

public interface ApiService {
    //Apaño rapido
    var API_KEY:String
        get()="1e07834b3cf658fea5163c09e574b152"
        set(value) = TODO()

    var BASE_URL: String
        get() = "https://api.themoviedb.org/3/"
        set(value) = TODO()

    @GET("/search/movie")
    fun getPeliculas(@Query("page") pagina:Int,@Query("query") movieName:String): Call<Results>


}