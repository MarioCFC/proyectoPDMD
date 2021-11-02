package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Pelicula
import com.squareup.picasso.Picasso

class ListaPeliculasAdapter(val peliculas : List<Pelicula>) : RecyclerView.Adapter<ListaPeliculasAdapter.PeliculaViewHolder>() {
    class PeliculaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvTitulo = itemView.findViewById<TextView>(R.id.tvTitulo)
        val tvGenero = itemView.findViewById<TextView>(R.id.tvGenero)
        val tvDirector = itemView.findViewById<TextView>(R.id.tvDirector)
        val tvValoracion = itemView.findViewById<TextView>(R.id.tvValoracion)
        val ivCaratula = itemView.findViewById<ImageView>(R.id.ivCaratula)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeliculaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_pelicula_list, parent, false)
        return PeliculaViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: PeliculaViewHolder, position: Int) {
        val pelicula = peliculas.get(position)

        holder.tvTitulo.setText(pelicula.titulo)
        holder.tvGenero.setText(pelicula.genero)
        holder.tvDirector.setText(pelicula.director)
        holder.tvValoracion.setText(pelicula.valoracion)
        Picasso.get().load(pelicula.urlImagen).into(holder.ivCaratula)

    }

    override fun getItemCount() = peliculas.size
}