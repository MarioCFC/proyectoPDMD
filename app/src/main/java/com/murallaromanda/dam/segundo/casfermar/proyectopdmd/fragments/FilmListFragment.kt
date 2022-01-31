package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.fragments

import android.app.appsearch.SearchResults
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.adapters.ListaPeliculasAdapter
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.FragmentFilmListBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class FilmListFragment : Fragment() {
    private lateinit var binding: FragmentFilmListBinding
    private lateinit var activity: AppCompatActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity = getActivity() as AppCompatActivity
        //Cambios en la ToolBar
        setHasOptionsMenu(true)
        activity.supportActionBar?.setTitle("Peliculas mas populares de la semana")

        //Enlazamos y ajustamos la disposicion y el numero de columnas del recycler
        binding = FragmentFilmListBinding.inflate(inflater, container, false)

        val layoutManager = GridLayoutManager(activity, 2)
        binding.rvFilmList.layoutManager = layoutManager

        /*Cargamos las peliculas mas populares en el RecyclerView*/
        rellenarRecyclerView(binding.rvFilmList)

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
        rellenarRecyclerView(binding.rvFilmList)
    }


    /**@param RecyclerView que será llenado
     * @return LLamado para llenar el recyclerView con las peliculas populares de la semana
     */
    fun rellenarRecyclerView(recyclerView: RecyclerView) {
        var call: Call<SearchResults> =
            RetrofitService().getMovieService().
        RetrofitService().getUserService().
        call.enqueue(object : Callback<SearchResults> {

            override fun onResponse(
                call: Call<SearchResults>,
                response: Response<SearchResults>
            ) {
                val adapter =
                    ListaPeliculasAdapter(response.body()!!.resultShortDataMovie, activity)


                recyclerView.adapter = adapter
            }

            override fun onFailure(call: Call<SearchResults>, t: Throwable) {
                throw Exception("Error al realizar petticion")
            }
        })

    }

}