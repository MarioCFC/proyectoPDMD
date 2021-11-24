package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities

import android.app.Activity
import android.content.ClipData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.recyclerview.widget.GridLayoutManager
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.adapters.ListaPeliculasAdapter
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.ActivityFilmListBinding
import android.widget.SearchView
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.Json


class FilmListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilmListBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_list)

        binding = ActivityFilmListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var busqueda = Json()
        var resultados = busqueda.buscarPelicula("spider man")
        val layoutManager = GridLayoutManager(this,2)


        val adapter = ListaPeliculasAdapter(resultados,this)

        binding.rvListaPeliculas.layoutManager = layoutManager
        binding.rvListaPeliculas.adapter = adapter

        binding.fabDos.setOnClickListener(){
            val intent = Intent(this,FilmSearchActivity::class.java)
            this.startActivity(intent)

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
    }



/*
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_add_search_film, menu)
        return true
    }



    fun colocarRecycler(cadenaBuscada :String){
        var busqueda = Json()
        var resultados = busqueda.buscarPelicula("superman")
        val adapter = ListaPeliculasAdapter(resultados,this)
        binding.rvListaPeliculas.adapter = adapter
    }
    */
}