package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.IO

import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

public interface DAMApiService {
    //Apaño rapido
    companion object {
        val BASE_URL: String
            get() = "https://damapi.herokuapp.com/api/v1/"
    }


    public interface UserService: DAMApiService  {
        fun  hola()
    }

    public interface MovieService : DAMApiService {
        @GET("movies")
        fun getAllMovies():Callback<Movie>
    }


}