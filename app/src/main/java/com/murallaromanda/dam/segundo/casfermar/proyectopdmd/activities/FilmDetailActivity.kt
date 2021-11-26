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
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.GestorLista


class FilmDetailActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityCollapsingToolDetailFilmBinding
    private var gestorLista = GestorLista(this)
    private lateinit var pelicula: PeliculaJSON
    private lateinit var menuItem: Menu
    private lateinit var editText: EditText
    private var estaEnEdicion: Boolean = true

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


        //Intercalando modo edicion
        binding.fabEditar.setOnClickListener() {
            //Ocultamos el ReadMore y creamos un editText
            if (estaEnEdicion) {
                binding.layoutDetallesPeliculaCollapse.FilmDetailTvSinopsis.visibility = View.GONE
                addEditText()
            } else {
                //Borrando el editTextSinopsis
                //Para borrar el edit text, no se porque si lo uso con removeView no funciona bien
                binding.layoutDetallesPeliculaCollapse.constraintFilmDetailLayout.removeViewAt(
                    binding.layoutDetallesPeliculaCollapse.constraintFilmDetailLayout.getChildCount() - 1
                );

                //Mostrando de nuevo el ReadMore de la sinopsis
                binding.layoutDetallesPeliculaCollapse.FilmDetailTvSinopsis.visibility =
                    View.VISIBLE
                binding.layoutDetallesPeliculaCollapse.FilmDetailTvSinopsis.setText(pelicula.overview)
            }
            cambiarModoEdicion()
            estaEnEdicion = !estaEnEdicion
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_search_film, menu)
        menuItem = menu!!
        menuItem.add(300, 1, 1, "Borrar").setIcon(getDrawable(R.drawable.ic_launcher_background))
        menuItem.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)


        menuItem.add(301, 2, 2, "Editar").setIcon(getDrawable(R.drawable.fab_add))
        menuItem.getItem(1).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id: Int = item.itemId
        //Dandole a eliminar muestra un aviso, este metodo nos permite pasarle otro metodo para que ejecute en caso afirmativo
        when (id) {
            menuItem.getItem(0).itemId -> mostrarAviso(
                "Alerta",
                "Deseas elimanar la pelicula?",
                gestorLista.borrarPelicula(pelicula)
            )
        }
        return super.onOptionsItemSelected(item)
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

    override fun onBackPressed() {
        if (hayModificaciones()){
            mostrarAviso("Aviso","Se han detectado cambios realizados,deseas guardarlos?",cargarCambios())
        }
        super.onBackPressed()
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

    fun cargarCambios(){
        //binding.layoutDetallesPeliculaCollapse.FilmDetailTvDirector.setText("director/Cambiar")
        pelicula.genres[0].name = binding.layoutDetallesPeliculaCollapse.FilmDetailTvGenero.getText().toString()
        pelicula.title = binding.layoutDetallesPeliculaCollapse.FilmDetailTvTitulo.getText().toString()
        pelicula.overview = binding.layoutDetallesPeliculaCollapse.FilmDetailTvSinopsis.getText().toString()

    }


    //Generarcin de aviso
    fun mostrarAviso(titulo: String, mensaje: String, bar: Unit) {
        val dialogBuilder = AlertDialog.Builder(this)

        // set message of alert dialog
        dialogBuilder.setMessage(mensaje)
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, id ->
                bar
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




}