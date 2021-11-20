package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.constraintlayout.widget.ConstraintLayout
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.ActivityFilmDetailBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Pelicula
import com.squareup.picasso.Picasso

class FilmDetailActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityFilmDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_detail)

        binding = ActivityFilmDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var pelicula = intent.extras?.get("pelicula") as Pelicula

        binding.FilmDetailTvDirector.text = pelicula.director
        binding.FilmDetailTvGenero.text = pelicula.genero
        binding.FilmDetailTvTitulo.text = pelicula.titulo

        binding.FilmDetailTvSinopsis.setMovementMethod(ScrollingMovementMethod())
        binding.FilmDetailTvSinopsis.text = pelicula.sinopsis
        Picasso.get().isLoggingEnabled = true
        Picasso.get().load(pelicula.urlImagen).into(binding.FilmDetaiIvCaratula)

    }
}