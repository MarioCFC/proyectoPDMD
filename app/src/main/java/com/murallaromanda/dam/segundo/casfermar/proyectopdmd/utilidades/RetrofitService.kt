package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades

import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.IO.DAMApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {

    companion object {
        private var miInstancia: Retrofit? = null
        private var userServiceInstace: DAMApiService.UserService? = null
        private var movieServiceInstace: DAMApiService.MovieService? = null

    }

    constructor() {
        if (miInstancia == null) {
            miInstancia = Retrofit.Builder().baseUrl(DAMApiService.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        }

    }

    fun getMovieService(): DAMApiService.MovieService {
        if (movieServiceInstace == null){
            movieServiceInstace = miInstancia!!.create(DAMApiService.MovieService::class.java)
        }

        return movieServiceInstace!!
    }

    fun getUserService() : DAMApiService.UserService{
        if (userServiceInstace == null){
            userServiceInstace = miInstancia!!.create(DAMApiService.UserService::class.java)
        }
        return userServiceInstace!!

    }

}