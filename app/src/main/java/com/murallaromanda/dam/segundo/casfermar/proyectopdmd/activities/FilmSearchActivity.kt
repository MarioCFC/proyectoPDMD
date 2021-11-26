package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import com.github.clans.fab.FloatingActionButton
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
        supportActionBar?.hide()

        binding = ActivityDatabaseFilmSearcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Eliminando el boton flotante
        binding.layoutDetallesPeliculaCollapse.FilmSearchLayout.removeView(binding.layoutDetallesPeliculaCollapse.fabMenu)


        val layoutManager = GridLayoutManager(this,2)


        binding.layoutDetallesPeliculaCollapse.rvListaPeliculas.layoutManager = layoutManager
        var searchView = binding.searchView

        searchView.setIconified(false);
        searchView.clearFocus();
        searchView.setQueryHint("Introduce un titulo")


        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                colocarRecycler(query)
                return false
            }

        })
    }

    fun colocarRecycler(cadenaBuscada :String){
        var busqueda = Json()
        val layoutManager = GridLayoutManager(this,2)

        var resultados = busqueda.buscarPelicula(cadenaBuscada)
        val adapter = ListaPeliculasAdapter(resultados,this)

        binding.layoutDetallesPeliculaCollapse.rvListaPeliculas.layoutManager = layoutManager
        binding.layoutDetallesPeliculaCollapse.rvListaPeliculas.adapter = adapter
    }



}
