package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.IO

import android.media.session.MediaSession
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.LoginToken
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Movie
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*

public interface DAMApiService {
    //Apaño rapido
    companion object {
        val BASE_URL: String
            get() = "https://damapi.herokuapp.com/api/v1/"
    }


    public interface UserService: DAMApiService  {
        @POST("users/login")
        fun login(@Body user:User):Call<LoginToken>

    }

    public interface MovieService : DAMApiService {
        @GET("movies")
        fun getAllMovies():Call<List<Movie>>
    }


}