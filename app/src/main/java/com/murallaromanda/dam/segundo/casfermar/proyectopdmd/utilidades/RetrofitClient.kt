package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades

import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.IO.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object {
        private var miInstancia: RetrofitClient? = null

        @Synchronized
        fun getInstance(): RetrofitClient {
            if (miInstancia == null) {
                miInstancia = RetrofitClient()
            }
            return miInstancia!!
        }
    }

    private var apiInstance: ApiService

    private constructor() {

        var retrofit = Retrofit.Builder().baseUrl(ApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
        apiInstance = retrofit.create(ApiService::class.java)
    }

    fun getResultados(): ApiService {
        return apiInstance
    }
}