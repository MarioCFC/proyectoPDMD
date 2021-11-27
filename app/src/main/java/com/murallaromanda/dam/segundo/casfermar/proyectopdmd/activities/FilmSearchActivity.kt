package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.adapters.ListaPeliculasAdapter
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.ActivityDatabaseFilmSearcBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.BuscadorPeliculas
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.GestorLista

class FilmSearchActivity: AppCompatActivity() {

    private lateinit var binding: ActivityDatabaseFilmSearcBinding
    private val m_Text = ""

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
        val layoutManager = GridLayoutManager(this,2)
        //EN vez de buscar los datos de la pelicula clicada, son cargados los datos de todas las peliculas
        //ya que la peticion a TMDB es lenta
        var resultados = BuscadorPeliculas().datosDePeliculasBuscadas(cadenaBuscada)
        val adapter = ListaPeliculasAdapter(resultados,this)

        binding.layoutDetallesPeliculaCollapse.rvListaPeliculas.layoutManager = layoutManager
        binding.layoutDetallesPeliculaCollapse.rvListaPeliculas.adapter = adapter
    }



}
