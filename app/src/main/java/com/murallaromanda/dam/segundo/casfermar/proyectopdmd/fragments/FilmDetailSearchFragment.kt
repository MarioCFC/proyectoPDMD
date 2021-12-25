package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.FragmentCollapsingToolDetailFilmBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Genres
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.PeliculaJSON
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.GestorLista
import com.squareup.picasso.Picasso

class FilmDetailSearchFragment:Fragment() {

    private lateinit var binding: FragmentCollapsingToolDetailFilmBinding
    private lateinit var pelicula: PeliculaJSON
    private lateinit var menuItem: Menu
    private lateinit var miActivity :AppCompatActivity
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        miActivity = (activity as AppCompatActivity)
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        miActivity.menuInflater.inflate(R.menu.menu_add_base, menu)
        menuItem = menu!!
        menuItem.add(300,1,1,getString(R.string.FilmDetailSerachActivityItemMenuAddTitle)).setIcon(miActivity.getDrawable(
            R.drawable.fab_add))
        menuItem.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        if (id == menuItem.getItem(0).itemId) {
            GestorLista(miActivity).añadirPelicula(pelicula)
            parentFragmentManager.popBackStack()
            return true
        }
        return false
    }


}