package com.murallaromanda.dam.segundo.casfermar.proyectopdmd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.util.Log
import android.view.inputmethod.InputBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Enlazamiento de los elementos de la vista
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvCreala.setOnClickListener(){
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.bLogin.setOnClickListener(){
            var email = binding.tieEmail.text.toString()
            var password = binding.tiePassword.text.toString()

            Log.d("MainActivity","\nEmail: " + email + "\nPassword: " + password)
        }

    }
}