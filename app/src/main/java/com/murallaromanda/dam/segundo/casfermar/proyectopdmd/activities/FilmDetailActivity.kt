package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.MotionEvent
import android.view.View
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.CollapsingToolDetailFilmBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Pelicula
import com.squareup.picasso.Picasso
import android.view.View.OnTouchListener
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

import androidx.core.content.ContextCompat

import android.animation.ObjectAnimator
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.PeliculaJSON
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.JSONPelicula


class FilmDetailActivity() : AppCompatActivity() {
    private lateinit var binding: CollapsingToolDetailFilmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        var interruptorSinopsis:Boolean = false

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_detail)

        binding = CollapsingToolDetailFilmBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
}