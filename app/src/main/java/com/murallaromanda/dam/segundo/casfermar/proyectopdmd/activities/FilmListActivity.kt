package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities

import ListaPeliculas
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
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Pelicula
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.PeliculaJSON
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.Json
import java.io.File


class FilmListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilmListBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_list)
        supportActionBar?.hide()

        binding = ActivityFilmListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var busqueda = Json()
        var resultados = busqueda.parsearLista(this)
        if (resultados != null)
            colocarRecycler(resultados)

        binding.fabUno.setOnClickListener(){
            var respuesta:Int = 0
            val  intent = Intent(this,FilmCreateActivity::class.java)
            intent.putExtra("pelicula", Pelicula("","","","","","",""))
            startActivityForResult(intent, respuesta);
            if(respuesta == 20){
                colocarRecycler(resultados)
            }
        }

        binding.fabDos.setOnClickListener(){
            val intent = Intent(this,FilmSearchActivity::class.java)
            this.startActivity(intent)
            colocarRecycler(resultados)

        }
    }
        fun colocarRecycler(resultados:ArrayList<PeliculaJSON>){
            var layoutManager = GridLayoutManager(this,2)
            val adapter = ListaPeliculasAdapter(resultados,this)
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
}