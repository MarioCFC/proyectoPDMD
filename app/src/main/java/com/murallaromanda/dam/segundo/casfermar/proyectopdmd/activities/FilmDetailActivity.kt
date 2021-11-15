package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.ActivityFilmDetailBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.ActivityListBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.ActivityMainBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Pelicula

class FilmDetailActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityFilmDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_detail)
        var pelicula = intent.extras?.get("pelicula") as Pelicula

        binding = ActivityFilmDetailBinding.inflate(layoutInflater)
        binding.FilmDetailTvDirector.text = pelicula.director
        binding.FilmDetailTvGenero.text = pelicula.genero
        binding.FilmDetailTvSinopsis.text
        binding.FilmDetailTvTitulo.text = pelicula.titulo

    }
}