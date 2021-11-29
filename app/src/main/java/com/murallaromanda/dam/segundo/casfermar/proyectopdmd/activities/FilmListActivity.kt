package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.adapters.ListaPeliculasAdapter
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.ActivityFilmListBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.PeliculaJSON
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.GestorLista
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.GestorJSON


class FilmListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilmListBinding
    private lateinit var resultados:ArrayList<PeliculaJSON>
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_list)
        supportActionBar?.hide()

        binding = ActivityFilmListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var gestor = GestorLista(this)
        resultados = gestor.getPeliculas()


        if (resultados != null)
            colocarRecycler(resultados)

        binding.fabUno.setOnClickListener(){
            val  intent = Intent(this,FilmCreateActivity::class.java)
            intent.putExtra("pelicula", PeliculaJSON())
            startActivity(intent);
        }

        binding.fabDos.setOnClickListener(){
            val intent = Intent(this,FilmSearchActivity::class.java)
            this.startActivity(intent)
            colocarRecycler(resultados)
        }
    }
        fun colocarRecycler(listaPelisculas:ArrayList<PeliculaJSON>){
            var layoutManager = GridLayoutManager(this,2)
            val adapter = ListaPeliculasAdapter(listaPelisculas,this)
            binding.rvFilmList.layoutManager = layoutManager
            binding.rvFilmList.adapter = adapter
        }

    override fun onResume() {
        super.onResume()
        binding.fabMenu.close(false)
        colocarRecycler(resultados)
    }
}