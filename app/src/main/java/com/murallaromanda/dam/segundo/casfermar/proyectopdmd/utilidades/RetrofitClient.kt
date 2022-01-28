package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades

import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.IO.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

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

    private constructor(interfazUsada:Class<InterfacePattern>, oApi:ApiService) {

        var retrofit = Retrofit.Builder().baseUrl(oApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
        apiInstance = retrofit.create(::class.java)
    }

    fun getResultados(): ApiService {
        return apiInstance
    }
}