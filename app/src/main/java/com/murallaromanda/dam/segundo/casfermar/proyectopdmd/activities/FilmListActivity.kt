package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.adapters.ListaPeliculasAdapter
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.ActivityFilmListBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Pelicula
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

        var busqueda = GestorJSON()
        //busqueda.guardarFicheroPeliculas(this)
        var gestor = GestorLista(this)
        resultados = gestor.getPeliculas()


        if (resultados != null)
            colocarRecycler(resultados)

        binding.fabUno.setOnClickListener(){
            var respuesta:Int = 0
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
            binding.rvListaPeliculas.layoutManager = layoutManager
            binding.rvListaPeliculas.adapter = adapter
        }

/*

        var sv = findViewById<Item>(R.id.app_bar_search)
        sv
        //Error no esta bien el eventp
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                colocarRecycler(query,this@FilmListActivity,binding)
                return false
            }

        })
        */




/*
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_add_search_film, menu)
        return true
    }




    */

    override fun onResume() {
        super.onResume()
        binding.fabMenu.close(false)
        colocarRecycler(resultados)
    }
}