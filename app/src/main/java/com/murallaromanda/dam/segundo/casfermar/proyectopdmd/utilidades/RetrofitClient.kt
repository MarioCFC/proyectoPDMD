package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades

import com.google.gson.Gson
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.IO.ApiService
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.searchMovie.Results
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object {
        private lateinit var miInstancia: RetrofitClient

        @Synchronized fun getInstance(): RetrofitClient {
            if (miInstancia == null) {
                miInstancia = RetrofitClient()
            }
            return miInstancia
        }
    }
    //Probablemente esto esté mal
    private var api: ApiService

    private constructor() {

        var retrofit = Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        api = retrofit.create(ApiService::class.java)
        miInstancia.getResultados()
    }

    fun getResultados(): ApiService {
        return api
    }
}