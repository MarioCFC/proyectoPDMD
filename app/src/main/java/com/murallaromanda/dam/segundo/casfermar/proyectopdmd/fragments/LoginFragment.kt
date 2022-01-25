package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities.FilmListFragmentManagerActivity
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.FragmentLoginBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.GestorSharedPreferences

class LoginFragment :Fragment(){

    companion object{
        lateinit var gestSharedPreferences: GestorSharedPreferences
    }

    lateinit var binding : FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater,container,false)
        gestSharedPreferences = GestorSharedPreferences(container!!.context)
        (activity as AppCompatActivity).supportActionBar?.show()

        binding.loginTvCreatIt.setOnClickListener(){
            val ft = activity?.supportFragmentManager?.beginTransaction()
            ft?.replace(R.id.contenedor_fragments,RegisterFragment())
            ft?.addToBackStack(null)
            ft?.commit()
        }

        binding.loginBtLogin.setOnClickListener(){
            var email = LoginFragment.gestSharedPreferences.getPreferencias("Email")
            var password = LoginFragment.gestSharedPreferences.getPreferencias("Password")
            var interruptorError:Boolean = false
/*DESCOMENTAR
            if (!email.equals(binding.loginTieEmail.text.toString())){
                binding.loginTilEmail.setError(getString(R.string.loginActivityEmailValidationError))
                interruptorError = true
            }else{
                binding.loginTilEmail.setError("")
            }

            if (!password.equals(binding.loginTiePassword.text.toString())){
                binding.loginTilPassword.setError(getString(R.string.loginActivityPasswordValidationError))
                interruptorError = true
            }else{
                binding.loginTilPassword.setError("")
            }
*/
            if(!interruptorError){
                /*En el caso de que el login se produzca*/
                startActivity(Intent(activity, FilmListFragmentManagerActivity::class.java))
            }
        }


        return binding.root
    }



}