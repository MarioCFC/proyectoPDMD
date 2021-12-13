package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.searchMovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.adapters.ListaPeliculasAdapter
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.FragmentFilmListBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.fragments.FilmCreateFragment
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.fragments.FilmSearchFragment
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.PeliculaJSON
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.GestorLista

class FilmListFragment : Fragment() {
    lateinit var binding: FragmentFilmListBinding
    lateinit var activity: AppCompatActivity
    private lateinit var resultados: ArrayList<PeliculaJSON>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity = getActivity() as AppCompatActivity
        binding = FragmentFilmListBinding.inflate(inflater, container, false)

        var gestor = GestorLista(activity)
        resultados = gestor.getPeliculas()


        if (resultados != null)
            colocarRecycler(resultados)

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

    fun colocarRecycler(listaPelisculas: ArrayList<PeliculaJSON>) {
        var layoutManager = GridLayoutManager(activity, 2)
        val adapter = ListaPeliculasAdapter(listaPelisculas, activity)
        binding.rvFilmList.layoutManager = layoutManager
        binding.rvFilmList.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        binding.fabMenu.close(false)
        colocarRecycler(resultados)
    }


}