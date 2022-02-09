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

        val BASE_PERSONAL_TOKEN: String
            get() = "Bearer "
    }


    public interface UserService: DAMApiService  {
        @POST("users/login")
        fun login(@Body user:User):Call<LoginToken>

        @POST("users/signup")
        fun singUp(@Body user:User):Call<Any>

    }

    public interface MovieService : DAMApiService {

        @GET("movies")
        fun getAllMovies(@Header("Authorization:") token:String ):Call<List<Movie>>

        @GET("movies/{id}")
        fun getMovieByID(@Header("Authorization:") token:String , @Path("id") id:String):Call<Movie>

        @DELETE("movies/{id}")
        fun deleteMovieByID(@Header("Authorization:") token:String , @Path("id") id:String):Call<Unit>

        @POST("movies")
        fun createMovie(@Header("Authorization") token: String,@Body pelicula: Movie): Call<Unit>
    }


}