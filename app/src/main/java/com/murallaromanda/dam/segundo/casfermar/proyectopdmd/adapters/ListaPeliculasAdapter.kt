package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.adapters

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities.FilmListFragmentManagerActivity
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.fragments.FilmDetailFragment
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.fragments.FilmDetailSearchFragment
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.PeliculaJSON
import com.squareup.picasso.Picasso

class ListaPeliculasAdapter(val peliculas: ArrayList<PeliculaJSON>, val miActivty: Activity) :
    RecyclerView.Adapter<ListaPeliculasAdapter.PeliculaViewHolder>() {
    class PeliculaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout_item = itemView.findViewById<LinearLayout>(R.id.card_item)
        val tvTitulo = itemView.findViewById<TextView>(R.id.CardTvTitulo)
        val tvGenero = itemView.findViewById<TextView>(R.id.CardTvGenero)
        val ivCaratula = itemView.findViewById<ImageView>(R.id.CardIvCaratula)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeliculaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_film_card_view, parent, false)
        return PeliculaViewHolder(layoutInflater)
    }

    //Preracion del layout de cada objeto de la lista
    override fun onBindViewHolder(holder: PeliculaViewHolder, position: Int) {
        val pelicula = peliculas.get(position)

        holder.tvTitulo.setText(pelicula.title)
        holder.tvGenero.setText(pelicula.genres[0].name.toString())

        Picasso.get().isLoggingEnabled = true

        if (pelicula.posterPath != null)
            Picasso.get()
                .load(pelicula.posterPath)
                .into(holder.ivCaratula)

        holder.layout_item.setOnClickListener() {
            //Detecta el fragment desde el que lo llaman
            val ft = (miActivty as FilmListFragmentManagerActivity).supportFragmentManager?.beginTransaction()
            var nombreFragment = miActivty.supportFragmentManager.findFragmentById(R.id.contenedor_fragments)!!.javaClass.name

            if (nombreFragment.contains("fragments.FilmListFragment")) {
                var datos = Bundle()
                datos.putInt("posicionEnLista",position)
                var nuevoFragment = FilmDetailFragment()
                nuevoFragment.arguments = datos

                ft?.replace(R.id.contenedor_fragments, nuevoFragment)
                ft?.addToBackStack(null)
                ft?.commit()

            } else if (nombreFragment.contains("fragments.FilmSearchFragment")) {
                var datos = Bundle()
                datos.putSerializable("pelicula",pelicula)
                var nuevoFragment = FilmDetailSearchFragment()
                nuevoFragment.arguments = datos

                ft?.replace(R.id.contenedor_fragments, nuevoFragment)
                ft?.addToBackStack(null)
                ft?.commit()
            }

        }

    }

    override fun getItemCount() = peliculas.size

}