package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            var user = User(binding.loginTieEmail.text.toString(), binding.loginTiePassword.text.toString())
            var call = RetrofitService().getUserService().login(user)

            //TODO: Mirar como indicar el error que se produce
            call.enqueue(object : Callback<LoginToken> {
                override fun onResponse(call: Call<LoginToken>, response: Response<LoginToken>) {
                    if (response.isSuccessful) {
                        GestorSharedPreferences(requireContext()).setPersonalToken(DAMApiService.BASE_PERSONAL_TOKEN + response.body()!!.token!!)
                        var a = GestorSharedPreferences(requireContext()).getPersonalToken()
                        startActivity(Intent(activity, FilmListFragmentManagerActivity::class.java))

                    }else{
                        var mensajeError = Gson().fromJson(response.errorBody()!!.string(),ErrorResponse::class.java)
                        Log.d("MainActivity",mensajeError.message!!)
                    }
                }


                override fun onFailure(call: Call<LoginToken>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })


        }

        return binding.root
    }


}