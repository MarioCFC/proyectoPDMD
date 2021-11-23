package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities.FilmDetailActivity
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Pelicula
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.PeliculaJSON
import com.squareup.picasso.Picasso

//Creo que le pasamos el mismo objeto para la activity y para el Context, revisar si se da pasado una sola vez
class ListaPeliculasAdapter(val peliculas : ArrayList<PeliculaJSON>, val miActivty:Activity) : RecyclerView.Adapter<ListaPeliculasAdapter.PeliculaViewHolder>() {
    class PeliculaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        //Mejor cambiarlo por el binding
        /*
        val layout_item = itemView.findViewById<ConstraintLayout>(R.id.layout_item)
        val tvTitulo = itemView.findViewById<TextView>(R.id.itemTvTitulo)
        val tvGenero = itemView.findViewById<TextView>(R.id.FilmDetailTvGenero)
        val ivCaratula = itemView.findViewById<ImageView>(R.id.ItemIvCaratula)
        */

        val layout_item = itemView.findViewById<LinearLayout>(R.id.card_item)
        val tvTitulo = itemView.findViewById<TextView>(R.id.CardTvTitulo)
        val tvGenero = itemView.findViewById<TextView>(R.id.CardTvGenero)
        val ivCaratula = itemView.findViewById<ImageView>(R.id.CardIvCaratula)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeliculaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.film_card_view, parent, false)
        return PeliculaViewHolder(layoutInflater)
    }

    //Preracion del layout de cada objeto de la lista
    override fun onBindViewHolder(holder: PeliculaViewHolder, position: Int) {
        val pelicula = peliculas.get(position)

        holder.tvTitulo.setText(pelicula.title)
        holder.tvGenero.setText(pelicula.genres[0].name)

        Picasso.get().isLoggingEnabled = true

        if (pelicula.posterPath != null)
            Picasso.get().load("https://www.themoviedb.org/t/p/w600_and_h900_bestv2" + pelicula.posterPath).into(holder.ivCaratula)

        holder.layout_item.setOnClickListener(){
            val intent = Intent(miActivty,FilmDetailActivity::class.java)
            intent.putExtra("pelicula", pelicula)
            miActivty.startActivity(intent)
        }

    }

    override fun getItemCount() = peliculas.size
    fun formatearCadena(cadena:String):String{
        val tamañoCadena:Int = 50
        if (cadena.length > tamañoCadena)
            return cadena.substring(0,tamañoCadena -  1) + "..."
        else
            return cadena

    }

}