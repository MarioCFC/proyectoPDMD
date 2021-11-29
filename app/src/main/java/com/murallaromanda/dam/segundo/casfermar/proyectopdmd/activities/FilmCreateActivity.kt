package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.EditText
import android.widget.LinearLayout
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.ActivityCollapsingToolDetailFilmBinding
import com.squareup.picasso.Picasso
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.PeliculaJSON
import android.util.TypedValue
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import android.view.MenuItem
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Genres
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.GestorLista
import android.content.DialogInterface
import android.text.InputType


class FilmCreateActivity() : AppCompatActivity() {
    lateinit var binding: ActivityCollapsingToolDetailFilmBinding
    private var url:String = ""
    private lateinit var editText: EditText
    private lateinit var menuItem: Menu
    private var pelicula = PeliculaJSON()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_film_detail)
        supportActionBar?.setTitle("")
        binding = ActivityCollapsingToolDetailFilmBinding.inflate(layoutInflater)
        //Eliminando el boton flotante y el edit readmore de sinopsis
        binding.layoutDetallesPeliculaCollapse.constraintFilmDetailLayout.removeView(binding.layoutDetallesPeliculaCollapse.FilmDetailTvSinopsis)


        binding.collapsingToolDetailBarImagenFondo.setImageDrawable(getDrawable(R.drawable.ic_baseline_camera_alt_24))
        binding.collapsingToolDetailBarImagenFondo.setBackgroundColor(Color.GRAY)
        binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula.setImageDrawable(getDrawable(R.drawable.ic_baseline_camera_alt_24))
        binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula.setBackgroundColor(Color.GRAY)

        setContentView(binding.root)
        cambiarModoEdicion()
        addEditText()


        binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula.setOnClickListener() {
                val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
                builder.setTitle("Url de la caraturla")
                builder.setMessage("Introduce la URL de la imagen")

                val input = EditText(this)
                input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_CLASS_TEXT
                builder.setView(input)

                builder.setPositiveButton(getString(R.string.AlertDialogPositiveButton),
                    DialogInterface.OnClickListener { dialog, which ->
                        {
                            //Guardar url de imagen y cargarla
                            pelicula.posterPath = input.text.toString()
                            Picasso.get()
                                .load(pelicula.posterPath)
                                .into(binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula)
                        }
                    })
                builder.setNegativeButton(getString(R.string.AlertDialogNegativeButton),
                    DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

                builder.show()
        }





        binding.collapsingToolDetailBarImagenFondo.setOnClickListener(){
                val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
                builder.setTitle(getString(R.string.filmDetailActivityBackgroundAlertDialogTitle))
                builder.setMessage(getString(R.string.filmDetailActvityPictureAlertDialogMessage))

                val input = EditText(this)
                input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_CLASS_TEXT
                builder.setView(input)

                builder.setPositiveButton(getString(R.string.AlertDialogPositiveButton),
                    DialogInterface.OnClickListener { dialog, which ->
                        {
                            //Guardamos la url de imagen y cargarla
                            pelicula.backdropPath = input.text.toString()
                            Picasso.get().load(pelicula.backdropPath).into(binding.collapsingToolDetailBarImagenFondo)
                        }
                    })
                builder.setNegativeButton(getString(R.string.AlertDialogNegativeButton),
                    DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

                builder.show()
        }
    }


    fun cambiarModoEdicion() {
        binding.layoutDetallesPeliculaCollapse.FilmDetailETDuracion.setEnabled(true)
        binding.layoutDetallesPeliculaCollapse.FilmDetailETGenero.setEnabled(true)
        binding.layoutDetallesPeliculaCollapse.FilmDetailETTitulo.setEnabled(true)
    }

    private fun addEditText() {
        editText = EditText(this)
        editText.setText(getString(R.string.FilmDetailTvTitulo))

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
        menuItem.add(300, 1, 1, getString(R.string.FilmCreateActivityMenuItemCreate)).setIcon(getDrawable(R.drawable.ic_baseline_check))
        menuItem.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id: Int = item.itemId
        //Dandole a eliminar muestra un aviso, este metodo nos permite pasarle otro metodo para que ejecute en caso afirmativo
        when (id) {
            menuItem.getItem(0).itemId -> {
                GestorLista(this).añadirPelicula(almacenarDatosPelicula())
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun almacenarDatosPelicula(): PeliculaJSON {
        pelicula.title = binding.layoutDetallesPeliculaCollapse.FilmDetailETTitulo.text.toString()

        pelicula.genres = List<Genres>(1) { it -> Genres() }
        pelicula.genres[0].name =
            binding.layoutDetallesPeliculaCollapse.FilmDetailETGenero.text.toString()
        //Sinopsis
        pelicula.overview = editText.text.toString()

        pelicula.runtime = binding.layoutDetallesPeliculaCollapse.FilmDetailETDuracion.text.toString().toInt()
        return pelicula
    }

}