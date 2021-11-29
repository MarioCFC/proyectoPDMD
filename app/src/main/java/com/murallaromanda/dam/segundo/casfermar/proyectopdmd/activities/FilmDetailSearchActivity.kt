package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.ActivityCollapsingToolDetailFilmBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.PeliculaJSON
import com.squareup.picasso.Picasso

import android.view.MenuItem
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Genres
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.GestorLista


class FilmDetailSearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCollapsingToolDetailFilmBinding
    private lateinit var pelicula:PeliculaJSON
    private lateinit var menuItem:Menu
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_film_detail)
        supportActionBar?.setTitle("")
        binding = ActivityCollapsingToolDetailFilmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pelicula = intent.extras?.get("pelicula") as PeliculaJSON
        binding.layoutDetallesPeliculaCollapse.FilmDetailETDuracion.setText(pelicula.runtime.toString())

        if (pelicula.genres.size == 0){
            pelicula.genres = List<Genres>(0) {it ->Genres()}
        }
        binding.layoutDetallesPeliculaCollapse.FilmDetailETGenero.setText(pelicula.genres[0].name)
        binding.layoutDetallesPeliculaCollapse.FilmDetailETTitulo.setText(pelicula.title)

        binding.layoutDetallesPeliculaCollapse.FilmDetailTvSinopsis.setText(pelicula.overview)
        Picasso.get().isLoggingEnabled = true

        if (pelicula.posterPath != null)
            Picasso.get().load(pelicula.posterPath).into(binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula)

        if (pelicula.backdropPath != null)
            Picasso.get().load(pelicula.backdropPath).into(binding.collapsingToolDetailBarImagenFondo)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_search_film, menu)
        menuItem = menu!!
        menuItem.add(300,1,1,getString(R.string.FilmDetailSerachActivityItemMenuAddTitle)).setIcon(getDrawable(R.drawable.fab_add))
        menuItem.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        if (id == menuItem.getItem(0).itemId) {
            GestorLista(this).añadirPelicula(pelicula)
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}