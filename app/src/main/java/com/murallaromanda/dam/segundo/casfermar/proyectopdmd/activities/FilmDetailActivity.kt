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





class FilmDetailActivity() : AppCompatActivity() {
    private lateinit var binding: CollapsingToolDetailFilmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        var interruptorSinopsis:Boolean = false

        super.onCreate(savedInstanceState)
        setContentView(com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R.layout.activity_film_detail)

        binding = CollapsingToolDetailFilmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var pelicula = intent.extras?.get("pelicula") as Pelicula
        binding.layoutDetallesPeliculaCollapse.FilmDetailTvDirector.text = pelicula.director
        binding.layoutDetallesPeliculaCollapse.FilmDetailTvGenero.text = pelicula.genero
        binding.layoutDetallesPeliculaCollapse.FilmDetailTvTitulo.text = pelicula.titulo

       // binding.layoutDetallesPeliculaCollapse.FilmDetailTvSinopsis.setMovementMethod(ScrollingMovementMethod())
        binding.layoutDetallesPeliculaCollapse.FilmDetailTvSinopsis.text = pelicula.sinopsis
        Picasso.get().isLoggingEnabled = true
        Picasso.get().load(pelicula.urlImagen).into(binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula)
        Picasso.get().load(pelicula.urlBanner).into(binding.collapsingToolbarImagenFondo)

        //Evento de toque sobre el textView


    }
}