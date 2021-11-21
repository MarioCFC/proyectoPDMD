package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.PruebaDetalleBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Pelicula
import com.squareup.picasso.Picasso

class FilmDetailActivity() : AppCompatActivity() {
    private lateinit var binding: PruebaDetalleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prueba_detalle)

        binding = PruebaDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var pelicula = intent.extras?.get("pelicula") as Pelicula


        binding.layoutIncludeDetailFilm.FilmDetailTvDirector.text = pelicula.director
        binding.layoutIncludeDetailFilm.FilmDetailTvGenero.text = pelicula.genero
        binding.layoutIncludeDetailFilm.FilmDetailTvTitulo.text = pelicula.titulo

       // binding.layoutIncludeDetailFilm.FilmDetailTvSinopsis.setMovementMethod(ScrollingMovementMethod())
        binding.layoutIncludeDetailFilm.FilmDetailTvSinopsis.text = pelicula.sinopsis
        Picasso.get().isLoggingEnabled = true
        Picasso.get().load(pelicula.urlImagen).into(binding.layoutIncludeDetailFilm.FilmDetaiIvCaratula)
        Picasso.get().load(pelicula.urlImagen).into(binding.imagenLayout)


    }
}