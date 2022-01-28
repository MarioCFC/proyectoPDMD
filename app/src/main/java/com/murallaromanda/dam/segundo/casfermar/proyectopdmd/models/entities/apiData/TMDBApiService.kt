package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.apiData

class TMDBApiService : ApiService() {
    private companion object{
        val URL_BASE : String = ""
        val API_KEY : String = ""

    }
    override fun getApiKey() : String {
        return URL_BASE
    }

    override fun getUrlBase() : String{
        return API_KEY
    }


}