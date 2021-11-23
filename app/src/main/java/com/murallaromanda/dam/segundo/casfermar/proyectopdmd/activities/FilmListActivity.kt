package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.recyclerview.widget.GridLayoutManager
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.adapters.ListaPeliculasAdapter
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.ActivityFilmListBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.data.PeliculaDataMock
import android.view.WindowInsets
import android.app.Activity
import android.graphics.Insets
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.JSONPelicula


class FilmListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilmListBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_list)

        binding = ActivityFilmListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val peliculaData = PeliculaDataMock()
        //val lista = peliculaData.getLista()

        var busqueda = JSONPelicula()
        var resultados = busqueda.buscarPelicula("spider man")
        val layoutManager = GridLayoutManager(this,2)


        val adapter = ListaPeliculasAdapter(resultados,this)

        binding.rvListaPeliculas.layoutManager = layoutManager
        binding.rvListaPeliculas.adapter = adapter


        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }


    }

}