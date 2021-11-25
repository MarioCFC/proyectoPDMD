package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.ActivityCollapsingToolDetailFilmBinding
import com.squareup.picasso.Picasso

import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.PeliculaJSON


class FilmDetailActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityCollapsingToolDetailFilmBinding
    private var estaEnEdicion : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_film_detail)
        binding = ActivityCollapsingToolDetailFilmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Obtenemos los datos
        var pelicula = intent.extras?.get("pelicula") as PeliculaJSON
        //Metemos los datos
        binding.layoutDetallesPeliculaCollapse.FilmDetailTvDirector.setText("director/Cambiar")
        binding.layoutDetallesPeliculaCollapse.FilmDetailTvGenero.setText(pelicula.genres[0].name)
        binding.layoutDetallesPeliculaCollapse.FilmDetailTvTitulo.setText(pelicula.title)
        binding.layoutDetallesPeliculaCollapse.FilmDetailTvSinopsis.setText(pelicula.overview)

        cambiarModoEdicion()

        //Imagenes
        Picasso.get().isLoggingEnabled = true
        if (pelicula.posterPath != null)
            Picasso.get().load("https://www.themoviedb.org/t/p/w600_and_h900_bestv2" + pelicula.posterPath).into(binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula)

        if (pelicula.backdropPath != null)
            Picasso.get().load("https://www.themoviedb.org/t/p/w600_and_h900_bestv2" + pelicula.backdropPath).into(binding.collapsingToolbarImagenFondo)
        binding.fabEditar.setOnClickListener(){
            cambiarModoEdicion()
        }
    }

    fun cambiarModoEdicion(){
        binding.layoutDetallesPeliculaCollapse.FilmDetailTvDirector.setEnabled(estaEnEdicion)
        binding.layoutDetallesPeliculaCollapse.FilmDetailTvGenero.setEnabled(estaEnEdicion)
        binding.layoutDetallesPeliculaCollapse.FilmDetailTvTitulo.setEnabled(estaEnEdicion)
        estaEnEdicion = !estaEnEdicion
    }



}