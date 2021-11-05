package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.parseColor
import android.icu.number.IntegerWidth
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Pelicula
import com.squareup.picasso.Picasso

class ListaPeliculasAdapter(val peliculas : List<Pelicula>, val miActivty:Activity) : RecyclerView.Adapter<ListaPeliculasAdapter.PeliculaViewHolder>() {
    class PeliculaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val layout_item = itemView.findViewById<ConstraintLayout>(R.id.layout_item)
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

        var valoracion = pelicula.valoracion.toDouble()



        holder.tvValoracion.setTextColor(elegirColor(valoracion))
        holder.tvValoracion.setText(pelicula.valoracion)

        Picasso.get().isLoggingEnabled = true
        Picasso.get().load(pelicula.urlImagen).into(holder.ivCaratula)

        //HAY QUE PASARLE UN CONTEXTO
        holder.layout_item.setOnClickListener(){
            val intent = Intent(,miActivty::class.java,)
        }

    }

    fun elegirColor(valoracion:Double): Int {
        var color: Int
        if (valoracion < 5) {
            color = Color.parseColor("#FF0000")
        }else if(valoracion >= 5 && valoracion < 8) {
            color = Color.parseColor("#FF9300")
        }else{
            color = Color.parseColor("#1D9A05")
        }
        return color
    }

    override fun getItemCount() = peliculas.size
}