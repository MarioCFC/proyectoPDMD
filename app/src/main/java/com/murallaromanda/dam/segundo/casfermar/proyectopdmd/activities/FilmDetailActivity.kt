package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.ActivityCollapsingToolDetailFilmBinding
import com.squareup.picasso.Picasso

import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.PeliculaJSON
import android.util.TypedValue

import androidx.constraintlayout.widget.ConstraintSet

import android.view.*
import androidx.appcompat.app.AlertDialog


class FilmDetailActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityCollapsingToolDetailFilmBinding
    private var estaEnEdicion: Boolean = true
    private lateinit var pelicula: PeliculaJSON
    private lateinit var editText: EditText
    private lateinit var menuItem: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    }

    fun cambiarModoEdicion() {
        binding.layoutDetallesPeliculaCollapse.FilmDetailTvDirector.setEnabled(estaEnEdicion)
        binding.layoutDetallesPeliculaCollapse.FilmDetailTvGenero.setEnabled(estaEnEdicion)
        binding.layoutDetallesPeliculaCollapse.FilmDetailTvTitulo.setEnabled(estaEnEdicion)
    }

    private fun addEditText() {
        editText = EditText(this)
        editText.setText(pelicula.overview)
        editText.setTextColor(getResources().getColor(R.color.enabled_color))

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

    private fun hayModificaciones(): Boolean {
        if (!binding.layoutDetallesPeliculaCollapse.FilmDetailTvTitulo.equals(pelicula.title)
            || binding.layoutDetallesPeliculaCollapse.FilmDetailTvGenero.equals(pelicula.genres[0].name) || !editText.text.equals(
                pelicula.overview
            )
        )
            return true
        return false
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_search_film, menu)
        menuItem = menu!!
        menuItem.add(300, 1, 1, "Borrar").setIcon(getDrawable(R.drawable.ic_launcher_background))
        menuItem.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        //menuItem.getItem(1).setIcon(getDrawable())


        menuItem.add(301, 2, 2, "Editar").setIcon(getDrawable(R.drawable.fab_add))
        menuItem.getItem(1).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id: Int = item.itemId

        when (id) {
            menuItem.getItem(0).itemId -> borrarPelicula()
        }
        return super.onOptionsItemSelected(item)
    }

    fun borrarPelicula() {
        mostrarAviso("Alerta","Deseas elimanar la pelicula?")
    }

    fun mostrarAviso(titulo: String, mensaje: String) {
        val dialogBuilder = AlertDialog.Builder(this)

        // set message of alert dialog
        dialogBuilder.setMessage(mensaje)
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, id ->
                finish()
            })
            // negative button text and action
            .setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("AlertDialogExample")
        // show alert dialog
        alert.show()
    }



    /*
    * override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id: Int = item.getItemId()
        if (id == menuItem.getItem(0).itemId) {
            Json().getLista().add(pelicula)
            Json().guardarFicheroPeliculas(this)
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    * */


}