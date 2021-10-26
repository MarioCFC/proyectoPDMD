package com.murallaromanda.dam.segundo.casfermar.proyectopdmd

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.MainActivity.Companion.gestSharedPreferences
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.ActivityRegisterBinding
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btRegister.setOnClickListener(){
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

            if (!interruptorError){
                gestSharedPreferences.setPreferencias(email,password)
                onBackPressed()
            }


        }
    }

    fun validarEmail(email:String):Boolean{
        val pattern:Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    fun esMismaContraseña(password:String,confirmPassword:String):Boolean{
        return password.equals(confirmPassword)
    }
}