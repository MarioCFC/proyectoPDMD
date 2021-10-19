package com.murallaromanda.dam.segundo.casfermar.proyectopdmd

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btRegister.setOnClickListener(){
            var email = binding.registerTieEmail.text.toString()
            var password = binding.registerTiePassword.text.toString()

            escribirSharedPreference(email,password)
            onBackPressed()
        }
    }

    fun escribirSharedPreference(usuario:String, contraseña:String){
        val sharedPref = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        with(sharedPref.edit()){
            putString("User",usuario)
            putString("Password",contraseña)
            commit()
        }
    }
}