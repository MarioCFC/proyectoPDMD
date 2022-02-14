package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.FragmentRegisterBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.ErrorResponse
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.LoginToken
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.User
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.GestorSharedPreferences
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class RegisterFragment : Fragment() {
    lateinit var binding: FragmentRegisterBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)


        binding.registerBtRegister.setOnClickListener() {
            var interruptorError: Boolean = false
            var user = User()
            user.email = binding.registerTieEmail.text.toString()
            user.password = binding.registerTiePassword.text.toString()
            var confirmPassword: String = binding.registerTieConfirmPassword.text.toString()


            //TODO:Introducir en clase que se encargue de validar los datos relacionados con el usuario
            if (binding.registerTieNick.text.toString().length == 0) {
                binding.registerTilNick.setError(getString(R.string.registerActivityIncorrectUser))
                interruptorError = true
            } else {
                binding.registerTilNick.setError("")
            }

            //Muestra de errores de validacion
            if (!validarEmail(user.email!!)) {
                binding.registerTilEmail.error = getString(R.string.recisterActivityInvalidEmail)
                interruptorError = true
            } else {
                binding.registerTilEmail.setError("")
            }

            if (user.password!!.length == 0 && confirmPassword.length == 0) {
                binding.registerTilPassword.setError(getString(R.string.registerActivityInvalidPassword))
                interruptorError = true
            } else if (!esMismaContraseña(user.password!!, confirmPassword)) {
                binding.registerTilPassword.setError(getString(R.string.registerActivityConfirmPasswordError))
                interruptorError = true
            } else {
                binding.registerTilPassword.setError("")
            }

            //TODO: Si todo está bien guardamos
            if (!interruptorError) {

                var call = RetrofitService().getUserService().singUp(user)

                //TODO: Arreglar la recogida de los codigos de error
                call.enqueue(object : Callback<Any> {
                    override fun onResponse(call: Call<Any>, response: Response<Any>) {
                        if (response.isSuccessful) {
                            parentFragmentManager.popBackStack()
                        } else {
                            var mensajeError = Gson().fromJson(
                                response.errorBody()!!.string(),
                                ErrorResponse::class.java
                            )

                            Toast.makeText(context!!,response.message(),Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Any>, t: Throwable) {
                        Toast.makeText(context!!,"No se ha podido establecer la conexion con la BD",Toast.LENGTH_SHORT).show()
                    }
                })

            }


        }

        binding.registerTieBirthday.setOnClickListener() {
            mostrarCalendario()
        }

        return binding.root
    }

    fun validarEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    fun esMismaContraseña(password: String, confirmPassword: String): Boolean {
        return password.equals(confirmPassword)
    }


    private fun mostrarCalendario() {
        //Añadimos al mes +1 ya que enero es 0 y luego los convertimos a String
        val newFragment =
            DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, mes, dia ->
                val selectedDate = dia.toString() + " / " + (mes + 1) + " / " + year
                binding.registerTieBirthday.setText(selectedDate)
            })

        newFragment.show(parentFragmentManager, "datePicker")
    }


}