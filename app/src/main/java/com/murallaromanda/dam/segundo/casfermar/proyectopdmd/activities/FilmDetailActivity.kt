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
import android.widget.ImageView


class FilmDetailActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityCollapsingToolDetailFilmBinding
    private var estaEnEdicion: Boolean = true
    private lateinit var pelicula: PeliculaJSON
    private lateinit var editText:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_film_detail)
        binding = ActivityCollapsingToolDetailFilmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Obtenemos los datos
        pelicula = intent.extras?.get("pelicula") as PeliculaJSON
        //Metemos los datos
        binding.layoutDetallesPeliculaCollapse.FilmDetailTvDirector.setText("director/Cambiar")
        binding.layoutDetallesPeliculaCollapse.FilmDetailTvGenero.setText(pelicula.genres[0].name)
        binding.layoutDetallesPeliculaCollapse.FilmDetailTvTitulo.setText(pelicula.title)
        binding.layoutDetallesPeliculaCollapse.FilmDetailTvSinopsis.setText(pelicula.overview)


        //Imagenes
        Picasso.get().isLoggingEnabled = true
        if (pelicula.posterPath != null)
            Picasso.get()
                .load("https://www.themoviedb.org/t/p/w600_and_h900_bestv2" + pelicula.posterPath)
                .into(binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula)

        if (pelicula.backdropPath != null)
            Picasso.get()
                .load("https://www.themoviedb.org/t/p/w600_and_h900_bestv2" + pelicula.backdropPath)
                .into(binding.collapsingToolbarImagenFondo)


        binding.fabEditar.setOnClickListener() {
            if (estaEnEdicion) {
                binding.layoutDetallesPeliculaCollapse.FilmDetailTvSinopsis.visibility = View.GONE
                addEditText()
            } else {
                //Para borrar el edit text, no se porque si lo uso con removeView no funciona bien
                binding.layoutDetallesPeliculaCollapse.constraintFilmDetailLayout.removeViewAt(
                    binding.layoutDetallesPeliculaCollapse.constraintFilmDetailLayout.getChildCount() - 1
                );
                binding.layoutDetallesPeliculaCollapse.FilmDetailTvSinopsis.visibility =
                    View.VISIBLE
                binding.layoutDetallesPeliculaCollapse.FilmDetailTvSinopsis.setText(pelicula.overview)
            }
            cambiarModoEdicion()
            estaEnEdicion = !estaEnEdicion
        }
/*
        binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula.setOnClickListener(){
            if (!estaEnEdicion){

                    val i = Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    startActivityForResult(i, RESULT_LOAD_IMAGE)
                }

            }*/
    }

    /*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            val selectedImage: Uri? = data.data
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            val cursor: Cursor? = selectedImage?.let {
                getContentResolver().query(
                    it,
                    filePathColumn, null, null, null
                )
            }
            cursor?.moveToFirst()
            val columnIndex: Int? = cursor?.getColumnIndex(filePathColumn[0])
            val picturePath: String? = columnIndex?.let { cursor?.getString(it) }
            cursor?.close()
            val imageView: ImageView =
                binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula as ImageView
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath))
        }
    }

*/
    fun cambiarModoEdicion() {
        binding.layoutDetallesPeliculaCollapse.FilmDetailTvDirector.setEnabled(estaEnEdicion)
        binding.layoutDetallesPeliculaCollapse.FilmDetailTvGenero.setEnabled(estaEnEdicion)
        binding.layoutDetallesPeliculaCollapse.FilmDetailTvTitulo.setEnabled(estaEnEdicion)
    }

    private fun addEditText() {
        editText = EditText(this)
        editText.setText(pelicula.overview)

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

    private fun hayModificaciones():Boolean {
        if (!binding.layoutDetallesPeliculaCollapse.FilmDetailTvTitulo.equals(pelicula.title)
            || binding.layoutDetallesPeliculaCollapse.FilmDetailTvGenero.equals(pelicula.genres[0].name) || !editText.text.equals(pelicula.overview))
                return true
        return false
    }


}