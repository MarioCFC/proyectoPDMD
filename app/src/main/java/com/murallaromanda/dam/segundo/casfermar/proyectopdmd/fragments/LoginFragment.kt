package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.IO.DAMApiService
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities.FilmListFragmentManagerActivity
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.FragmentLoginBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.ErrorResponse
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.LoginToken
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.User
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.GestorSharedPreferences
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginFragment : Fragment() {

    companion object {
        lateinit var gestSharedPreferences: GestorSharedPreferences
    }

    lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        gestSharedPreferences = GestorSharedPreferences(container!!.context)
        (activity as AppCompatActivity).supportActionBar?.show()

        binding.loginTvCreatIt.setOnClickListener() {
            val ft = activity?.supportFragmentManager?.beginTransaction()
            ft?.replace(R.id.contenedor_fragments, RegisterFragment())
            ft?.addToBackStack(null)
            ft?.commit()
        }

        binding.loginBtLogin.setOnClickListener() {
            var user = User()
            var email = binding.loginTieEmail.text.toString()
            var contraseña = binding.loginTiePassword.text.toString()

            //TODO:Cambiar el siguiente bloque de condiciones anidadas
            if (user.validarEmail(email)) {
                user.email = email

                binding.loginTilEmail.setError("")

                if (!contraseña.equals("")) {
                    user.password = contraseña

                    var call = RetrofitService().getUserService().login(user)
                    call.enqueue(object : Callback<LoginToken> {
                        override fun onResponse(
                            call: Call<LoginToken>,
                            response: Response<LoginToken>
                        ) {
                            if (response.isSuccessful) {
                                GestorSharedPreferences(requireContext()).setPersonalToken(
                                    DAMApiService.BASE_PERSONAL_TOKEN + response.body()!!.token!!
                                )
                                var a = GestorSharedPreferences(requireContext()).getPersonalToken()
                                startActivity(
                                    Intent(
                                        activity,
                                        FilmListFragmentManagerActivity::class.java
                                    )
                                )
                                //Para que en la lista no se pueda volver al login
                                activity!!.finish()
                            } else {
                                var mensajeError = Gson().fromJson(
                                    response.errorBody()!!.string(),
                                    ErrorResponse::class.java
                                )
                                Toast.makeText(context!!, mensajeError.message, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }


                        override fun onFailure(call: Call<LoginToken>, t: Throwable) {
                            Toast.makeText(
                                context!!,
                                "No se ha podido establecer la conexion con la BD",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                    binding.loginTilPassword.setError("")
                } else {
                    binding.loginTilPassword.setError("Contraseña no válida")
                }
            } else {
                binding.loginTilEmail.setError("Email no válido")
            }


        }

        return binding.root
    }


}