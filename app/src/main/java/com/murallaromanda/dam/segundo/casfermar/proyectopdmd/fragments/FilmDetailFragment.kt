package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.fragments

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.IO.DAMApiService
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.FragmentCollapsingToolDetailFilmBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Movie
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.GestorSharedPreferences
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.RetrofitService
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilmDetailFragment:Fragment() {

    private lateinit var binding: FragmentCollapsingToolDetailFilmBinding
    private lateinit var pelicula: Movie
    private lateinit var menuItem: Menu
    private lateinit var editText: EditText
    private var estaEnEdicion: Boolean = true
    private lateinit var activity: AppCompatActivity


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        activity = getActivity() as AppCompatActivity
        binding = FragmentCollapsingToolDetailFilmBinding.inflate(inflater, container, false)

        var idPelicula = requireArguments().getString("idPelicula")

        var call = RetrofitService().getMovieService().getMovieByID(GestorSharedPreferences(requireContext()).getPersonalToken()!!,idPelicula!!)

        call.enqueue(object :Callback<Movie>{
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                pelicula = response.body()!!
                textViewDataLoad()
                loadMoviePicture()
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.d("Main",t.message!!)
            }
        })

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity.menuInflater.inflate(R.menu.menu_add_base, menu)
        menuItem = menu!!
        //Boton borrar
        menuItem.add(300, 1, 1, getString(R.string.FilmDetailActivityMenuItemDeleteTitle)).setIcon(activity.getDrawable(R.drawable.ic_baseline_delete))
        menuItem.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)

        //Boton de búsqueda del titulo en el navegador
        menuItem.add(300,2,2,getString(R.string.FilmDetailActivityMenuItemBrowserTitle)).setIcon(activity.getDrawable(R.drawable.ic_baseline_browser))
        menuItem.getItem(1).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)

        //Boton editar
        menuItem.add(301, 3, 3, getString(R.string.FilmDetailActivityMenuItemEditTitle)).setIcon(activity.getDrawable(R.drawable.ic_baseline_edit))
        menuItem.getItem(2).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)

        super.onCreateOptionsMenu(menu, inflater)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id: Int = item.itemId
        //Dandole a eliminar muestra un aviso, este metodo nos permite pasarle otro metodo para que ejecute en caso afirmativo
        when (id) {
           menuItem.getItem(0).itemId ->avisoBorrarPelicula()

            menuItem.getItem(1).itemId -> {
                val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.GoogleQueryString) + pelicula.title))
                startActivity(myIntent)
            }

            menuItem.getItem(2).itemId -> {
                if (!estaEnEdicion && hayCambios()) {
                   //Guardar cambios
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
        //Activamos/Desactivamos los EditTextt permitiendo la edición de los datos
        binding.layoutDetallesPeliculaCollapse.FilmDetailETDuracion.setEnabled(estaEnEdicion)
        binding.layoutDetallesPeliculaCollapse.FilmDetailETGenero.setEnabled(estaEnEdicion)
        binding.layoutDetallesPeliculaCollapse.FilmDetailETTitulo.setEnabled(estaEnEdicion)
        binding.layoutDetallesPeliculaCollapse.FilmDetailETValoracion.setEnabled(estaEnEdicion)

        if (estaEnEdicion) {
            //Ocultamos el ReadMore y creamos el edit text
            binding.layoutDetallesPeliculaCollapse.FilmDetailTvSinopsis.visibility = View.GONE
            addEditText()

            //Imagenes
            binding.collapsingToolDetailBarImagenFondo.setImageDrawable(activity.getDrawable(R.drawable.ic_baseline_camera_alt_24))
            binding.collapsingToolDetailBarImagenFondo.setBackgroundColor(Color.GRAY)
            binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula.setImageDrawable(activity.getDrawable(R.drawable.ic_baseline_camera_alt_24))
            binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula.setBackgroundColor(Color.GRAY)

        } else {
            //Eliminamos el editText
            binding.layoutDetallesPeliculaCollapse.constraintFilmDetailLayout.removeViewAt(
                binding.layoutDetallesPeliculaCollapse.constraintFilmDetailLayout.getChildCount() - 1
            )
            //Hacemos visible el ReadMore y le colocamos el texto del EditText
            binding.layoutDetallesPeliculaCollapse.FilmDetailTvSinopsis.visibility =
                View.VISIBLE
            binding.layoutDetallesPeliculaCollapse.FilmDetailTvSinopsis.setText(pelicula.description)

            //Imagenes
            Picasso.get()
                .load(pelicula.imageUrl)
                .into(binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula)
            binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.pruebaFondo))

            Picasso.get()
                .load(pelicula.imageUrl)
                .into(binding.collapsingToolDetailBarImagenFondo)
            binding.collapsingToolDetailBarImagenFondo.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.pruebaFondo))
        }
    }

    /**
     * @return Modificca los iconos de la toolbar ocultando/mostrando los correspondientes al modo edicion
     */
    fun cambiarModoEdicionMenu() {
        if (estaEnEdicion) {
            menuItem.setGroupVisible(300,false)
            menuItem.getItem(2).setIcon(activity.getDrawable(R.drawable.ic_baseline_check))
        } else {
            menuItem.setGroupVisible(300,true)
            menuItem.getItem(2).setIcon(activity.getDrawable(R.drawable.ic_baseline_edit))
        }
    }

    //Bindeamos los datos
        fun textViewDataLoad() {
            binding.layoutDetallesPeliculaCollapse.FilmDetailETDuracion.setText(pelicula.runtimeMinutes.toString())
            binding.layoutDetallesPeliculaCollapse.FilmDetailETGenero.setText(pelicula.genre)
            binding.layoutDetallesPeliculaCollapse.FilmDetailETTitulo.setText(pelicula.title)
            binding.layoutDetallesPeliculaCollapse.FilmDetailTvSinopsis.setText(pelicula.description)
            binding.layoutDetallesPeliculaCollapse.FilmDetailETValoracion.setText(pelicula.rating)
        }

        fun loadMoviePicture() {
            //Imagenes
            Picasso.get().isLoggingEnabled = true
            if (pelicula.imageUrl != null)
                Picasso.get()
                    .load(pelicula.imageUrl)
                    .into(binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula)

            if (pelicula.imageUrl != null)
                Picasso.get()
                    .load(pelicula.imageUrl)
                    .into(binding.collapsingToolDetailBarImagenFondo)


            //Introducción de una URL para cargar otra imagen
            binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula.setOnClickListener() {
                if (!estaEnEdicion) {
                    val builder: android.app.AlertDialog.Builder =
                        android.app.AlertDialog.Builder(activity)
                    builder.setTitle(getString(R.string.filmDetailActivityPosterAlertDialogTitle))
                    builder.setMessage(getString(R.string.filmDetailActvityPictureAlertDialogMessage))

                    val input = EditText(activity)
                    input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_CLASS_TEXT
                    builder.setView(input)

                    builder.setPositiveButton(getString(R.string.AlertDialogPositiveButton),
                        DialogInterface.OnClickListener { dialog, which ->
                            pelicula.imageUrl = input.text.toString()
                            Picasso.get()
                                .load(pelicula.imageUrl)
                                .into(binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula)
                        })
                    builder.setNegativeButton(getString(R.string.AlertDialogNegativeButton),
                        DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

                    builder.show()
                }


            }
        }

    private fun addEditText() {
        editText = EditText(activity)
        editText.setText(pelicula.description)
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
            binding.layoutDetallesPeliculaCollapse.FilmDetailETValoracion.id,
            ConstraintSet.BOTTOM
        );

        constraintSet.applyTo(constraintLayout)

    }

    fun cargarCambios() {
        pelicula.genre =
            binding.layoutDetallesPeliculaCollapse.FilmDetailETGenero.getText().toString()
        pelicula.title =
            binding.layoutDetallesPeliculaCollapse.FilmDetailETTitulo.getText().toString()
        pelicula.imageUrl = editText.text.toString()

    }

    /**
     *@return Indica si se han realizado cambios en los datos dela pelicula volcados en los componentes de la vista
     */
    fun hayCambios():Boolean{
        return binding.layoutDetallesPeliculaCollapse.FilmDetailETTitulo.getText().toString() != pelicula.title ||
                binding.layoutDetallesPeliculaCollapse.FilmDetailETGenero.getText().toString() != pelicula.description ||
                editText.text.toString() != pelicula.description
    }



    fun avisoBorrarPelicula() {
        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setTitle(getString(R.string.AlertDialogDeleteTitle))
        dialogBuilder.setMessage(getString(R.string.FilmDetailActivityDeleteMessage))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.AlertDialogPositiveButton), DialogInterface.OnClickListener { dialog, id ->

                val call = RetrofitService().getMovieService().deleteMovieByID(GestorSharedPreferences(requireContext()).getPersonalToken()!!,pelicula.id!!)
                call.enqueue(object :Callback<Unit>{
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        if(response.isSuccessful){
                            parentFragmentManager.popBackStack()
                        }else{
                            Log.d("Main",response.message())
                        }
                    }
                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })

            })
            .setNegativeButton(getString(R.string.AlertDialogNegativeButton), DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })

        val alert = dialogBuilder.create()
        alert.setTitle(getString(R.string.AlertDialogDeleteTitle))
        alert.show()
    }



}