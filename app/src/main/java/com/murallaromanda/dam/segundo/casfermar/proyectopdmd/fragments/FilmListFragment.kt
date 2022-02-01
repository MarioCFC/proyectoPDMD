package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.fragments

import android.app.appsearch.SearchResults
import android.media.session.MediaSession
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.adapters.ListaPeliculasAdapter
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.FragmentFilmListBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.ErrorResponse
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.LoginToken
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Movie
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.User
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

    //REFACTORIZAR
    fun rellenarRecyclerView(recyclerView: RecyclerView) {
        var call = RetrofitService().getMovieService().getAllMovies()

        call.enqueue(object : Callback<List<Movie>> {
            override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                if (response.code() > 200 && response.code() < 300) {
                    val adapter =
                        ListaPeliculasAdapter(response.body()!!, activity)
                    recyclerView.adapter = adapter

                } else {
                    var error: ErrorResponse =
                        Gson().fromJson(response.errorBody()!!.string(), ErrorResponse::class.java)
                    throw Exception(error.message!!)
                }
            }

            override fun onFailure(call: Call<List<Movie>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

}
