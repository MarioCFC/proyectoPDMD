package com.murallaromanda.dam.segundo.casfermar.proyectopdmd

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
            onBackPressed()
        }
    }
}