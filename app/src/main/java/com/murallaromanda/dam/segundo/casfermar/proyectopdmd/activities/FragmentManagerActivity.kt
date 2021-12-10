package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.fragments.LoginFragment

class FragmentManagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_manager)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.contenedor_fragments,LoginFragment())
        fragmentTransaction.commit()
    }
}