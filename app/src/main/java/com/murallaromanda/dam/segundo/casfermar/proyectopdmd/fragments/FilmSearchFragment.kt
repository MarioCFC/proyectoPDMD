package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.IO.ApiService
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.adapters.ListaPeliculasAdapter
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.ActivityDatabaseFilmSearcBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.searchMovie.SearchResults
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class FilmSearchFragment : Fragment() {
    private lateinit var binding: ActivityDatabaseFilmSearcBinding
    private lateinit var activity: AppCompatActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity = getActivity() as AppCompatActivity
        setHasOptionsMenu(true)

        binding = ActivityDatabaseFilmSearcBinding.inflate(inflater, container, false)

        //Eliminando el boton flotante
        binding.layoutDetallesPeliculaCollapse.FilmSearchLayout.removeView(binding.layoutDetallesPeliculaCollapse.fabMenu)

        val layoutManager = GridLayoutManager(activity, 2)

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

        searchView.queryHint = "Búsqueda"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(query: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                // ColocarRecycler(activity, binding.layoutDetallesPeliculaCollapse.rvFilmList).execute(query)


                var call: Call<SearchResults> =
                    RetrofitClient.getInstance().getResultados().getPeliculas(1, query,ApiService.API_KEY);

                call.enqueue(object : Callback<SearchResults> {

                    override fun onResponse(
                        call: Call<SearchResults>,
                        response: Response<SearchResults>
                    ) {

                        val layoutManager = GridLayoutManager(activity, 2)
                        val adapter = ListaPeliculasAdapter(response.body()!!.resultShortDataMovie, activity)

                        binding.layoutDetallesPeliculaCollapse.rvFilmList.layoutManager = layoutManager
                        binding.layoutDetallesPeliculaCollapse.rvFilmList.adapter = adapter
                    }

                    override fun onFailure(call: Call<SearchResults>, t: Throwable) {
                        TODO("Error busqueda de pelicula en FilmSearchFragment.kt")
                    }
                })
                return false
            }


        })


        super.onCreateOptionsMenu(menu, inflater)
    }
}


