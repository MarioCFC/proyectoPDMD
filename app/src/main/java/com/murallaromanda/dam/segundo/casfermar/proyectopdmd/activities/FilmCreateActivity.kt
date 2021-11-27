package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities

import android.R.attr
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.ActivityCollapsingToolDetailFilmBinding
import com.squareup.picasso.Picasso

import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.PeliculaJSON
import android.R.attr.button
import android.os.Build
import android.util.TypedValue
import android.view.View
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout

import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.widget.NestedScrollView
import androidx.core.app.ActivityCompat.startActivityForResult

import android.content.Intent
import android.database.Cursor
import android.provider.MediaStore
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.app.ActivityCompat.startActivityForResult
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.MenuItem
import android.widget.ImageView
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.GestorLista

//NO ESTAS GUARDANDO LA PELICULA
class FilmCreateActivity() : AppCompatActivity() {
    lateinit var binding: ActivityCollapsingToolDetailFilmBinding

    private lateinit var pelicula: PeliculaJSON
    private lateinit var editText: EditText
    private lateinit var menuItem:Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_film_detail)
        binding = ActivityCollapsingToolDetailFilmBinding.inflate(layoutInflater)
        //Eliminando el boton flotante y el edit readmore de sinopsis
        binding.layoutDetallesPeliculaCollapse.constraintFilmDetailLayout.removeView(binding.layoutDetallesPeliculaCollapse.FilmDetailTvSinopsis)
        binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula.setBackgroundColor(Color.GRAY)

        binding.collapsingToolbarImagenFondo.setImageDrawable(getDrawable(R.drawable.ic_baseline_camera_alt_24))
        binding.collapsingToolbarImagenFondo.setBackgroundColor(Color.GRAY)
        binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula.setImageDrawable(getDrawable(R.drawable.ic_baseline_camera_alt_24))
        binding.collapsingToolbarImagenFondo.setBackgroundColor(Color.GRAY)

        setContentView(binding.root)
        cambiarModoEdicion()
        addEditText()
    }



    fun cambiarModoEdicion() {
        binding.layoutDetallesPeliculaCollapse.FilmDetailTvDirector.setEnabled(true)
        binding.layoutDetallesPeliculaCollapse.FilmDetailTvGenero.setEnabled(true)
        binding.layoutDetallesPeliculaCollapse.FilmDetailTvTitulo.setEnabled(true)
    }

    private fun addEditText() {
        editText = EditText(this)
        editText.setText("Sinopsis")

        editText.setBackgroundResource(android.R.color.transparent);
        editText.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        editText.id = View.generateViewId()
        editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        var constraintLayout = binding.layoutDetallesPeliculaCollapse.constraintFilmDetailLayout
        constraintLayout.addView(editText)

        var constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        constraintSet.connect(
            editText.getId(),
            ConstraintSet.END,
            ConstraintSet.PARENT_ID,
            ConstraintSet.END
        );
        constraintSet.connect(
            editText.getId(),
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START
        );
        constraintSet.connect(
            editText.getId(),
            ConstraintSet.TOP,
            binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula.id,
            ConstraintSet.BOTTOM
        );
        constraintSet.applyTo(constraintLayout)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_search_film, menu)
        menuItem = menu!!
        menuItem.add(300, 1, 1, "Crear").setIcon(getDrawable(R.drawable.ic_baseline_check))
        menuItem.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id: Int = item.itemId
        //Dandole a eliminar muestra un aviso, este metodo nos permite pasarle otro metodo para que ejecute en caso afirmativo
        when (id) {
            menuItem.getItem(0).itemId -> GestorLista(this).añadirPelicula(almacenarDatosPelicula())
        }
        return super.onOptionsItemSelected(item)
    }

    fun almacenarDatosPelicula() : PeliculaJSON {
        var pelicula = PeliculaJSON()
        pelicula.title = binding.layoutDetallesPeliculaCollapse.FilmDetailTvSinopsis.text.toString()
        pelicula.genres[0].name = binding.layoutDetallesPeliculaCollapse.FilmDetailTvGenero.text.toString()
        pelicula.overview = binding.layoutDetallesPeliculaCollapse.FilmDetailTvSinopsis.text.toString()
        return pelicula
    }


}