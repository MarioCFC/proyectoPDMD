package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.fragments

import android.app.Activity
import android.os.AsyncTask
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.adapters.ListaPeliculasAdapter
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.ActivityDatabaseFilmSearcBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.PeliculaJSON
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
        setHasOptionsMenu(true)

        binding = ActivityDatabaseFilmSearcBinding.inflate(inflater,container,false)

        //Eliminando el boton flotante
        binding.layoutDetallesPeliculaCollapse.FilmSearchLayout.removeView(binding.layoutDetallesPeliculaCollapse.fabMenu)

        val layoutManager = GridLayoutManager(activity,2)

        binding.layoutDetallesPeliculaCollapse.rvFilmList.layoutManager = layoutManager

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity.menuInflater.inflate(R.menu.menu_search_film, menu)

        var search = menu.findItem(R.id.search_view)
        val searchView = search.actionView as SearchView

        searchView.setIconifiedByDefault(false);
        searchView.clearFocus();
        searchView.setQueryHint("Introduce un titulo")

        searchView.queryHint="Búsqueda"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(query: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                ColocarRecycler(activity, binding.layoutDetallesPeliculaCollapse.rvFilmList).execute(query)
                return false
            }

        })

        super.onCreateOptionsMenu(menu, inflater)


    }

    class ColocarRecycler(var miActividad:Activity, var miRecyclerView:RecyclerView) : AsyncTask<String, Void, ArrayList<PeliculaJSON>>(){
        override fun doInBackground(vararg params: String?): ArrayList<PeliculaJSON> {
            return BuscadorPeliculas().datosDePeliculasBuscadas(params[0]!!)
        }

        override fun onPostExecute(result: ArrayList<PeliculaJSON>?) {
            val layoutManager = GridLayoutManager(miActividad,2)
            val adapter = ListaPeliculasAdapter(result!!,miActividad)

            miRecyclerView.layoutManager = layoutManager
            miRecyclerView.adapter = adapter
        }

    }



}