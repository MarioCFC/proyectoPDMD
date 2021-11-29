package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.ActivityLoginBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.GestorSharedPreferences

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    /*Para poder usar en otras clases*/
    companion object{
        lateinit var gestSharedPreferences: GestorSharedPreferences
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        //Enlazamiento de los elementos de la vista
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gestSharedPreferences = GestorSharedPreferences(applicationContext)

        binding.loginTvCreatIt.setOnClickListener(){
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.loginBtLogin.setOnClickListener(){
            var email = gestSharedPreferences.getPreferencias("Email")
            var password = gestSharedPreferences.getPreferencias("Password")
            var interruptorError:Boolean = false

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

            if(!interruptorError){
                /*En el caso de que el login se produzca*/
                val intent = Intent(this,FilmListActivity::class.java)
                startActivity(intent)
            }
        }

    }

}