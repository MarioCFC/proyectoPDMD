package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.adapters.ListaPeliculasAdapter
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.FragmentFilmListBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.PeliculaJSON
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.GestorLista

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
        activity.supportActionBar?.setTitle("Mis peliculas")

        binding = FragmentFilmListBinding.inflate(inflater,container,false)

        var gestor = GestorLista(activity)
        resultados = gestor.getPeliculas()


        if (resultados != null)
            colocarRecycler(resultados)

        binding.fabUno.setOnClickListener() {
            val ft = activity?.supportFragmentManager?.beginTransaction()
            ft?.replace(R.id.contenedor_fragments,FilmCreateFragment())
            ft?.addToBackStack(null)
            ft?.commit()
        }

        binding.fabDos.setOnClickListener() {
            val ft = activity?.supportFragmentManager?.beginTransaction()
            ft?.replace(R.id.contenedor_fragments,FilmSearchFragment())
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