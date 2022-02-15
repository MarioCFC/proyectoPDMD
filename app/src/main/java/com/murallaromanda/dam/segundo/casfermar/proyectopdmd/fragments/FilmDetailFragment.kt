package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.fragments

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.view.iterator
import androidx.fragment.app.Fragment
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.adapters.ListaPeliculasAdapter
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.FragmentCollapsingToolDetailFilmBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.ErrorResponse
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Movie
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.GestorSharedPreferences
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.RetrofitService
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilmDetailFragment : Fragment() {
/*TODO:Mirar movimiento imagen al activar/desactivar el modo edicion
  TODO:Tratar de hacer que a al hora de guardar se espere al alertdialog sin tener que repetir codigo*/

    private lateinit var peliculaEditTextData: Array<String?>
    private var posibleNuevaURLCaratula: String? = null
    private lateinit var editsTextOfLayout: Array<EditText>
    private lateinit var cadenasDatosPeliculaNull: Array<String>
    private lateinit var editText: EditText

    private lateinit var binding: FragmentCollapsingToolDetailFilmBinding
    private lateinit var menuItem: Menu
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

        //LOCALIZACION DE LOS EDITTEXT
        editsTextOfLayout = getEditText()

        var idPelicula = requireArguments().getString("idPelicula")

        var call = RetrofitService().getMovieService().getMovieByID(
            GestorSharedPreferences(requireContext()).getPersonalToken()!!,
            idPelicula!!
        )
        call.enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if (response.isSuccessful) {
                    peliculaEditTextData = response.body()!!.getDataEditTextDataArray()
                    posibleNuevaURLCaratula = peliculaEditTextData[peliculaEditTextData.size - 1]
                    textViewDataLoad()
                    loadMoviePicture()
                } else {
                    ErrorResponse.gestionarError(response.errorBody()!!.toString(), activity)
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Toast.makeText(context!!,"No se ha podido establecer la conexion con la BD", Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity.menuInflater.inflate(R.menu.menu_add_base, menu)
        menuItem = menu!!
        //Boton borrar
        menuItem.add(300, 1, 1, getString(R.string.FilmDetailActivityMenuItemDeleteTitle))
            .setIcon(activity.getDrawable(R.drawable.ic_baseline_delete_24))
        menuItem.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)

        //Boton de búsqueda del titulo en el navegador
        menuItem.add(300, 2, 2, getString(R.string.FilmDetailActivityMenuItemBrowserTitle))
            .setIcon(activity.getDrawable(R.drawable.ic_baseline_add_24))
        menuItem.getItem(1).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)

        //Boton editar
        menuItem.add(301, 3, 3, getString(R.string.FilmDetailActivityMenuItemEditTitle))
            .setIcon(activity.getDrawable(R.drawable.ic_baseline_mode_edit))
        menuItem.getItem(2).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)

        super.onCreateOptionsMenu(menu, inflater)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id: Int = item.itemId
        //Dandole a eliminar muestra un aviso, este metodo nos permite pasarle otro metodo para que ejecute en caso afirmativo
        when (id) {
            menuItem.getItem(0).itemId -> avisoBorrarPelicula()

            menuItem.getItem(1).itemId -> {
                val myIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(getString(R.string.GoogleQueryString) + peliculaEditTextData[1])
                )
                startActivity(myIntent)
            }

            menuItem.getItem(2).itemId -> {
                if (!estaEnEdicion && !editsTextOfLayout[0].text.toString().equals("")) {
                    avisoGuardarPelicula()
                } else if (!estaEnEdicion && editsTextOfLayout[0].text.toString().equals("")) {
                    editsTextOfLayout[0].setError("Introduce el titulo")
                } else {
                    cambiarModoEdicion()
                }
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
            editTextEditModeLoad()
            //Imagenes
            binding.collapsingToolDetailBarImagenFondo.setImageDrawable(activity.getDrawable(R.drawable.ic_baseline_camera_alt_24))
            binding.collapsingToolDetailBarImagenFondo.setBackgroundColor(Color.GRAY)
            binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula.setImageDrawable(
                activity.getDrawable(
                    R.drawable.ic_baseline_camera_alt_24
                )
            )
            binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula.setBackgroundColor(Color.GRAY)

        } else {
            //Eliminamos el editText
            binding.layoutDetallesPeliculaCollapse.constraintFilmDetailLayout.removeViewAt(
                binding.layoutDetallesPeliculaCollapse.constraintFilmDetailLayout.getChildCount() - 1
            )
            //Hacemos visible el ReadMore y le colocamos el texto del EditText
            binding.layoutDetallesPeliculaCollapse.FilmDetailTvSinopsis.visibility =
                View.VISIBLE
            binding.layoutDetallesPeliculaCollapse.FilmDetailTvSinopsis.setText(peliculaEditTextData[peliculaEditTextData.size - 2])

            textViewDataLoad()

            //Imagenes
            binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula.setBackgroundColor(
                ContextCompat.getColor(requireContext(), R.color.mainBackgroundColor)
            )

            binding.collapsingToolDetailBarImagenFondo.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.mainBackgroundColor
                )
            )
            loadMoviePicture()
        }
    }

    /**
     * @return Modificca los iconos de la toolbar ocultando/mostrando los correspondientes al modo edicion
     */
    fun cambiarModoEdicionMenu() {
        if (estaEnEdicion) {
            menuItem.setGroupVisible(300, false)
            menuItem.getItem(2).setIcon(activity.getDrawable(R.drawable.ic_baseline_done_24))
        } else {
            menuItem.setGroupVisible(300, true)
            menuItem.getItem(2).setIcon(activity.getDrawable(R.drawable.ic_baseline_mode_edit))
        }
    }


    fun textViewDataLoad() {
        cadenasDatosPeliculaNull = Movie.getCadenasDatosPeliculaNull(context!!)

        for (i in 1..editsTextOfLayout.size) {
            if (peliculaEditTextData[i] != null)
                editsTextOfLayout[i - 1].setText(peliculaEditTextData[i])
            else
                editsTextOfLayout[i - 1].setText(cadenasDatosPeliculaNull[i])
        }
        //TODO:Refactorizar
        if (peliculaEditTextData[peliculaEditTextData.size - 2] != null)
            binding.layoutDetallesPeliculaCollapse.FilmDetailTvSinopsis.setText(peliculaEditTextData[peliculaEditTextData.size - 2])
        else
            binding.layoutDetallesPeliculaCollapse.FilmDetailTvSinopsis.setText(
                cadenasDatosPeliculaNull[peliculaEditTextData.size - 2]
            )

    }

    fun editTextEditModeLoad() {
        //Recordar que el bucle for en este caso es desde 1 hasta el tamaño del array,inluido
        for (i in 1..editsTextOfLayout.size) {
            if (peliculaEditTextData[i] != null) {
                editsTextOfLayout[i - 1].setText(peliculaEditTextData[i])
            } else {
                editsTextOfLayout[i - 1].setText("")
                editsTextOfLayout[i - 1].setHint(cadenasDatosPeliculaNull[i])
            }
        }

        //Sinopsiss ya que el edit text rota con el ReadMore
        if (peliculaEditTextData[peliculaEditTextData.size - 2] != null)
            editText.setText(peliculaEditTextData[peliculaEditTextData.size - 2])
        else
            editText.setText("")
        editText.setHint(cadenasDatosPeliculaNull[peliculaEditTextData.size - 2])
    }

    fun setPictures() {
        if (posibleNuevaURLCaratula != null)
            Picasso.get()
                .load(posibleNuevaURLCaratula)
                .into(binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula)

        if (posibleNuevaURLCaratula != null)
            Picasso.get()
                .load(posibleNuevaURLCaratula)
                .into(binding.collapsingToolDetailBarImagenFondo)
    }

    fun loadMoviePicture() {
        //Imagenes
        setPictures()
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

                        var urlIntroducida = input.text.toString()
                        if (urlIntroducida.equals("")) {
                            posibleNuevaURLCaratula = null

                        } else {
                            posibleNuevaURLCaratula = urlIntroducida
                        }
                        setPictures()

                    })

                builder.setNegativeButton(getString(R.string.AlertDialogNegativeButton),
                    DialogInterface.OnClickListener { dialog, which ->
                        dialog.cancel()
                    })

                builder.show()
            }
        }
    }

    private fun addEditText() {
        editText = EditText(activity)
        editText.setText(peliculaEditTextData[peliculaEditTextData.size - 2])
        editText.setTextColor(ContextCompat.getColor(requireContext(), R.color.enabled_color))

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

    fun guardarCambiosDatosPelicula() {
        var textoEditText: String
        //EditTexts
        for (i in 1..editsTextOfLayout.size) {
            textoEditText = editsTextOfLayout[i - 1].text.toString()

            if (textoEditText.equals("")) {
                peliculaEditTextData[i] = null
            } else if (!textoEditText.equals(peliculaEditTextData[i])) {
                peliculaEditTextData[i] = textoEditText
            }
        }

        //Sinopsis
        textoEditText = editText.text.toString()
        if (textoEditText.equals("")) {
            peliculaEditTextData[peliculaEditTextData.size - 2] = null
        } else if (!textoEditText.equals(peliculaEditTextData[peliculaEditTextData.size - 2])) {
            peliculaEditTextData[peliculaEditTextData.size - 2] = textoEditText
        }

        //Imagen
        peliculaEditTextData[peliculaEditTextData.size - 1] = posibleNuevaURLCaratula


        var peliculaModificadaEnviadaAPI = Movie()
        peliculaModificadaEnviadaAPI.setEditTextDataOfArray(peliculaEditTextData)
        var call = RetrofitService().getMovieService().editMOvie(
            GestorSharedPreferences(requireContext()).getPersonalToken()!!,
            peliculaModificadaEnviadaAPI
        )

        call.enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    Toast.makeText(context!!,"Pelicula editada",Toast.LENGTH_SHORT).show()
                } else{
                    ErrorResponse.gestionarError( response.errorBody()!!.string(), activity)
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.d("Main", t.message!!)
            }

        })

    }

    /**
     *@return Indica si se han realizado cambios en los datos dela pelicula volcados en los componentes de la vista
     */
    fun avisoBorrarPelicula() {
        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setTitle(getString(R.string.AlertDialogDeleteTitle))
        dialogBuilder.setMessage(getString(R.string.FilmDetailActivityDeleteMessage))
            .setCancelable(false)
            .setPositiveButton(
                getString(R.string.AlertDialogPositiveButton),
                DialogInterface.OnClickListener { dialog, id ->

                    val call = RetrofitService().getMovieService().deleteMovieByID(
                        GestorSharedPreferences(requireContext()).getPersonalToken()!!,
                        peliculaEditTextData[0]!!
                    )
                    call.enqueue(object : Callback<Unit> {
                        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                            if (response.isSuccessful) {
                                parentFragmentManager.popBackStack()
                            }else{
                                ErrorResponse.gestionarError( response.errorBody()!!.string(), activity)
                            }
                        }

                        override fun onFailure(call: Call<Unit>, t: Throwable) {
                            TODO("Not yet implemented")
                        }

                    })

                })
            .setNegativeButton(
                getString(R.string.AlertDialogNegativeButton),
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                })

        val alert = dialogBuilder.create()
        alert.setTitle(getString(R.string.AlertDialogDeleteTitle))
        alert.show()
    }

    fun avisoGuardarPelicula() {
        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setTitle(getString(R.string.AlertDialogDSaveChangesTitle))
        dialogBuilder.setMessage(getString(R.string.FilmDetailActivitySaveChangesMessage))
            .setCancelable(false)
            .setPositiveButton(
                getString(R.string.AlertDialogPositiveButton),
                DialogInterface.OnClickListener { dialog, id ->
                    guardarCambiosDatosPelicula()
                    //TODO:Intentar cambiar esto
                    cambiarModoEdicion()
                })
            .setNegativeButton(
                getString(R.string.AlertDialogNegativeButton),
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                    posibleNuevaURLCaratula = peliculaEditTextData[peliculaEditTextData.size - 1]
                    setPictures()
                    textViewDataLoad()
                    cambiarModoEdicion()
                })

        val alert = dialogBuilder.create()
        alert.setTitle(getString(R.string.AlertDialogDeleteTitle))
        alert.show()
    }

    fun getEditText(): Array<EditText> {
        var editTextContenidas = ArrayList<EditText>()

        for (cont: View in binding.layoutDetallesPeliculaCollapse.constraintFilmDetailLayout.iterator()) {
            if (cont is EditText) {
                editTextContenidas.add(cont)
            }
        }

        return editTextContenidas.toTypedArray()

    }
}