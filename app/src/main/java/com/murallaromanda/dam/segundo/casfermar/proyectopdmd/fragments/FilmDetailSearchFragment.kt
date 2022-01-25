package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.IO.ApiService
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.FragmentCollapsingToolDetailFilmBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Genres
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.PeliculaJSON
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.GestorLista
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.RetrofitClient
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilmDetailSearchFragment:Fragment() {

    private lateinit var binding: FragmentCollapsingToolDetailFilmBinding
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
        var idPelicula :Int = (requireArguments().getSerializable("idPelicula")) as Int

        //Pedimos los datos de la pelicula
        var call: Call<PeliculaJSON> = RetrofitClient.getInstance().getResultados()
            .getMovieData(idPelicula, ApiService.API_KEY)

        call.enqueue(object :Callback<PeliculaJSON>{
            override fun onResponse(call: Call<PeliculaJSON>, response: Response<PeliculaJSON>) {
                binding.layoutDetallesPeliculaCollapse.FilmDetailETDuracion.setText(response.body()!!.runtime.toString())

                if (response.body()!!.genres.size == 0){
                    response.body()!!.genres = List<Genres>(0) { it -> Genres() }
                }
                binding.layoutDetallesPeliculaCollapse.FilmDetailETGenero.setText(response.body()!!.genres[0].name)
                binding.layoutDetallesPeliculaCollapse.FilmDetailETTitulo.setText(response.body()!!.title)

                binding.layoutDetallesPeliculaCollapse.FilmDetailTvSinopsis.setText(response.body()!!.overview)
                Picasso.get().isLoggingEnabled = true

                if (response.body()!!.posterPath != null)
                    Picasso.get().load(ApiService.POSTER_PATH + response.body()!!.posterPath).into(binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula)

                if (response.body()!!.backdropPath != null)
                    Picasso.get().load(ApiService.POSTER_PATH + response.body()!!.backdropPath).into(binding.collapsingToolDetailBarImagenFondo)




            }

            override fun onFailure(call: Call<PeliculaJSON>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })



        return binding.root


        /*Bindeado*/

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        miActivity.menuInflater.inflate(R.menu.menu_add_base, menu)
        menuItem = menu!!
        menuItem.add(300,1,1,getString(R.string.FilmDetailSerachActivityItemMenuAddTitle)).setIcon(miActivity.getDrawable(
            R.drawable.fab_add))
        menuItem.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)

        super.onCreateOptionsMenu(menu, inflater)
    }
/*A priori inutilizada
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        if (id == menuItem.getItem(0).itemId) {
            GestorLista(miActivity).añadirPelicula(response)
            parentFragmentManager.popBackStack()
            return true
        }
        return false
    }
*/

}