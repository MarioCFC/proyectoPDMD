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
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.text.InputType

class FilmDetailActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityCollapsingToolDetailFilmBinding
    private var gestorLista = GestorLista(this)
    private lateinit var pelicula: PeliculaJSON
    private var posicionEnLista: Int = 0
    private lateinit var menuItem: Menu
    private lateinit var editText: EditText
    private var estaEnEdicion: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCollapsingToolDetailFilmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Obtenemos los datos. Hago referencia a la lista para poder modificar los datos con facilidad y poder guardar los cambios sin demasiada complicacion
        posicionEnLista = intent.extras?.get("posicionEnLista") as Int
        pelicula = gestorLista.getPeliculas().get(posicionEnLista)

        //Metemos los datos
        binding.layoutDetallesPeliculaCollapse.FilmDetailETDuracion.setText(pelicula.runtime.toString())
        binding.layoutDetallesPeliculaCollapse.FilmDetailETGenero.setText(pelicula.genres[0].name)
        binding.layoutDetallesPeliculaCollapse.FilmDetailETTitulo.setText(pelicula.title)
        binding.layoutDetallesPeliculaCollapse.FilmDetailTvSinopsis.setText(pelicula.overview)


        //Imagenes
        Picasso.get().isLoggingEnabled = true
        if (pelicula.posterPath != null)
            Picasso.get()
                .load(pelicula.posterPath)
                .into(binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula)

        if (pelicula.backdropPath != null)
            Picasso.get()
                .load(pelicula.backdropPath)
                .into(binding.collapsingToolbarImagenFondo)



        binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula.setOnClickListener() {
            if (!estaEnEdicion){
                val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("Url de la caraturla")
            builder.setMessage("Introduce la URL de la imagen")

            val input = EditText(this)
            input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_CLASS_TEXT
            builder.setView(input)

            builder.setPositiveButton("OK",
                DialogInterface.OnClickListener { dialog, which ->
                    pelicula.posterPath = input.text.toString()
                    Picasso.get()
                        .load(pelicula.posterPath)
                        .into(binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula)
                })
            builder.setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

            builder.show()
            }
        }

        binding.collapsingToolbarImagenFondo.setOnClickListener(){
            if (!estaEnEdicion){
                val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("Url del fondo")
            builder.setMessage("Introduce la URL de la imagen")

            val input = EditText(this)
            input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_CLASS_TEXT
            builder.setView(input)

            builder.setPositiveButton("OK",
                DialogInterface.OnClickListener { dialog, which ->
                    {
                        pelicula.backdropPath = input.text.toString()
                        Picasso.get().load(pelicula.backdropPath).into(binding.collapsingToolbarImagenFondo)
                    }
                })
            builder.setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

            builder.show()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_search_film, menu)
        menuItem = menu!!
        //Boton borrar
        menuItem.add(300, 1, 1, "Borrar").setIcon(getDrawable(R.drawable.ic_baseline_delete))
        menuItem.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)

        menuItem.add(300,2,2,"Abrir en el navegador").setIcon(getDrawable(R.drawable.ic_baseline_browser))
        menuItem.getItem(1).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)

        //Boton editar
        menuItem.add(301, 3, 3, "Editar").setIcon(getDrawable(R.drawable.ic_baseline_edit))
        menuItem.getItem(2).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)


        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id: Int = item.itemId
        //Dandole a eliminar muestra un aviso, este metodo nos permite pasarle otro metodo para que ejecute en caso afirmativo
        when (id) {
            menuItem.getItem(0).itemId ->avisoBorrarPelicula()

            menuItem.getItem(1).itemId -> {
                val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=" + pelicula.title))
                startActivity(myIntent)
            }

            menuItem.getItem(2).itemId -> {
                if (!estaEnEdicion && hayCambios()) {
                    cargarCambios()
                    gestorLista.guardarPelicula()
                }
                cambiarModoEdicion()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    fun cambiarModoEdicion() {
        cambiarModoEdicionComponentes()
        cambiarModoEdicionMenu()
        estaEnEdicion = !estaEnEdicion

    }

    fun cambiarModoEdicionComponentes() {
        //Cambiando las labels
        binding.layoutDetallesPeliculaCollapse.FilmDetailETDuracion.setEnabled(estaEnEdicion)
        binding.layoutDetallesPeliculaCollapse.FilmDetailETGenero.setEnabled(estaEnEdicion)
        binding.layoutDetallesPeliculaCollapse.FilmDetailETTitulo.setEnabled(estaEnEdicion)

        if (estaEnEdicion) {
            //Ocultamos el ReadMore y creamos el edit text
            binding.layoutDetallesPeliculaCollapse.FilmDetailTvSinopsis.visibility = View.GONE
            addEditText()

            //Imagenes
            binding.collapsingToolbarImagenFondo.setImageDrawable(getDrawable(R.drawable.ic_baseline_camera_alt_24))
            binding.collapsingToolbarImagenFondo.setBackgroundColor(Color.GRAY)
            binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula.setImageDrawable(getDrawable(R.drawable.ic_baseline_camera_alt_24))
            binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula.setBackgroundColor(Color.GRAY)

        } else {
            //Eliminamos el editText
            binding.layoutDetallesPeliculaCollapse.constraintFilmDetailLayout.removeViewAt(
                binding.layoutDetallesPeliculaCollapse.constraintFilmDetailLayout.getChildCount() - 1
            )
            //Hacemos visible el ReadMore y le colocamos el texto del EditText
            binding.layoutDetallesPeliculaCollapse.FilmDetailTvSinopsis.visibility =
                View.VISIBLE
            binding.layoutDetallesPeliculaCollapse.FilmDetailTvSinopsis.setText(pelicula.overview)

            //Imagenes
            Picasso.get()
                .load(pelicula.posterPath)
                .into(binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula)

            Picasso.get()
                .load(pelicula.backdropPath)
                .into(binding.collapsingToolbarImagenFondo)
        }
    }

    fun cambiarModoEdicionMenu() {
        if (estaEnEdicion) {
            menuItem.setGroupVisible(300,false)
            menuItem.getItem(2).setIcon(getDrawable(R.drawable.ic_baseline_check))
        } else {
            menuItem.setGroupVisible(300,true)
            menuItem.getItem(2).setIcon(getDrawable(R.drawable.ic_baseline_edit))
        }
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


    fun cargarCambios() {
        //binding.layoutDetallesPeliculaCollapse.FilmDetailTvDirector.setText("director/Cambiar")
        pelicula.genres[0].name =
            binding.layoutDetallesPeliculaCollapse.FilmDetailETGenero.getText().toString()
        pelicula.title =
            binding.layoutDetallesPeliculaCollapse.FilmDetailETTitulo.getText().toString()
        pelicula.overview = editText.text.toString()

    }

    fun hayCambios():Boolean{
        return binding.layoutDetallesPeliculaCollapse.FilmDetailETTitulo.getText().toString() != pelicula.title ||
        binding.layoutDetallesPeliculaCollapse.FilmDetailETGenero.getText().toString() != pelicula.genres[0].name ||
        editText.text.toString() != pelicula.overview
    }


    //Generarcion de aviso
    fun avisoBorrarPelicula() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("Alerta")
        // set message of alert dialog
        dialogBuilder.setMessage("Deseas elimanar la pelicula?")
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, id ->
                gestorLista.borrarPelicula(pelicula)
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