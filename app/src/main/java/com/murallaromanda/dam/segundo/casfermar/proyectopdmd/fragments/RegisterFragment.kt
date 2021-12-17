package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.FragmentRegisterBinding
import java.util.regex.Pattern

class RegisterFragment : Fragment() {
    lateinit var binding : FragmentRegisterBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.show()
/*
        parentFragmentManager.addOnBackStackChangedListener(){
            if( parentFragmentManager.backStackEntryCount == 0)
                (activity as AppCompatActivity).supportActionBar?.hide()

        }
*/
        //supportActionBar?.setTitle("")
        binding.registerBtRegister.setOnClickListener(){
            var interruptorError:Boolean = false
            var email:String = binding.registerTieEmail.text.toString()
            var password:String = binding.registerTiePassword.text.toString()
            var confirmPassword:String = binding.registerTieConfirmPassword.text.toString()


            /*Validacion de datos, habria que crear varios métodos o incluso una clase*/
            if (binding.registerTieNick.text.toString().length == 0){
                binding.registerTilNick.setError(getString(R.string.registerActivityIncorrectUser))
                interruptorError = true
            }else{
                binding.registerTilNick.setError("")

            }

            //Muestra de errores de validacion
            if (!validarEmail(email)){
                binding.registerTilEmail.error = getString(R.string.recisterActivityInvalidEmail)
                interruptorError = true
            }else{
                binding.registerTilEmail.setError("")
            }

            if (password.length == 0 && confirmPassword.length == 0){
                binding.registerTilPassword.setError(getString(R.string.registerActivityInvalidPassword))
                interruptorError = true
            } else if (!esMismaContraseña(password, confirmPassword)){
                binding.registerTilPassword.setError(getString(R.string.registerActivityConfirmPasswordError))
                interruptorError = true
            }else{
                binding.registerTilPassword.setError("")
            }

            //Si todo está bien guardamos
            if (!interruptorError){
                LoginFragment.gestSharedPreferences.setPreferencias(email,password)
                parentFragmentManager.popBackStack()
            }



        }

        binding.registerTieBirthday.setOnClickListener(){
            mostrarCalendario()
        }

        return binding.root
    }

    fun validarEmail(email:String):Boolean{
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    fun esMismaContraseña(password:String,confirmPassword:String):Boolean{
        return password.equals(confirmPassword)
    }


    private fun mostrarCalendario() {
        //Añadimos al mes +1 ya que enero es 0 y luego los convertimos a String
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, mes, dia ->
            val selectedDate = dia.toString() + " / " + (mes + 1) + " / " + year
            binding.registerTieBirthday.setText(selectedDate)
        })

        newFragment.show(parentFragmentManager, "datePicker")
    }


}