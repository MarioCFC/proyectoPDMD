package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.FragmentCollapsingToolDetailFilmBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Genres
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.PeliculaJSON
import com.squareup.picasso.Picasso

class FilmDetailSearchFragment:Fragment() {

    private lateinit var binding: FragmentCollapsingToolDetailFilmBinding
    private lateinit var pelicula: PeliculaJSON
    private lateinit var menuItem: Menu

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCollapsingToolDetailFilmBinding.inflate(inflater,container,false)
        pelicula = (requireArguments().getSerializable("pelicula")) as PeliculaJSON
        binding.layoutDetallesPeliculaCollapse.FilmDetailETDuracion.setText(pelicula.runtime.toString())

        if (pelicula.genres.size == 0){
            pelicula.genres = List<Genres>(0) { it -> Genres() }
        }
        binding.layoutDetallesPeliculaCollapse.FilmDetailETGenero.setText(pelicula.genres[0].name)
        binding.layoutDetallesPeliculaCollapse.FilmDetailETTitulo.setText(pelicula.title)

        binding.layoutDetallesPeliculaCollapse.FilmDetailTvSinopsis.setText(pelicula.overview)
        Picasso.get().isLoggingEnabled = true

        if (pelicula.posterPath != null)
            Picasso.get().load(pelicula.posterPath).into(binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula)

        if (pelicula.backdropPath != null)
            Picasso.get().load(pelicula.backdropPath).into(binding.collapsingToolDetailBarImagenFondo)



        return binding.root
    }
}