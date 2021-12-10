package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities.FilmCreateActivity
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities.FilmSearchActivity
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
        binding = FragmentFilmListBinding.inflate(inflater,container,false)

        var gestor = GestorLista(activity)
        resultados = gestor.getPeliculas()


        if (resultados != null)
            colocarRecycler(resultados)

        binding.fabUno.setOnClickListener() {
            val intent = Intent(activity, FilmCreateActivity::class.java)
            intent.putExtra("pelicula", PeliculaJSON())
            startActivity(intent);
        }

        binding.fabDos.setOnClickListener() {
            val intent = Intent(activity, FilmSearchActivity::class.java)
            this.startActivity(intent)
            colocarRecycler(resultados)
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