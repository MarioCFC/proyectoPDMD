package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.fragments

import android.content.DialogInterface
import android.graphics.Color
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
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.FragmentCollapsingToolDetailFilmBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.ErrorResponse
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Movie
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.GestorSharedPreferences
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.RetrofitService
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilmCreateFragment : Fragment() {
    lateinit var binding: FragmentCollapsingToolDetailFilmBinding
    private lateinit var editsTextOfLayout: Array<EditText>
    private lateinit var editText: EditText

    private var peliculaEditTextData: Array<String?> =
        arrayOf(null, null, null, null, null, null, null)
    private var posibleNuevaURLCaratula: String? = null

    private lateinit var menuItem: Menu
    private lateinit var activity: AppCompatActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        activity = getActivity() as AppCompatActivity
        binding = FragmentCollapsingToolDetailFilmBinding.inflate(inflater, container, false)

        activity.supportActionBar?.show()
        activity.supportActionBar?.setTitle("")

        editsTextOfLayout = getEditText()


        binding.layoutDetallesPeliculaCollapse.constraintFilmDetailLayout.removeView(binding.layoutDetallesPeliculaCollapse.FilmDetailTvSinopsis)
        binding.collapsingToolDetailBarImagenFondo.setImageDrawable(activity.getDrawable(R.drawable.ic_baseline_camera_alt_24))
        binding.collapsingToolDetailBarImagenFondo.setBackgroundColor(Color.GRAY)
        binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula.setImageDrawable(
            activity.getDrawable(
                R.drawable.ic_baseline_camera_alt_24
            )
        )
        binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula.setBackgroundColor(Color.GRAY)

        cambiarModoEdicion()
        addEditText()


        binding.layoutDetallesPeliculaCollapse.FilmDetaiIvCaratula.setOnClickListener() {
            val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(activity)
            builder.setTitle(getString(R.string.filmDetailActivityPosterAlertDialogTitle))
            builder.setMessage(getString(R.string.filmDetailActvityPictureAlertDialogMessage))

            val input = EditText(activity)
            input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_CLASS_TEXT
            builder.setView(input)

            builder.setPositiveButton(getString(R.string.AlertDialogPositiveButton),
                DialogInterface.OnClickListener { dialog, which ->
                    {
                        var urlIntroducida = input.text.toString()
                        if (urlIntroducida.equals("")) {
                            posibleNuevaURLCaratula = null

                        } else {
                            posibleNuevaURLCaratula = urlIntroducida
                        }
                        setPictures()
                    }
                })
            builder.setNegativeButton(getString(R.string.AlertDialogNegativeButton),
                DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

            builder.show()
        }

        return binding.root
    }

    fun cambiarModoEdicion() {
        binding.layoutDetallesPeliculaCollapse.FilmDetailETDuracion.setEnabled(true)
        binding.layoutDetallesPeliculaCollapse.FilmDetailETGenero.setEnabled(true)
        binding.layoutDetallesPeliculaCollapse.FilmDetailETTitulo.setEnabled(true)
        binding.layoutDetallesPeliculaCollapse.FilmDetailETValoracion.setEnabled(true)

    }

    private fun addEditText() {
        editText = EditText(activity)
        editText.setText(getString(R.string.FilmDetailTvTitulo))
        editText.setTextColor(ContextCompat.getColor(requireContext(), R.color.generalText))
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


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity.menuInflater.inflate(R.menu.menu_add_base, menu)
        menuItem = menu!!
        menuItem.add(300, 1, 1, getString(R.string.FilmCreateActivityMenuItemCreate))
            .setIcon(activity.getDrawable(R.drawable.ic_baseline_check))
        menuItem.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id: Int = item.itemId
        //Dandole a eliminar muestra un aviso, este metodo nos permite pasarle otro metodo para que ejecute en caso afirmativo
        when (id) {
            menuItem.getItem(0).itemId -> {
                if (!editsTextOfLayout[0].text.toString().equals("")) {
                    avisoGuardarPelicula()
                } else
                    editsTextOfLayout[0].setError("Introduce el titulo")
                return true
            }
        }
        return false
    }

    fun almacenarDatosPelicula() {
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
        var call = RetrofitService().getMovieService().createMovie(
            GestorSharedPreferences(requireContext()).getPersonalToken()!!,
            peliculaModificadaEnviadaAPI
        )
        call.enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    parentFragmentManager.popBackStack()
                } else {
                    ErrorResponse.gestionarError(response.errorBody().toString(), activity)
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Toast.makeText(context!!,"No se ha podido establecer la conexion con la BD", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun avisoGuardarPelicula() {
        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setTitle(getString(R.string.AlertDialogDSaveChangesTitle))
        dialogBuilder.setMessage(getString(R.string.FilmDetailActivitySaveChangesMessage))
            .setCancelable(false)
            .setPositiveButton(
                getString(R.string.AlertDialogPositiveButton),
                DialogInterface.OnClickListener { dialog, id ->
                    almacenarDatosPelicula()
                    //TODO:Intentar cambiar esto
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

