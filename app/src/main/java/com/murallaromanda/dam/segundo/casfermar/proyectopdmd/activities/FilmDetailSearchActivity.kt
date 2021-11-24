package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.ActivityCollapsingToolDetailFilmBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.PeliculaJSON
import com.squareup.picasso.Picasso
import android.widget.Toast

import android.view.MenuItem
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R


class FilmDetailSearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCollapsingToolDetailFilmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_film_detail)

        binding = ActivityCollapsingToolDetailFilmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activityCollapsingLayout.removeView(binding.fabEditar)

        var pelicula = intent.extras?.get("pelicula") as PeliculaJSON
        binding.layoutDetallesPeliculaCollapse.FilmDetailTvDirector.text = "director/Cambiar"
        binding.layoutDetallesPeliculaCollapse.FilmDetailTvGenero.text = pelicula.genres[0].name
        binding.layoutDetallesPeliculaCollapse.FilmDetailTvTitulo.text = pelicula.title

        // binding.layoutDetallesPeliculaCollapse.FilmDetailTvSinopsis.setMovementMethod(ScrollingMovementMethod())
        binding.layoutDetallesPeliculaCollapse.FilmDetailTvSinopsis.text = pelicula.overview
        Picasso.get().isLoggingEnabled = true

        if (pelicula.posterPath != null)
            Picasso.get().load("https://www.themoviedb.org/t/p/w600_and_h900_bestv2" + pelicula.posterPath).into(binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula)

        if (pelicula.backdropPath != null)
            Picasso.get().load("https://www.themoviedb.org/t/p/w600_and_h900_bestv2" + pelicula.backdropPath).into(binding.collapsingToolbarImagenFondo)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_add_search_film, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id: Int = item.getItemId()
        if (id == R.id.appBar) {
            Toast.makeText(this, "Action clicked", Toast.LENGTH_LONG).show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}