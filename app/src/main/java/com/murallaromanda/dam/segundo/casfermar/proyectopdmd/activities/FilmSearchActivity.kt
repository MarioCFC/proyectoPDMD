package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.adapters.ListaPeliculasAdapter
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.ActivityDatabaseFilmSearcBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.data.PeliculaDataMock
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.Json

class FilmSearchActivity: AppCompatActivity() {

    private lateinit var binding: ActivityDatabaseFilmSearcBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database_film_searc)

        binding = ActivityDatabaseFilmSearcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var busqueda = Json()

        val layoutManager = GridLayoutManager(this,2)

        var resultados = busqueda.buscarPelicula("super man")
        val adapter = ListaPeliculasAdapter(resultados,this)

        binding.layoutDetallesPeliculaCollapse.rvListaPeliculas.layoutManager = layoutManager
        binding.layoutDetallesPeliculaCollapse.rvListaPeliculas.adapter = adapter

    }

}