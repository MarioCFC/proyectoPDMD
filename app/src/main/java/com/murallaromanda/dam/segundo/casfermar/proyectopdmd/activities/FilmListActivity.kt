package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.adapters.ListaPeliculasAdapter
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.ActivityFilmListBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.data.PeliculaDataMock
import android.view.View
import android.view.animation.OvershootInterpolator
import com.google.android.material.snackbar.Snackbar
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.Json


class FilmListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilmListBinding
    var menuAbierto:Boolean = false
    var interpolator = OvershootInterpolator()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_list)

        binding = ActivityFilmListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val peliculaData = PeliculaDataMock()
        //val lista = peliculaData.getLista()

        var busqueda = Json()
        var resultados = busqueda.buscarPelicula("spider man")
        val layoutManager = GridLayoutManager(this,2)


        val adapter = ListaPeliculasAdapter(resultados,this)

        binding.rvListaPeliculas.layoutManager = layoutManager
        binding.rvListaPeliculas.adapter = adapter

    }





}