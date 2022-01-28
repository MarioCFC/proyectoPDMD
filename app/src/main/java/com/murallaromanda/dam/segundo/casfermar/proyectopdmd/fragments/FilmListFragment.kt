package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.IO.ApiService
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.adapters.ListaPeliculasAdapter
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.FragmentFilmListBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.PeliculaJSON
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.searchMovie.SearchResults
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.GestorLista
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilmListFragment : Fragment() {
    private lateinit var binding: FragmentFilmListBinding
    private lateinit var resultados: ArrayList<PeliculaJSON>
    private lateinit var activity: AppCompatActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity = getActivity() as AppCompatActivity
        setHasOptionsMenu(true)
        activity.supportActionBar?.setTitle("Peliculas mas populares de la semana")

        binding = FragmentFilmListBinding.inflate(inflater, container, false)


        /*Cargamos las peliculas mas populares en el RecyclerView*/
        rellenarRecyclerView()

        binding.fabUno.setOnClickListener() {
            val ft = activity?.supportFragmentManager?.beginTransaction()
            ft?.replace(R.id.contenedor_fragments, FilmCreateFragment())
            ft?.addToBackStack(null)
            ft?.commit()
        }

        binding.fabDos.setOnClickListener() {
            val ft = activity?.supportFragmentManager?.beginTransaction()
            ft?.replace(R.id.contenedor_fragments, FilmSearchFragment())
            ft?.addToBackStack(null)
            ft?.commit()
        }

        return binding.root
    }


    override fun onResume() {
        super.onResume()
        binding.fabMenu.close(false)
        rellenarRecyclerView()
    }

    fun rellenarRecyclerView(){
        var call: Call<SearchResults> =
            RetrofitClient.getInstance().getResultados().getTrendingMovie(1, ApiService.API_KEY);

        call.enqueue(object : Callback<SearchResults> {

            override fun onResponse(
                call: Call<SearchResults>,
                response: Response<SearchResults>
            ) {
                val layoutManager = GridLayoutManager(activity, 2)
                val adapter =
                    ListaPeliculasAdapter(response.body()!!.resultShortDataMovie, activity)

                binding.rvFilmList.layoutManager = layoutManager
                binding.rvFilmList.adapter = adapter
            }

            override fun onFailure(call: Call<SearchResults>, t: Throwable) {
                TODO("Error busqueda de pelicula en FilmSearchFragment.kt")
            }
        })

    }


}