package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.IO

import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.PeliculaJSON
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.searchMovie.ShortDataMovieSearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

public interface ApiService {
    //Apaño rapido

    companion object {
        var API_KEY: String
            get() = "1e07834b3cf658fea5163c09e574b152"
            set(value) = TODO()

        var BASE_URL: String
            get() = "https://api.themoviedb.org/3/"
            set(value) = TODO()
    }
    @GET("search/movie")
    fun getPeliculas(@Query("page") pagina:Int,@Query("query") movieName:String,@Query("api_key") key:String): Call<ShortDataMovieSearchResult>

    @GET("movie")
    fun getMovieData(@Path("id") id:Int,@Query("api_key") key:String):Call<PeliculaJSON>
}