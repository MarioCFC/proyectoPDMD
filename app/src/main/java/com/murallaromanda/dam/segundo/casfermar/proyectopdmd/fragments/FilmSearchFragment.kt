package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.adapters.ListaPeliculasAdapter
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.ActivityDatabaseFilmSearcBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.BuscadorPeliculas

class FilmSearchFragment:Fragment() {
    private lateinit var binding: ActivityDatabaseFilmSearcBinding
    private lateinit var activity: AppCompatActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity = getActivity() as AppCompatActivity
        binding = ActivityDatabaseFilmSearcBinding.inflate(inflater,container,false)

        //Eliminando el boton flotante
        binding.layoutDetallesPeliculaCollapse.FilmSearchLayout.removeView(binding.layoutDetallesPeliculaCollapse.fabMenu)

        val layoutManager = GridLayoutManager(activity,2)

        binding.layoutDetallesPeliculaCollapse.rvFilmList.layoutManager = layoutManager

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

        return binding.root
    }

    fun colocarRecycler(cadenaBuscada :String){
        val layoutManager = GridLayoutManager(activity,2)
        //En vez de buscar los datos de la pelicula clicada, son cargados los datos de todas las peliculas
        //ya que la peticion a TMDB es lenta
        var resultados = BuscadorPeliculas().datosDePeliculasBuscadas(cadenaBuscada)
        val adapter = ListaPeliculasAdapter(resultados,activity)

        binding.layoutDetallesPeliculaCollapse.rvFilmList.layoutManager = layoutManager
        binding.layoutDetallesPeliculaCollapse.rvFilmList.adapter = adapter
    }

}