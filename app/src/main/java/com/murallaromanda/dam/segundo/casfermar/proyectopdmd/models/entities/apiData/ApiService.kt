package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.apiData

abstract class ApiService {
    abstract fun getApiKey() : String
    abstract fun getUrlBase() : String

}