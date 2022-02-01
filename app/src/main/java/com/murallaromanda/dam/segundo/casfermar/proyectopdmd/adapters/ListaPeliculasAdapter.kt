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
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.IO.DAMApiService
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities.FilmListFragmentManagerActivity
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.fragments.FilmDetailSearchFragment
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Movie
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.RetrofitService
import com.squareup.picasso.Picasso
import retrofit2.Call

class ListaPeliculasAdapter(
    val peliculas: List<Movie>,
    val miActivty: Activity
) :
    RecyclerView.Adapter<ListaPeliculasAdapter.PeliculaViewHolder>() {
    class PeliculaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout_item = itemView.findViewById<LinearLayout>(R.id.card_item)
        val ivCaratula = itemView.findViewById<ImageView>(R.id.CardIvCaratula)
        val tvTitulo = itemView.findViewById<TextView>(R.id.CardTvTitulo)
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

        Picasso.get().isLoggingEnabled = true


        //Arreglar la url para cargar la imagen bien
        if (pelicula.imageUrl != null)
            Picasso.get()
                .load(pelicula.imageUrl)
                .into(holder.ivCaratula)

        holder.layout_item.setOnClickListener() {
            val ft =
                (miActivty as FilmListFragmentManagerActivity).supportFragmentManager?.beginTransaction()

            var datos = Bundle()
            datos.putSerializable("idPelicula", pelicula.id)
            var nuevoFragment = FilmDetailSearchFragment()
            nuevoFragment.arguments = datos

            ft?.replace(R.id.contenedor_fragments, nuevoFragment)
            ft?.addToBackStack(null)
            ft?.commit()

/*
            var call: Call<Movie> = RetrofitService.getInstance().getResultados()
                .getMovieData(pelicula.id!!, DAMApiService.API_KEY)



            call.enqueue(object :Callback<PeliculaJSON>{
                override fun onResponse(
                    call: Call<PeliculaJSON>,
                    response: Response<PeliculaJSON>
                ) {
                    var datos = Bundle()
                    datos.putSerializable("pelicula", response.body())
                    var nuevoFragment = FilmDetailSearchFragment()
                    nuevoFragment.arguments = datos

                    ft?.replace(R.id.contenedor_fragments, nuevoFragment)
                    ft?.addToBackStack(null)
                    ft?.commit()
                }

                override fun onFailure(call: Call<PeliculaJSON>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })

*/

        }

    }

    override fun getItemCount() = peliculas.size

}