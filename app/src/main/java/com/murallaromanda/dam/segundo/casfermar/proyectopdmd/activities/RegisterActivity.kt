package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.DatePicker
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities.LoginActivity.Companion.gestSharedPreferences
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.ActivityRegisterBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.fragments.DatePickerFragment
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerBtRegister.setOnClickListener(){
            var interruptorError:Boolean = false
            var email:String = binding.registerTieEmail.text.toString()
            var password:String = binding.registerTiePassword.text.toString()
            var confirmPassword:String = binding.registerTieConfirmPassword.text.toString()


            /*Validacion de datos, habria que crear varios métodos o incluso una clase*/
            if (binding.registerTieNick.text.toString().length == 0){
                binding.registerTilNick.setError("Usuario no valido")
                interruptorError = true
            }else{
                binding.registerTilNick.setError("")

            }

            //Muestra de errores de validacion
            if (!validarEmail(email)){
                binding.registerTilEmail.setError("Email no valido")
                interruptorError = true
            }else{
                binding.registerTilEmail.setError("")
            }

            if (password.length == 0 && confirmPassword.length == 0){
                binding.registerTilPassword.setError("Contraseña no valida")
                interruptorError = true
            } else if (!esMismaContraseña(password, confirmPassword)){
                binding.registerTilPassword.setError("Las contraseñas no coinciden")
                interruptorError = true
            }else{
                binding.registerTilPassword.setError("")
            }

            //Si todo está bien guardamos
            if (!interruptorError){
                gestSharedPreferences.setPreferencias(email,password)
                onBackPressed()
            }


        }

        binding.registerTieBirthday.setOnClickListener(){
            mostrarCalendario()
        }
    }

    fun validarEmail(email:String):Boolean{
        val pattern:Pattern = Patterns.EMAIL_ADDRESS
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

        newFragment.show(supportFragmentManager, "datePicker")
    }
}